package de.fiereu.openmmo.server.game.domain

import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

data class ExpGainInput(
    val defeatedBaseExp: Int,
    val defeatedLevel: Int,
    val victorLevel: Int,
    val participated: Int,
    val trainerBattle: Boolean = false,
    val traded: Boolean = false,
    val luckyEgg: Boolean = false,
    val expShare: Boolean = false,
    val affectionBoost: Boolean = false,
    val serverMultiplier: Double = 1.0,
)

object ExpCalculator {
  fun expForLevel(growthRate: GrowthRate, level: Int): Int {
    val n = clampLevel(level)
    return when (growthRate) {
      GrowthRate.ERRATIC ->
          when {
            n <= 50 -> floor((n * n * n * (100 - n)) / 50.0).toInt()
            n <= 68 -> floor((n * n * n * (150 - n)) / 100.0).toInt()
            n <= 98 -> floor((n * n * n * floor((1911 - 10 * n) / 3.0)) / 500.0).toInt()
            else -> floor((n * n * n * (160 - n)) / 100.0).toInt()
          }
      GrowthRate.FAST -> floor((4 * n * n * n) / 5.0).toInt()
      GrowthRate.MEDIUM_FAST -> n * n * n
      GrowthRate.MEDIUM_SLOW -> floor((6.0 / 5.0) * n * n * n - 15 * n * n + 100 * n - 140).toInt()
      GrowthRate.SLOW -> floor((5 * n * n * n) / 4.0).toInt()
      GrowthRate.FLUCTUATING ->
          when {
            n <= 15 -> floor((n * n * n * (floor((n + 1) / 3.0) + 24)) / 50.0).toInt()
            n <= 36 -> floor((n * n * n * (n + 14)) / 50.0).toInt()
            else -> floor((n * n * n * (floor(n / 2.0) + 32)) / 50.0).toInt()
          }
    }
  }

  fun levelForExp(growthRate: GrowthRate, exp: Int): Int {
    var level = 1
    for (candidate in 2..100) {
      if (expForLevel(growthRate, candidate) > exp) break
      level = candidate
    }
    return level
  }

  fun expToNextLevel(growthRate: GrowthRate, level: Int, currentExp: Int): Int {
    if (level >= 100) return 0
    return maxOf(0, expForLevel(growthRate, level + 1) - currentExp)
  }

  /** Gen V-style scaled EXP, with server multiplier applied last. */
  fun calculateExpGain(input: ExpGainInput): Int {
    val participants = maxOf(1, input.participated)
    val battle = if (input.trainerBattle) 1.5 else 1.0
    val traded = if (input.traded) 1.5 else 1.0
    val luckyEgg = if (input.luckyEgg) 1.5 else 1.0
    val expShare = if (input.expShare) 0.5 else 1.0
    val affection = if (input.affectionBoost) 1.2 else 1.0
    val levelFactor =
        floor(
            sqrt(
                (2 * input.defeatedLevel + 10).toDouble().pow(2.5) /
                    (input.defeatedLevel + input.victorLevel + 10).toDouble().pow(2.5)) + 1,
        )
    val base = floor(input.defeatedBaseExp * input.defeatedLevel / 5.0 / participants).toInt()
    return maxOf(
        1,
        floor(
                base *
                    levelFactor *
                    battle *
                    traded *
                    luckyEgg *
                    expShare *
                    affection *
                    input.serverMultiplier)
            .toInt())
  }

  private fun clampLevel(level: Int): Int {
    require(level in 1..100) { "level must be an integer from 1..100, got $level" }
    return level
  }
}
