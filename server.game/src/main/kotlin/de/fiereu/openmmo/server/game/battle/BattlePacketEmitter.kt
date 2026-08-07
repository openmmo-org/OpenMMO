package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.enums.ChatType
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.Language
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.net.game.packets.ChatMessagePacket
import de.fiereu.openmmo.net.game.packets.EntityMovePpPacket
import de.fiereu.openmmo.net.game.packets.EntityPresencePacket
import de.fiereu.openmmo.net.game.packets.PokemonContainerPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleActionEvent
import de.fiereu.openmmo.net.game.packets.battle.BattleBulkStatePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEffectTarget
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityDeltaPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEntityMoveEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleEventBody
import de.fiereu.openmmo.net.game.packets.battle.BattleFieldStatePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleQueuedEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSidePacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSlotEventEnumPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSlotFlagEventPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleStatCountersPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleSwitchInPacket
import de.fiereu.openmmo.net.game.packets.battle.BattleTileMapPacket
import de.fiereu.openmmo.net.game.packets.battle.Experience
import de.fiereu.openmmo.net.game.packets.battle.MoveSlots
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.typechart.TypeChart
import javax.inject.Inject
import javax.inject.Singleton

private const val ACTION_PROMPT: Byte = -128 // 0x80
private const val MOVE_EVENT_KIND: Byte = 1
// Slot-event type shown when the player gets away from a wild battle.
private const val FLED_EVENT: Byte = 0

// The player's overworld entity is hidden while the battle scene is up and shown again when it
// ends.
private const val PRESENCE_IN_BATTLE: Byte = 1
private const val PRESENCE_OVERWORLD: Byte = 0

// The active battle side reported to the client so the bag knows which monster an item targets.
private const val PLAYER_SIDE: Byte = 1

// The target move short the live server sends for each event. Meaning unknown, but it is fixed per
// event type in every capture: a hit carries 0x0200, other events carry 0.
private const val HP_TARGET_MOVE: Short = 0x0200
private const val DEFAULT_TARGET_MOVE: Short = 0

/**
 * Turns battle state and [BattleEvent]s into packets. Everything battle wide goes through the
 * battle's interest key, so spectators and later trainer opponents receive it too. Packets tied to
 * one viewer (side, bag, party sync) go to that session directly.
 */
@Singleton
class BattlePacketEmitter @Inject constructor(private val interestManager: InterestManager) {

  fun sendStart(battle: BattleInstance, playerName: String) {
    battle.session.send(EntityPresencePacket(entityId = battle.charId, status = PRESENCE_IN_BATTLE))
    // Tell the client which side is local so the battle bag knows which monster an item targets.
    // Without it, opening the bag crashes. Opcode 0x40 is left alone here, since re-sending it
    // would wipe the balls out of the battle bag.
    battle.session.send(BattleSidePacket(side = PLAYER_SIDE))
    broadcast(
        battle,
        BattleFieldStatePacket(
            playerName = playerName,
            playerId = battle.charId,
            playerParty = battle.party.mapIndexed { slot, mon -> mon.toBlock(slot, true) },
            activeSlot = battle.activeSlot,
            wildParty = listOf(battle.wild.toBlock(slot = 0, movesPresent = false)),
        ),
    )
    sendPrompt(battle)
  }

  fun sendEvents(battle: BattleInstance, events: List<BattleEvent>) {
    var i = 0
    while (i < events.size) {
      val event = events[i]
      when (event) {
        is BattleEvent.MoveUsed -> {
          if (event.attackerId != battle.wild.entityId) {
            battle.session.send(
                EntityMovePpPacket(
                    event.attackerId, event.moveSlot.toByte(), event.ppLeft.toByte()))
          }
          // The move's outcome rides inside the move event so the client animates it. A hit carries
          // the target's resulting hp, a stat change carries the affected stat and its signed stage
          // delta. A capped change reports no delta, so it stays unanimated.
          val targets =
              when (val next = events.getOrNull(i + 1)) {
                is BattleEvent.DamageDealt -> {
                  i++
                  val subEvents =
                      mutableListOf(
                          BattleActionEvent(
                              null, null, BattleEventBody.HpUpdate(next.newHp.toShort())))
                  // Type 4 is assumed to be the "super effective" line. Not very effective and no
                  // effect ride other event ids we have not identified yet.
                  if (next.effectiveness > TypeChart.NEUTRAL) {
                    subEvents += BattleActionEvent(null, null, BattleEventBody.EffectivenessMessage)
                  }
                  // The client faints the target on hp reaching 0, as the real server does, so no
                  // faint sub-event is sent here.
                  listOf(BattleEffectTarget(next.targetId, HP_TARGET_MOVE, subEvents))
                }
                is BattleEvent.StageChanged ->
                    if (!next.failed) {
                      i++
                      listOf(
                          target(
                              next.targetId,
                              DEFAULT_TARGET_MOVE,
                              BattleEventBody.StatChange(
                                  statIndex(next.stat), next.delta.toShort())))
                    } else {
                      emptyList()
                    }
                is BattleEvent.MoveWithoutTarget -> {
                  i++
                  listOf(failTarget(battle, event.attackerId, next.moveId))
                }
                else -> emptyList()
              }
          broadcast(
              battle,
              BattleEntityMoveEventPacket(event.attackerId, event.moveId, MOVE_EVENT_KIND, targets))
        }
        is BattleEvent.DamageDealt -> Unit
        is BattleEvent.StageChanged -> Unit
        is BattleEvent.Fainted -> Unit
        is BattleEvent.MoveWithoutTarget -> Unit
      }
      i++
    }
  }

  fun sendSwitchIn(battle: BattleInstance, fullBlock: Boolean) {
    broadcast(
        battle,
        BattleSwitchInPacket(
            // These are field positions, not party slots: a newly revealed mon enters at field slot
            // 1, one already seen returns to slot 0. The party slot itself rides in the block.
            newSlot = if (fullBlock) 1 else 0,
            oldSlot = if (fullBlock) 0 else 1,
            mon = battle.activeMon().toBlock(slot = battle.activeSlot, movesPresent = true),
            fullBlock = fullBlock,
        ),
    )
  }

  fun sendPrompt(battle: BattleInstance) {
    broadcast(battle, BattleTileMapPacket(groupId = battle.turn.toShort(), slotTiles = null))
    broadcast(battle, BattleQueuedEventPacket(packed = ACTION_PROMPT))
  }

  /** Opens the party switch screen after the active mon faints, in place of the action prompt. */
  fun sendSwitchPrompt(battle: BattleInstance) {
    broadcast(battle, BattleSlotFlagEventPacket(slot = 0, flag = false, immediate = false))
  }

  /** Confirms the forced replacement choice just before its switch-in. */
  fun sendSwitchConfirm(battle: BattleInstance) {
    broadcast(battle, BattleSlotFlagEventPacket(slot = 0, flag = false, immediate = true))
  }

  fun sendFled(battle: BattleInstance) {
    broadcast(battle, BattleSlotEventEnumPacket(slot = 0, eventType = FLED_EVENT))
    broadcast(battle, BattleBulkStatePacket.fled())
    battle.session.send(EntityPresencePacket(entityId = battle.charId, status = PRESENCE_OVERWORLD))
  }

  fun sendBattleEnd(battle: BattleInstance, party: List<Pokemon>) {
    broadcast(battle, BattleBulkStatePacket.battleEnd())
    battle.session.send(EntityPresencePacket(entityId = battle.charId, status = PRESENCE_OVERWORLD))
    battle.session.send(
        PokemonContainerPacket(
            container = PokemonContainer.PARTY,
            hasChange = true,
            delete = false,
            pokemon = party,
        ),
    )
  }

  fun sendVictoryDelta(battle: BattleInstance, entityId: Long, reward: RewardResult) {
    broadcast(
        battle,
        BattleEntityDeltaPacket(
            entityId = entityId,
            experience = Experience(reward.newLevel.toByte(), reward.newXp),
        ),
    )
    // The delta above moves the bar, the reward text reads its number from here.
    broadcast(battle, experienceReward(entityId, reward.xpGained))
    if (!reward.leveled) return
    broadcast(
        battle,
        BattleEntityDeltaPacket(
            entityId = entityId,
            statValues = reward.newStats.asWireList(),
            currentHp = reward.newCurrentHp.toShort(),
            evValues = reward.newEvs.asWireList(),
        ),
    )
  }

  // The other six counters are rewards we do not award yet.
  private fun experienceReward(entityId: Long, gained: Int): BattleStatCountersPacket =
      BattleStatCountersPacket(
          entityId = entityId,
          baseCounter = gained,
          counter1 = null,
          counter2 = null,
          counter3 = null,
          counter4 = null,
          counter5 = null,
          counter6 = null,
      )

  fun sendNotice(battle: BattleInstance, message: String) {
    battle.session.send(notice(message))
  }

  private fun target(entityId: Long, targetMove: Short, body: BattleEventBody): BattleEffectTarget =
      BattleEffectTarget(entityId, targetMove, listOf(BattleActionEvent(null, null, body)))

  // A missed or failed move carries no target of its own, so it lands on the attacker's opponent.
  private fun failTarget(
      battle: BattleInstance,
      attackerId: Long,
      moveId: Short
  ): BattleEffectTarget {
    val opponent =
        if (attackerId == battle.wild.entityId) battle.activeMon().entityId
        else battle.wild.entityId
    return target(opponent, DEFAULT_TARGET_MOVE, BattleEventBody.MoveFailed(moveId))
  }

  fun broadcast(battle: BattleInstance, packet: Any) {
    interestManager.broadcast(battle.key, packet)
  }

  /** The delta that tells the client a monster's moveset changed. */
  fun moveSlotsDelta(
      entityId: Long,
      moveSlots: List<Pair<Short, Byte>>,
      ppUps: Byte,
  ): BattleEntityDeltaPacket =
      BattleEntityDeltaPacket(entityId = entityId, moves = MoveSlots(moveSlots, ppUps))

  // The decomp battle stat order. Not verified against the live client yet.
  private fun statIndex(stat: BattleStat): Byte =
      when (stat) {
        BattleStat.ATTACK -> 1
        BattleStat.DEFENSE -> 2
        BattleStat.SPEED -> 3
        BattleStat.SP_ATTACK -> 4
        BattleStat.SP_DEFENSE -> 5
        BattleStat.ACCURACY -> 6
        BattleStat.EVASION -> 7
      }
}

// The decomp in-game stat order. Not verified against the live client yet.
private fun statOrder(hp: Int, atk: Int, def: Int, spd: Int, spAtk: Int, spDef: Int): List<Short> =
    listOf(
        hp.toShort(), atk.toShort(), def.toShort(), spd.toShort(), spAtk.toShort(), spDef.toShort())

internal fun ComputedStats.asWireList(): List<Short> = statOrder(hp, atk, def, spd, spAtk, spDef)

private fun EVs.asWireList(): List<Short> = statOrder(hp, atk, def, spd, spAtk, spDef)

fun notice(message: String): ChatMessagePacket =
    ChatMessagePacket(
        type = ChatType.GAME_NOTIFICATIONS,
        language = Language.EN,
        message = message,
        sender = "",
    )
