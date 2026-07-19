package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.BattleAction
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.net.game.packets.ChatMessageSendPacket
import de.fiereu.openmmo.net.game.packets.EntityMovePpPacket
import de.fiereu.openmmo.net.game.packets.EntityPresencePacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.SocialListEntryAddPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleBulkStatePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityMoveEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleFieldMon
import de.fiereu.openmmo.net.game.packets.battle.BattleFieldStatePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleListEventDetail
import de.fiereu.openmmo.net.game.packets.battle.BattleListEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleQueuedEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSidePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSlotEventEnumPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSwitchInMon
import de.fiereu.openmmo.net.game.packets.battle.BattleSwitchInPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleTileMapPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

private const val TEST_BATTLE_COMMAND = "/testbattle"
// The bag is populated in the join flow, so /catch drives the catch sequence directly for testing
// even though a ball can now be thrown from the UI.
private const val CATCH_COMMAND = "/catch"

private const val PATRAT: Short = 504
private const val WILD_MON_ID = 0x000000000003C000L
private const val CAUGHT_PATRAT_UID = 0x000000000002C000L
private const val WILD_PATRAT_HP: Short = 14
private const val CAUGHT_PATRAT_LEVEL: Byte = 2
private const val CAUGHT_PATRAT_HP: Short = 8
private const val CAUGHT_PATRAT_XP = 8
private const val PATRAT_TACKLE: Short = 33
private const val PATRAT_TACKLE_PP: Byte = 35
private const val ACTION_PROMPT: Byte = -128 // 0x80

private const val ENEMY_MOVE: Short = 33
private const val MOVE_EVENT_KIND: Byte = 1
private const val POKE_BALL_ITEM: Short = 5004
// Slot-event type shown when the player gets away from a wild battle.
private const val FLED_EVENT: Byte = 0

// The player's overworld entity is hidden while the battle scene is up and shown again when it
// ends.
private const val PRESENCE_IN_BATTLE: Byte = 1
private const val PRESENCE_OVERWORLD: Byte = 0

// The active battle side reported to the client so the bag knows which monster an item targets.
private const val PLAYER_SIDE: Byte = 1

@Singleton
class BattleService
@Inject
constructor(
    private val characterStore: CharacterStore,
) {

  private val battleTurn = ConcurrentHashMap<Long, Int>()
  // The active party slot per battle, and which slots have been sent out as active. A monster's
  // first appearance carries its full block, a return only its active detail.
  private val battleActiveSlot = ConcurrentHashMap<Long, Int>()
  private val battleSeenActive = ConcurrentHashMap<Long, MutableSet<Int>>()

  fun onBattlePacket(event: PacketEvent<*>) {
    log.info { "Battle packet ${event.packet::class.simpleName} received: ${event.packet}" }
  }

  fun onBattleAction(event: PacketEvent<BattleActionSelectPacket>) {
    val session = event.session
    val charId = session.attributes[PLAYER_STATE]?.characterId ?: return
    val action = event.packet
    log.info { "Battle action char=$charId: $action" }
    when (action.action) {
      BattleAction.MOVE -> {
        playMoveAnimations(session, charId, action.moveOrItemId)
        advanceTurn(session, charId)
      }
      BattleAction.ITEM -> catchWildMon(session, charId)
      BattleAction.SWITCH -> switchMon(session, charId, action.moveOrItemId)
      BattleAction.RUN -> fleeBattle(session, charId)
    }
  }

  // The client picked a party slot to switch to. The switch-in packet (opcode 0x35) brings the new
  // monster onto the field: the first time a monster comes out it carries its full block, otherwise
  // just its active detail. The turn then advances and re-prompts for the next action.
  private fun switchMon(session: SessionContext, charId: Long, partyIndex: Short) {
    val stored = characterStore.getCharacter(charId) ?: return
    val target = partyIndex.toInt()
    val mon = stored.pokemon.getOrNull(target)
    if (mon == null) {
      log.warn { "Switch to invalid party index $partyIndex for char=$charId" }
      advanceTurn(session, charId)
      return
    }
    val oldSlot = battleActiveSlot[charId] ?: 0
    if (target == oldSlot) {
      advanceTurn(session, charId)
      return
    }
    val seen = battleSeenActive.getOrPut(charId) { mutableSetOf(0) }
    val fullBlock = target !in seen
    log.info { "Switch char=$charId slot $oldSlot -> $target (fullBlock=$fullBlock)" }
    session.send(
        BattleSwitchInPacket(
            newSlot = target,
            oldSlot = oldSlot,
            mon = BattleSwitchInMon(mon.id, mon.dexId.toShort(), mon.hp),
            fullBlock = fullBlock,
        ),
    )
    seen.add(target)
    battleActiveSlot[charId] = target
    advanceTurn(session, charId)
  }

  // The player fled. The slot event shows the got-away text, the flee end marker closes the battle
  // scene, and the presence toggle restores the overworld player.
  private fun fleeBattle(session: SessionContext, charId: Long) {
    battleTurn.remove(charId)
    battleActiveSlot.remove(charId)
    battleSeenActive.remove(charId)
    session.send(BattleSlotEventEnumPacket(slot = 0, eventType = FLED_EVENT))
    session.send(BattleBulkStatePacket.fled())
    session.send(EntityPresencePacket(entityId = charId, status = PRESENCE_OVERWORLD))
  }

  private fun catchWildMon(session: SessionContext, charId: Long) {
    log.info { "Ball thrown, catching wild Patrat for char=$charId" }
    battleTurn.remove(charId)
    battleActiveSlot.remove(charId)
    battleSeenActive.remove(charId)
    val ot = characterStore.getCharacter(charId)?.info?.name ?: ""
    val patrat = caughtPatrat(charId, ot)
    // The caught Patrat is sent as a full 148-byte record on opcode 0x14 before the ball-throw
    // event, so the client can resolve the monster when the throw lands.
    session.send(SocialListEntryAddPacket(patrat))
    // "Player threw a Poke Ball" event.
    session.send(
        BattleListEventPacket(
            kind = 0,
            value = POKE_BALL_ITEM,
            subKind = 4,
            detail = BattleListEventDetail(listType = 1, value = 1),
        ),
    )
    endBattle(session, charId, patrat)
  }

  // The wild encounter is over. The bulk-state end marker tells the client to leave the battle
  // scene, the presence toggle shows the overworld player again, and the party is persisted and
  // resynced so the caught monster stays after the battle.
  // TODO: the caught monster does not show up in the party after the battle closes.
  private fun endBattle(session: SessionContext, charId: Long, caught: Pokemon) {
    val stored = characterStore.getCharacter(charId) ?: return
    if (stored.pokemon.none { it.id == caught.id }) {
      characterStore.addPokemon(charId, caught)
    }
    session.send(BattleBulkStatePacket.battleEnd())
    session.send(EntityPresencePacket(entityId = charId, status = PRESENCE_OVERWORLD))
    session.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = characterStore.getCharacter(charId)?.pokemon ?: emptyList(),
        ),
    )
  }

  private fun caughtPatrat(ownerId: Long, ot: String): Pokemon =
      Pokemon(
          id = CAUGHT_PATRAT_UID,
          ownerId = ownerId,
          container = PokemonContainer.PARTY,
          containerSlot = 1,
          dexId = PATRAT.toInt(),
          seed = 0,
          ot = ot,
          nickname = "",
          level = CAUGHT_PATRAT_LEVEL,
          hp = CAUGHT_PATRAT_HP,
          xp = CAUGHT_PATRAT_XP,
          eVs = EVs(),
          iVs = IVs(),
          moves =
              listOf(
                  PokemonMove(PATRAT_TACKLE, PATRAT_TACKLE_PP),
                  PokemonMove(0, 0),
                  PokemonMove(0, 0),
                  PokemonMove(0, 0),
              ),
          isShiny = false,
          hasHiddenAbility = false,
          isAlpha = false,
          isSecret = false,
          isFatefulEncounter = false,
          isRaidEncounter = false,
          caughtAt = LocalDateTime.now(),
      )

  private fun playMoveAnimations(session: SessionContext, charId: Long, playerMove: Short) {
    // The move comes from whichever monster is currently on the field, not always the lead.
    val activeSlot = battleActiveSlot[charId] ?: 0
    val partyMon = characterStore.getCharacter(charId)?.pokemon?.getOrNull(activeSlot) ?: return
    // Decrement the used move's PP before the animation, matching the real turn order.
    val slot = partyMon.moves.indexOfFirst { it.id == playerMove }
    if (slot >= 0) {
      val newPp = (partyMon.moves[slot].pp - 1).coerceAtLeast(0).toByte()
      session.send(EntityMovePpPacket(partyMon.id, slot.toByte(), newPp))
    }
    session.send(BattleEntityMoveEventPacket(partyMon.id, playerMove, MOVE_EVENT_KIND, emptyList()))
    session.send(BattleEntityMoveEventPacket(WILD_MON_ID, ENEMY_MOVE, MOVE_EVENT_KIND, emptyList()))
  }

  private fun advanceTurn(session: SessionContext, charId: Long) {
    val turn = battleTurn.merge(charId, 1) { a, b -> a + b } ?: 1
    session.send(BattleTileMapPacket(groupId = turn.toShort(), slotTiles = null))
    session.send(BattleQueuedEventPacket(packed = ACTION_PROMPT))
  }

  fun onChatSend(event: PacketEvent<ChatMessageSendPacket>) {
    val session = event.session
    val text = (event.packet.message ?: event.packet.target).trim()
    when {
      text.equals(TEST_BATTLE_COMMAND, ignoreCase = true) -> startTestBattle(session)
      text.equals(CATCH_COMMAND, ignoreCase = true) -> {
        val charId = session.attributes[PLAYER_STATE]?.characterId ?: return
        catchWildMon(session, charId)
      }
    }
  }

  private fun startTestBattle(session: SessionContext) {
    val state = session.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val stored = characterStore.getCharacter(charId) ?: return
    val partyMon = stored.pokemon.firstOrNull()
    if (partyMon == null) {
      log.warn { "Cannot start test battle: char=$charId has no party monster" }
      return
    }
    log.info {
      "Starting test battle for char=$charId (${stored.info.name}): dex=${partyMon.dexId} vs wild Patrat"
    }
    battleTurn[charId] = 1
    battleActiveSlot[charId] = 0
    battleSeenActive[charId] = mutableSetOf(0)
    // Hide the overworld player entity while the battle scene is up, matching the real enter order.
    session.send(EntityPresencePacket(entityId = charId, status = PRESENCE_IN_BATTLE))
    // Tell the client which side is local so the battle bag knows which monster an item targets.
    // Without it, opening the bag crashes. Opcode 0x40 is left alone here, since re-sending it
    // would
    // wipe the balls out of the battle bag.
    session.send(BattleSidePacket(side = PLAYER_SIDE))
    session.send(
        BattleFieldStatePacket(
            playerName = stored.info.name,
            playerId = charId,
            playerParty = stored.pokemon.map { BattleFieldMon(it.id, it.dexId.toShort(), it.hp) },
            wildMon = BattleFieldMon(WILD_MON_ID, PATRAT, WILD_PATRAT_HP),
        ),
    )
    session.send(BattleTileMapPacket(groupId = 1, slotTiles = null))
    session.send(BattleQueuedEventPacket(packed = ACTION_PROMPT))
  }
}
