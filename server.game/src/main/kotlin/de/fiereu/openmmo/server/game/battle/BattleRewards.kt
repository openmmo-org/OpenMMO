package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.PokemonStat
import de.fiereu.openmmo.pokemon.SpeciesDef
import de.fiereu.openmmo.trainer.TrainerDef
import javax.inject.Inject
import javax.inject.Singleton

private const val EV_STAT_CAP = 252
private const val EV_TOTAL_CAP = 510

// The flat multiplier the decomp applies on top of the class rate.
private const val PRIZE_PER_LEVEL = 4

data class RewardResult(
    val xpGained: Int,
    val newXp: Int,
    val newLevel: Int,
    val leveled: Boolean,
    val newStats: ComputedStats,
    val newCurrentHp: Int,
    val newEvs: EVs,
)

/** Experience and EV rewards for a won wild battle. */
@Singleton
class BattleRewards @Inject constructor() {

  /**
   * The Gen 3 wild battle experience. The live server is on a later formula and pays more, and
   * staying on Gen 3 is deliberate.
   */
  fun wildXp(defeated: SpeciesDef, defeatedLevel: Int): Int = defeated.expYield * defeatedLevel / 7

  /** The Gen 3 payout for beating a trainer, off the level of the last monster it sent out. */
  fun trainerPrize(trainer: TrainerDef, lastLevel: Int): Int =
      PRIZE_PER_LEVEL * lastLevel * trainer.prizeRate

  /** A trainer's monster is worth half again as much, the way Gen 3 pays it. */
  fun trainerXp(defeated: SpeciesDef, defeatedLevel: Int): Int =
      wildXp(defeated, defeatedLevel) * 3 / 2

  fun apply(
      winner: BattleMonState,
      defeated: SpeciesDef,
      defeatedLevel: Int,
      fromTrainer: Boolean = false,
  ): RewardResult {
    val gained =
        if (fromTrainer) trainerXp(defeated, defeatedLevel) else wildXp(defeated, defeatedLevel)
    val rate = winner.species.growthRate
    val cap = ExpCurves.totalXpFor(rate, ExpCurves.MAX_LEVEL)
    val newXp = minOf(winner.source.xp + gained, cap)
    val newLevel = maxOf(winner.level, ExpCurves.levelFor(rate, newXp))
    val leveled = newLevel > winner.level
    val newEvs = addYields(winner.source.eVs, defeated)
    val grown = winner.source.copy(level = newLevel.toByte(), eVs = newEvs)
    // Stats only move on a level up. New EVs are banked until then, as Gen 3 does, and the client
    // is only told about stats when it is told about the level, so moving them apart desyncs it.
    val newStats = if (leveled) StatCalculator.computeAll(winner.species, grown) else winner.stats
    // A level up raises the maximum, the missing hp stays missing.
    val newCurrentHp =
        (winner.currentHp + maxOf(0, newStats.hp - winner.stats.hp)).coerceAtMost(newStats.hp)
    return RewardResult(gained, newXp, newLevel, leveled, newStats, newCurrentHp, newEvs)
  }

  private fun addYields(current: EVs, defeated: SpeciesDef): EVs {
    val result = EVs()
    for (stat in PokemonStat.entries) {
      result.assign(stat, current.value(stat))
    }
    val yields =
        listOf(
            PokemonStat.HP to defeated.evYieldHp,
            PokemonStat.ATTACK to defeated.evYieldAttack,
            PokemonStat.DEFENSE to defeated.evYieldDefense,
            PokemonStat.SP_ATTACK to defeated.evYieldSpAttack,
            PokemonStat.SP_DEFENSE to defeated.evYieldSpDefense,
            PokemonStat.SPEED to defeated.evYieldSpeed,
        )
    for ((stat, yield) in yields) {
      if (yield <= 0) continue
      val value = result.value(stat)
      val room = minOf(EV_STAT_CAP - value, EV_TOTAL_CAP - result.total)
      if (room <= 0) continue
      result.assign(stat, value + minOf(yield, room))
    }
    return result
  }
}

private fun EVs.value(stat: PokemonStat): Int =
    when (stat) {
      PokemonStat.HP -> hp
      PokemonStat.ATTACK -> atk
      PokemonStat.DEFENSE -> def
      PokemonStat.SP_ATTACK -> spAtk
      PokemonStat.SP_DEFENSE -> spDef
      PokemonStat.SPEED -> spd
    }

private fun EVs.assign(stat: PokemonStat, value: Int) {
  when (stat) {
    PokemonStat.HP -> hp = value
    PokemonStat.ATTACK -> atk = value
    PokemonStat.DEFENSE -> def = value
    PokemonStat.SP_ATTACK -> spAtk = value
    PokemonStat.SP_DEFENSE -> spDef = value
    PokemonStat.SPEED -> spd = value
  }
}
