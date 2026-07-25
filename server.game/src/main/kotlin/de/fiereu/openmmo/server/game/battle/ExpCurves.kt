package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.enums.GrowthRate

/**
 * The six Gen 3 growth curves as formulas. The decomp experience table is built from these same
 * macro invocations, so evaluating them directly matches it entry for entry.
 */
object ExpCurves {

  const val MAX_LEVEL = 100

  /** Total experience needed to reach a level. */
  fun totalXpFor(rate: GrowthRate, level: Int): Int {
    require(level in 0..MAX_LEVEL) { "Level $level out of range" }
    // The decomp table hardcodes the first two entries for every curve.
    if (level <= 1) return level
    val n = level
    val cube = n * n * n
    return when (rate) {
      GrowthRate.MEDIUM_FAST -> cube
      GrowthRate.SLOW -> 5 * cube / 4
      GrowthRate.FAST -> 4 * cube / 5
      GrowthRate.MEDIUM_SLOW -> 6 * cube / 5 - 15 * n * n + 100 * n - 140
      GrowthRate.ERRATIC ->
          when {
            n <= 50 -> (100 - n) * cube / 50
            n <= 68 -> (150 - n) * cube / 100
            n <= 98 -> ((1911 - 10 * n) / 3) * cube / 500
            else -> (160 - n) * cube / 100
          }
      GrowthRate.FLUCTUATING ->
          when {
            n <= 15 -> ((n + 1) / 3 + 24) * cube / 50
            n <= 36 -> (n + 14) * cube / 50
            else -> (n / 2 + 32) * cube / 50
          }
    }
  }

  /** The highest level whose total requirement fits the given xp. */
  fun levelFor(rate: GrowthRate, xp: Int): Int {
    var level = 1
    while (level < MAX_LEVEL && totalXpFor(rate, level + 1) <= xp) level++
    return level
  }
}
