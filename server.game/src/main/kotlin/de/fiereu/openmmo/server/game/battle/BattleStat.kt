package de.fiereu.openmmo.server.game.battle

/** The stats a battle can stage up or down. HP has no stage. */
enum class BattleStat {
  ATTACK,
  DEFENSE,
  SP_ATTACK,
  SP_DEFENSE,
  SPEED,
  ACCURACY,
  EVASION,
}

/** The Gen 3 stage ratio tables for stages -6 to +6. */
object StatStages {
  const val MIN = -6
  const val MAX = 6

  private val STAT_NUM = intArrayOf(10, 10, 10, 10, 10, 10, 10, 15, 20, 25, 30, 35, 40)
  private val STAT_DEN = intArrayOf(40, 35, 30, 25, 20, 15, 10, 10, 10, 10, 10, 10, 10)

  // Applied to a move's accuracy for the combined accuracy minus evasion stage.
  private val ACC_NUM = intArrayOf(33, 36, 43, 50, 60, 75, 1, 133, 166, 2, 233, 133, 3)
  private val ACC_DEN = intArrayOf(100, 100, 100, 100, 100, 100, 1, 100, 100, 1, 100, 50, 1)

  fun scaleStat(value: Int, stage: Int): Int {
    val i = stage.coerceIn(MIN, MAX) - MIN
    return value * STAT_NUM[i] / STAT_DEN[i]
  }

  fun scaleAccuracy(accuracy: Int, stage: Int): Int {
    val i = stage.coerceIn(MIN, MAX) - MIN
    return accuracy * ACC_NUM[i] / ACC_DEN[i]
  }
}
