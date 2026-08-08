package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.enums.MoveEffect
import de.fiereu.openmmo.moves.MoveDef
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.typechart.TypeChart
import javax.inject.Inject
import javax.inject.Singleton

private const val CRIT_DENOMINATOR = 16
private const val TACKLE_ID = 33

sealed interface BattleEvent {
  data class MoveUsed(val attackerId: Long, val moveId: Short, val moveSlot: Int, val ppLeft: Int) :
      BattleEvent

  /** A move that reached no target of its own, so its message lands on the opponent. */
  sealed interface MoveWithoutTarget : BattleEvent {
    val attackerId: Long
    val moveId: Short
  }

  data class MoveMissed(override val attackerId: Long, override val moveId: Short) :
      MoveWithoutTarget

  data class MoveFailed(override val attackerId: Long, override val moveId: Short) :
      MoveWithoutTarget

  data class DamageDealt(
      val targetId: Long,
      val newHp: Int,
      val crit: Boolean,
      val effectiveness: Int,
  ) : BattleEvent

  data class StageChanged(
      val targetId: Long,
      val stat: BattleStat,
      val stage: Int,
      val effectiveValue: Int,
      val delta: Int,
      val failed: Boolean,
  ) : BattleEvent

  data class Fainted(val targetId: Long) : BattleEvent
}

private data class TurnAction(
    val attacker: BattleMonState,
    val defender: BattleMonState,
    val move: MoveDef?,
)

private data class StageEffect(val stat: BattleStat, val delta: Int, val onSelf: Boolean)

/**
 * Resolves one wild battle turn with the Gen 3 core rules: priority and speed order, accuracy
 * stages, the damage formula with crit, STAB, and type chart, and stat stage moves. Effects outside
 * that core fail gracefully without touching state.
 */
@Singleton
class TurnEngine
@Inject
constructor(
    private val moves: MoveRegistry,
    private val typeChart: TypeChart,
) {

  fun resolveTurn(battle: BattleInstance, playerMoveId: Short): List<BattleEvent> {
    val events = mutableListOf<BattleEvent>()
    val player = battle.activeMon()
    val enemy = battle.opponentMon()

    val playerAction = TurnAction(player, enemy, moves.get(playerMoveId.toInt()))
    val enemyAction = TurnAction(enemy, player, pickEnemyMove(battle, enemy))

    for (action in order(battle, playerAction, enemyAction)) {
      if (action.attacker.fainted) continue
      execute(battle, action, events)
      if (player.fainted || enemy.fainted) break
    }
    return events
  }

  /** A voluntary switch spends the player's turn, so the enemy attacks the incoming monster. */
  fun resolveSwitchTurn(battle: BattleInstance): List<BattleEvent> {
    val events = mutableListOf<BattleEvent>()
    val enemy = battle.opponentMon()
    if (enemy.fainted) return events
    execute(battle, TurnAction(enemy, battle.activeMon(), pickEnemyMove(battle, enemy)), events)
    return events
  }

  /** The enemy AI picks a random usable move, falling back to Tackle with no pp left. */
  private fun pickEnemyMove(battle: BattleInstance, enemy: BattleMonState): MoveDef? {
    val usable = enemy.moves.filter { it.id.toInt() != 0 && it.pp > 0 }
    if (usable.isEmpty()) return moves.get(TACKLE_ID)
    return moves.get(usable[battle.rng.pick(usable.size)].id.toInt())
  }

  private fun order(
      battle: BattleInstance,
      a: TurnAction,
      b: TurnAction,
  ): List<TurnAction> {
    val pa = a.move?.priority ?: 0
    val pb = b.move?.priority ?: 0
    if (pa != pb) return if (pa > pb) listOf(a, b) else listOf(b, a)
    val sa = a.attacker.effective(BattleStat.SPEED)
    val sb = b.attacker.effective(BattleStat.SPEED)
    if (sa != sb) return if (sa > sb) listOf(a, b) else listOf(b, a)
    return if (battle.rng.coinFlip()) listOf(a, b) else listOf(b, a)
  }

  private fun execute(
      battle: BattleInstance,
      action: TurnAction,
      events: MutableList<BattleEvent>
  ) {
    val attacker = action.attacker
    val move = action.move
    if (move == null) {
      events += BattleEvent.MoveFailed(attacker.entityId, 0)
      return
    }
    val moveId = move.id.toShort()
    val slot = attacker.moves.indexOfFirst { it.id == moveId }
    if (slot >= 0 && attacker.moves[slot].pp > 0) {
      attacker.moves[slot].pp = (attacker.moves[slot].pp - 1).toByte()
    }
    events +=
        BattleEvent.MoveUsed(
            attacker.entityId,
            moveId,
            slot.coerceAtLeast(0),
            attacker.moves.getOrNull(slot)?.pp?.toInt() ?: 0)

    if (!accuracyCheck(battle, action, move)) {
      events += BattleEvent.MoveMissed(attacker.entityId, moveId)
      return
    }

    val stage = stageEffect(move.effect)
    when {
      move.power > 0 -> damage(battle, action, move, events)
      stage != null -> applyStage(action, stage, events)
      else -> events += BattleEvent.MoveFailed(attacker.entityId, moveId)
    }
  }

  private fun accuracyCheck(battle: BattleInstance, action: TurnAction, move: MoveDef): Boolean {
    if (move.accuracy == 0) return true
    val stage =
        action.attacker.stage(BattleStat.ACCURACY) - action.defender.stage(BattleStat.EVASION)
    val threshold = StatStages.scaleAccuracy(move.accuracy, stage)
    return battle.rng.accuracyRoll() <= threshold
  }

  private fun damage(
      battle: BattleInstance,
      action: TurnAction,
      move: MoveDef,
      events: MutableList<BattleEvent>,
  ) {
    val attacker = action.attacker
    val defender = action.defender
    val eff = typeChart.effectiveness(move.type, defender.species.type1, defender.species.type2)
    if (eff == 0) {
      events += BattleEvent.MoveFailed(attacker.entityId, move.id.toShort())
      return
    }
    val physical = MoveCategory.isPhysical(move.type)
    val atkStat = if (physical) BattleStat.ATTACK else BattleStat.SP_ATTACK
    val defStat = if (physical) BattleStat.DEFENSE else BattleStat.SP_DEFENSE
    val crit = battle.rng.critRoll(CRIT_DENOMINATOR)
    // A crit ignores the attacker's negative stages and the defender's positive stages.
    val atk =
        if (crit && attacker.stage(atkStat) < 0) attacker.unstaged(atkStat)
        else attacker.effective(atkStat)
    val def =
        if (crit && defender.stage(defStat) > 0) defender.unstaged(defStat)
        else defender.effective(defStat)

    var dmg = (2 * attacker.level / 5 + 2) * move.power * atk / def / 50 + 2
    if (crit) dmg *= 2
    if (attacker.species.hasType(move.type)) dmg = dmg * 3 / 2
    dmg = dmg * eff / TypeChart.NEUTRAL
    dmg = dmg * battle.rng.damageRoll() / 100
    if (dmg < 1) dmg = 1

    defender.currentHp = (defender.currentHp - dmg).coerceAtLeast(0)
    events += BattleEvent.DamageDealt(defender.entityId, defender.currentHp, crit, eff)
    if (defender.fainted) {
      events += BattleEvent.Fainted(defender.entityId)
      return
    }
    secondaryEffect(move.effect)?.let { secondary ->
      if (move.secondaryEffectChance > 0 &&
          battle.rng.accuracyRoll() <= move.secondaryEffectChance) {
        applyStage(action, secondary, events)
      }
    }
  }

  private fun applyStage(
      action: TurnAction,
      effect: StageEffect,
      events: MutableList<BattleEvent>,
  ) {
    val target = if (effect.onSelf) action.attacker else action.defender
    val applied = target.changeStage(effect.stat, effect.delta)
    val value =
        when (effect.stat) {
          BattleStat.ACCURACY,
          BattleStat.EVASION -> 0
          else -> target.effective(effect.stat)
        }
    events +=
        BattleEvent.StageChanged(
            target.entityId,
            effect.stat,
            target.stage(effect.stat),
            value,
            effect.delta,
            applied == 0)
  }

  private fun stageEffect(effect: MoveEffect): StageEffect? =
      when (effect) {
        MoveEffect.ATTACK_UP -> StageEffect(BattleStat.ATTACK, 1, true)
        MoveEffect.DEFENSE_UP -> StageEffect(BattleStat.DEFENSE, 1, true)
        MoveEffect.SPEED_UP -> StageEffect(BattleStat.SPEED, 1, true)
        MoveEffect.SPECIAL_ATTACK_UP -> StageEffect(BattleStat.SP_ATTACK, 1, true)
        MoveEffect.SPECIAL_DEFENSE_UP -> StageEffect(BattleStat.SP_DEFENSE, 1, true)
        MoveEffect.ACCURACY_UP -> StageEffect(BattleStat.ACCURACY, 1, true)
        MoveEffect.EVASION_UP -> StageEffect(BattleStat.EVASION, 1, true)
        MoveEffect.ATTACK_UP_2 -> StageEffect(BattleStat.ATTACK, 2, true)
        MoveEffect.DEFENSE_UP_2 -> StageEffect(BattleStat.DEFENSE, 2, true)
        MoveEffect.SPEED_UP_2 -> StageEffect(BattleStat.SPEED, 2, true)
        MoveEffect.SPECIAL_ATTACK_UP_2 -> StageEffect(BattleStat.SP_ATTACK, 2, true)
        MoveEffect.SPECIAL_DEFENSE_UP_2 -> StageEffect(BattleStat.SP_DEFENSE, 2, true)
        MoveEffect.ACCURACY_UP_2 -> StageEffect(BattleStat.ACCURACY, 2, true)
        MoveEffect.EVASION_UP_2 -> StageEffect(BattleStat.EVASION, 2, true)
        MoveEffect.ATTACK_DOWN -> StageEffect(BattleStat.ATTACK, -1, false)
        MoveEffect.DEFENSE_DOWN -> StageEffect(BattleStat.DEFENSE, -1, false)
        MoveEffect.SPEED_DOWN -> StageEffect(BattleStat.SPEED, -1, false)
        MoveEffect.SPECIAL_ATTACK_DOWN -> StageEffect(BattleStat.SP_ATTACK, -1, false)
        MoveEffect.SPECIAL_DEFENSE_DOWN -> StageEffect(BattleStat.SP_DEFENSE, -1, false)
        MoveEffect.ACCURACY_DOWN -> StageEffect(BattleStat.ACCURACY, -1, false)
        MoveEffect.EVASION_DOWN -> StageEffect(BattleStat.EVASION, -1, false)
        MoveEffect.ATTACK_DOWN_2 -> StageEffect(BattleStat.ATTACK, -2, false)
        MoveEffect.DEFENSE_DOWN_2 -> StageEffect(BattleStat.DEFENSE, -2, false)
        MoveEffect.SPEED_DOWN_2 -> StageEffect(BattleStat.SPEED, -2, false)
        MoveEffect.SPECIAL_ATTACK_DOWN_2 -> StageEffect(BattleStat.SP_ATTACK, -2, false)
        MoveEffect.SPECIAL_DEFENSE_DOWN_2 -> StageEffect(BattleStat.SP_DEFENSE, -2, false)
        MoveEffect.ACCURACY_DOWN_2 -> StageEffect(BattleStat.ACCURACY, -2, false)
        MoveEffect.EVASION_DOWN_2 -> StageEffect(BattleStat.EVASION, -2, false)
        else -> null
      }

  private fun secondaryEffect(effect: MoveEffect): StageEffect? =
      when (effect) {
        MoveEffect.ATTACK_DOWN_HIT -> StageEffect(BattleStat.ATTACK, -1, false)
        MoveEffect.DEFENSE_DOWN_HIT -> StageEffect(BattleStat.DEFENSE, -1, false)
        MoveEffect.SPEED_DOWN_HIT -> StageEffect(BattleStat.SPEED, -1, false)
        MoveEffect.SPECIAL_ATTACK_DOWN_HIT -> StageEffect(BattleStat.SP_ATTACK, -1, false)
        MoveEffect.SPECIAL_DEFENSE_DOWN_HIT -> StageEffect(BattleStat.SP_DEFENSE, -1, false)
        MoveEffect.ACCURACY_DOWN_HIT -> StageEffect(BattleStat.ACCURACY, -1, false)
        MoveEffect.EVASION_DOWN_HIT -> StageEffect(BattleStat.EVASION, -1, false)
        MoveEffect.ATTACK_UP_HIT -> StageEffect(BattleStat.ATTACK, 1, true)
        MoveEffect.DEFENSE_UP_HIT -> StageEffect(BattleStat.DEFENSE, 1, true)
        else -> null
      }
}
