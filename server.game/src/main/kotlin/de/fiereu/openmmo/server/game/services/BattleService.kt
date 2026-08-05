package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.BattleAction
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.net.game.packets.ChatMessageSendPacket
import de.fiereu.openmmo.net.game.packets.MapLoadedAckPacket
import de.fiereu.openmmo.net.game.packets.SocialListEntryAddPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionSelectPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleListEventDetail
import de.fiereu.openmmo.net.game.packets.battle.BattleListEventPacket
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnPromptPacket
import de.fiereu.openmmo.net.game.packets.battle.moves.MoveLearnReplyPacket
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.battle.BattleInstance
import de.fiereu.openmmo.server.game.battle.BattleMonState
import de.fiereu.openmmo.server.game.battle.BattlePacketEmitter
import de.fiereu.openmmo.server.game.battle.BattleRegistry
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.battle.BattleRewards
import de.fiereu.openmmo.server.game.battle.BattleRng
import de.fiereu.openmmo.server.game.battle.MoveLearner
import de.fiereu.openmmo.server.game.battle.StatCalculator
import de.fiereu.openmmo.server.game.battle.TurnEngine
import de.fiereu.openmmo.server.game.battle.WildMonFactory
import de.fiereu.openmmo.server.game.battle.notice
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.world.interest.InterestManager
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

private const val DEFAULT_WILD_DEX = 19
private const val DEFAULT_WILD_LEVEL = 3
private const val MIN_WILD_LEVEL = 2
private const val MAX_WILD_LEVEL = 100

private const val POKE_BALL_ITEM: Short = 5004

/** A prompt waiting for its answer, kept after the battle ends. */
private data class PendingMoveLearn(val entityId: Long, val offered: List<Short>)

/**
 * Orchestrates wild battles: builds the battle state from the party and a rolled wild monster,
 * routes client actions through the [TurnEngine], and persists the outcome. Packets go out through
 * the [BattlePacketEmitter] over the battle's interest key.
 */
@Singleton
class BattleService
@Inject
constructor(
    private val characterStore: CharacterStore,
    private val battles: BattleRegistry,
    private val engine: TurnEngine,
    private val wildMons: WildMonFactory,
    private val emitter: BattlePacketEmitter,
    private val rewards: BattleRewards,
    private val moveLearner: MoveLearner,
    private val interestManager: InterestManager,
    private val speciesRegistry: SpeciesRegistry,
    private val moveRegistry: MoveRegistry,
) {

  private val pendingLearns = ConcurrentHashMap<Long, PendingMoveLearn>()

  fun onBattlePacket(event: PacketEvent<*>) {
    log.info { "Battle packet ${event.packet::class.simpleName} received: ${event.packet}" }
  }

  fun onBattleAction(event: PacketEvent<BattleActionSelectPacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    val battle = battles.byChar(charId) ?: return
    if (battle.pendingResult != null) return
    val action = event.packet
    log.info { "Battle action char=$charId: $action" }
    // While the active mon is fainted the player owes a replacement and may only switch.
    if (battle.activeMon().fainted && action.action != BattleAction.SWITCH) return
    when (action.action) {
      BattleAction.MOVE -> resolveTurn(battle, action.moveOrItemId)
      BattleAction.ITEM -> catchWild(battle)
      BattleAction.SWITCH -> switchMon(battle, action.moveOrItemId)
      BattleAction.RUN -> flee(battle)
    }
  }

  /** Applies the moveset the player picked after a level up. */
  fun onMoveLearnReply(event: PacketEvent<MoveLearnReplyPacket>) {
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    val reply = event.packet
    val pending = pendingLearns[charId] ?: return
    if (pending.entityId != reply.entityId) return
    pendingLearns.remove(charId)
    val stored =
        characterStore.getCharacter(charId)?.pokemon?.firstOrNull { it.id == reply.entityId }
            ?: return
    val moves = stored.moves.toMutableList()
    if (!moveLearner.apply(moves, reply.moveIds, pending.offered)) {
      log.warn { "char=$charId picked an invalid moveset for ${reply.entityId}: ${reply.moveIds}" }
      return
    }
    if (moves == stored.moves) return
    characterStore.updatePokemon(charId, stored.copy(moves = moves))
    characterStore.flushCharacterAsync(charId)
    event.session.send(emitter.moveSlotsDelta(reply.entityId, moves.map { it.id to it.pp }, 0))
  }

  fun onChatSend(event: PacketEvent<ChatMessageSendPacket>) {
    val session = event.session
    val text = (event.packet.message ?: event.packet.target).trim()
    val parts = text.split(Regex("\\s+"))
    when {
      parts[0].equals(TEST_BATTLE_COMMAND, ignoreCase = true) -> {
        val dexId = parts.getOrNull(1)?.toIntOrNull() ?: DEFAULT_WILD_DEX
        val level =
            (parts.getOrNull(2)?.toIntOrNull() ?: DEFAULT_WILD_LEVEL).coerceIn(
                MIN_WILD_LEVEL, MAX_WILD_LEVEL)
        startWildBattle(session, dexId, level)
      }
      text.equals(CATCH_COMMAND, ignoreCase = true) -> {
        val charId = session.attributes[PLAYER_STATE]?.characterId ?: return
        battles.byChar(charId)?.let { catchWild(it) }
      }
    }
  }

  /** Ends a running battle when the player disconnects, keeping the last hp and pp state. */
  fun onDisconnect(session: SessionContext) {
    val charId = session.attributes[PLAYER_STATE]?.characterId ?: return
    pendingLearns.remove(charId)
    val battle = battles.byChar(charId) ?: return
    persistParty(battle)
    finishBattle(battle, BattleResult.DISCONNECTED)
  }

  /** Resumes scripts after returning to the overworld. */
  fun onClientReady(event: PacketEvent<MapLoadedAckPacket>) {
    if (event.packet.data.isNotEmpty()) return
    val charId = event.session.attributes[PLAYER_STATE]?.characterId ?: return
    val battle = battles.byChar(charId) ?: return
    val result = battle.pendingResult ?: return
    finishBattle(battle, result)
  }

  /** True while the character has a battle running, so callers can skip starting another. */
  fun inBattle(charId: Long): Boolean = battles.byChar(charId) != null

  fun startWildBattle(session: SessionContext, dexId: Int, level: Int) {
    createWildBattle(session, dexId, level, catchable = true, escapable = true)
  }

  /** Runs a story battle and waits for its scene. */
  suspend fun startScriptedBattle(
      session: SessionContext,
      dexId: Int,
      level: Int,
      moveIds: List<Int> = emptyList(),
  ): BattleResult {
    val battle =
        createWildBattle(
            session,
            dexId,
            level,
            catchable = false,
            escapable = false,
            moveIds = moveIds,
        ) ?: return BattleResult.FAILED
    return battle.completion.await()
  }

  private fun createWildBattle(
      session: SessionContext,
      dexId: Int,
      level: Int,
      catchable: Boolean,
      escapable: Boolean,
      moveIds: List<Int> = emptyList(),
  ): BattleInstance? {
    val charId = session.attributes[PLAYER_STATE]?.characterId ?: return null
    if (battles.byChar(charId) != null) {
      session.send(notice("You are already in a battle."))
      return null
    }
    val stored = characterStore.getCharacter(charId) ?: return null
    if (stored.pokemon.isEmpty()) {
      session.send(notice("You need a monster in your party to battle."))
      return null
    }
    val party = mutableListOf<BattleMonState>()
    for ((index, mon) in stored.pokemon.withIndex()) {
      val def = speciesRegistry.get(mon.dexId)
      if (def == null) {
        session.send(notice("Your party has a species the battle data does not cover yet."))
        return null
      }
      party += BattleMonState(mon.id, def, index, mon, StatCalculator.computeAll(def, mon))
    }
    if (party.all { it.fainted }) {
      session.send(notice("All of your monsters have fainted."))
      return null
    }
    val rng = BattleRng()
    var wildPokemon = wildMons.create(dexId, level, rng)
    if (wildPokemon == null) {
      session.send(notice("Unknown species $dexId."))
      return null
    }
    if (moveIds.isNotEmpty()) {
      wildPokemon =
          wildPokemon.copy(
              moves =
                  moveIds.take(4).map { id ->
                    PokemonMove(id.toShort(), (moveRegistry.get(id)?.pp ?: 0).toByte())
                  } + List((4 - moveIds.size).coerceAtLeast(0)) { PokemonMove(0, 0) })
    }
    val wildDef = speciesRegistry.get(dexId)!!
    val wild =
        BattleMonState(
            wildPokemon.id,
            wildDef,
            null,
            wildPokemon,
            StatCalculator.computeAll(wildDef, wildPokemon),
        )
    log.info {
      "Starting wild battle for char=$charId (${stored.info.name}): ${wildDef.name} level $level"
    }
    val battle = battles.create(charId, session, party, wild, rng, catchable, escapable)
    val firstAlive = party.indexOfFirst { !it.fainted }
    battle.activeSlot = firstAlive
    battle.seenActive.clear()
    battle.seenActive.add(firstAlive)
    interestManager.join(session, battle.key)
    emitter.sendStart(battle, stored.info.name)
    return battle
  }

  private fun resolveTurn(battle: BattleInstance, moveId: Short) {
    val events = engine.resolveTurn(battle, moveId)
    emitter.sendEvents(battle, events)
    afterTurn(battle)
  }

  private fun afterTurn(battle: BattleInstance) {
    when {
      battle.wild.fainted -> endVictory(battle)
      battle.party.all { it.fainted } -> endDefeat(battle)
      // The active mon fainted with a live backup. Open the switch screen instead of the action
      // prompt. The replacement arrives as a normal SWITCH action.
      battle.activeMon().fainted -> emitter.sendSwitchPrompt(battle)
      else -> {
        battle.turn += 1
        emitter.sendPrompt(battle)
      }
    }
  }

  private fun switchMon(battle: BattleInstance, partyIndex: Short) {
    val target = partyIndex.toInt()
    val mon = battle.party.getOrNull(target)
    val forced = battle.activeMon().fainted
    if (mon == null || mon.fainted || target == battle.activeSlot) {
      // Reopen the switch screen on an invalid forced choice, otherwise re-prompt for an action.
      if (forced) {
        emitter.sendSwitchPrompt(battle)
      } else {
        battle.turn += 1
        emitter.sendPrompt(battle)
      }
      return
    }
    // A forced switch confirms the choice before the switch-in. The captures pair the confirm with
    // a full block for a new mon and with a return block for a mon that was already active.
    if (forced) emitter.sendSwitchConfirm(battle)
    performSwitch(battle, target)
    if (forced) {
      // Replacing a fainted mon does not spend a turn, the new mon acts next.
      battle.turn += 1
      emitter.sendPrompt(battle)
    } else {
      // A voluntary switch spends the turn, so the wild attacks the incoming mon.
      emitter.sendEvents(battle, engine.resolveSwitchTurn(battle))
      afterTurn(battle)
    }
  }

  private fun performSwitch(battle: BattleInstance, target: Int) {
    val oldSlot = battle.activeSlot
    val fullBlock = target !in battle.seenActive
    battle.activeSlot = target
    battle.seenActive.add(target)
    log.info { "Switch char=${battle.charId} slot $oldSlot -> $target (fullBlock=$fullBlock)" }
    emitter.sendSwitchIn(battle, fullBlock)
  }

  private fun flee(battle: BattleInstance) {
    if (!battle.escapable) {
      emitter.sendNotice(battle, "You can't run from this battle.")
      emitter.sendPrompt(battle)
      return
    }
    emitter.sendFled(battle)
    persistParty(battle)
    battle.pendingResult = BattleResult.FLED
  }

  private fun catchWild(battle: BattleInstance) {
    if (!battle.catchable) {
      emitter.sendNotice(battle, "You can't catch this monster.")
      emitter.sendPrompt(battle)
      return
    }
    val stored = characterStore.getCharacter(battle.charId) ?: return
    val nextSlot = ((stored.pokemon.maxOfOrNull { it.containerSlot } ?: -1) + 1).toShort()
    val caught =
        battle.wild.source.copy(
            ownerId = battle.charId,
            container = PokemonContainer.PARTY,
            containerSlot = nextSlot,
            ot = stored.info.name,
            hp = battle.wild.currentHp.toShort(),
            moves = battle.wild.moves.map { PokemonMove(it.id, it.pp) },
            caughtAt = LocalDateTime.now(),
        )
    log.info { "Caught wild ${battle.wild.species.name} for char=${battle.charId}" }
    // The caught monster is sent as a full 148-byte record on opcode 0x14 before the ball-throw
    // event, so the client can resolve the monster when the throw lands.
    battle.session.send(SocialListEntryAddPacket(caught))
    // "Player threw a Poke Ball" event.
    battle.session.send(
        BattleListEventPacket(
            kind = 0,
            value = POKE_BALL_ITEM,
            subKind = 4,
            detail = BattleListEventDetail(listType = 1, value = 1),
        ),
    )
    characterStore.addPokemon(battle.charId, caught)
    endBattle(battle, BattleResult.CAUGHT)
  }

  private fun endVictory(battle: BattleInstance) {
    val winner = battle.activeMon()
    val reward = rewards.apply(winner, battle.wild.species, battle.wild.level)
    log.info {
      "char=${battle.charId} won: +${reward.xpGained} xp, level ${winner.level} -> ${reward.newLevel}"
    }
    winner.currentHp = reward.newCurrentHp
    val outcome =
        moveLearner.learn(winner.moves, winner.source.dexId, winner.level, reward.newLevel)
    emitter.sendVictoryDelta(battle, winner.entityId, reward)
    for (move in outcome.learned) {
      emitter.sendNotice(battle, "${winner.species.name} learned ${move.name}!")
    }
    if (outcome.offered.isNotEmpty()) {
      val offered = outcome.offered.map { it.moveId.toShort() }
      pendingLearns[battle.charId] = PendingMoveLearn(winner.entityId, offered)
      battle.session.send(MoveLearnPromptPacket(winner.entityId, offered))
    }
    characterStore.updatePokemon(
        battle.charId,
        winner.source.copy(
            level = reward.newLevel.toByte(),
            xp = reward.newXp,
            hp = reward.newCurrentHp.toShort(),
            eVs = reward.newEvs,
            moves = winner.moves.map { PokemonMove(it.id, it.pp) },
        ),
    )
    endBattle(battle, BattleResult.VICTORY, skip = winner.entityId)
  }

  private fun endDefeat(battle: BattleInstance) {
    endBattle(battle, BattleResult.DEFEAT)
  }

  // Known issue: the caught monster does not show up in the party until the client reopens it.
  private fun endBattle(
      battle: BattleInstance,
      result: BattleResult,
      skip: Long? = null,
  ) {
    persistParty(battle, skip)
    val party = characterStore.getCharacter(battle.charId)?.pokemon ?: emptyList()
    emitter.sendBattleEnd(battle, party)
    battle.pendingResult = result
  }

  /** Write the battle's live hp and pp back into the party and flush the character. */
  private fun persistParty(battle: BattleInstance, skip: Long? = null) {
    for (state in battle.party) {
      if (state.entityId == skip) continue
      val updated =
          state.source.copy(
              hp = state.currentHp.toShort(),
              moves = state.moves.map { PokemonMove(it.id, it.pp) },
          )
      characterStore.updatePokemon(battle.charId, updated)
    }
    characterStore.flushCharacterAsync(battle.charId)
  }

  private fun finishBattle(battle: BattleInstance, result: BattleResult) {
    interestManager.leave(battle.session, battle.key)
    battles.remove(battle.charId)
    battle.completion.complete(result)
  }
}
