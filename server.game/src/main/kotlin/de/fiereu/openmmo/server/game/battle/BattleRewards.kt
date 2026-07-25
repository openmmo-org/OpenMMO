package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.PokemonStat
import de.fiereu.openmmo.pokemon.SpeciesDef
import javax.inject.Inject
import javax.inject.Singleton

private const val EV_STAT_CAP = 252
private const val EV_TOTAL_CAP = 510

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

  /** The Gen 3 wild battle experience: base yield times level over seven. */
  fun wildXp(defeated: SpeciesDef, defeatedLevel: Int): Int = defeated.expYield * defeatedLevel / 7

  fun apply(winner: BattleMonState, defeated: SpeciesDef, defeatedLevel: Int): RewardResult {
    val gained = wildXp(defeated, defeatedLevel)
    val rate = winner.species.growthRate
    val cap = ExpCurves.totalXpFor(rate, ExpCurves.MAX_LEVEL)
    val newXp = minOf(winner.source.xp + gained, cap)
    val newLevel = maxOf(winner.level, ExpCurves.levelFor(rate, newXp))
    val leveled = newLevel > winner.level
    val newEvs = addYields(winner.source.eVs, defeated)
    val grown = winner.source.copy(level = newLevel.toByte(), eVs = newEvs)
    val newStats = StatCalculator.computeAll(winner.species, grown)
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
