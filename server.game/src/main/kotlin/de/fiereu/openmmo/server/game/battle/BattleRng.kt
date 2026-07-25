package de.fiereu.openmmo.server.game.battle

import kotlin.random.Random

/** All battle randomness goes through one seedable source, so turns replay in tests. */
class BattleRng(seed: Long = Random.nextLong()) {
  // Game randomness, not security sensitive. It is seedable on purpose so turns replay in tests.
  @Suppress("kotlin:S2245") private val random = Random(seed)

  fun natureSeed(): Int = random.nextInt()

  fun ivRoll(): Int = random.nextInt(32)

  fun accuracyRoll(): Int = random.nextInt(1, 101)

  fun damageRoll(): Int = random.nextInt(85, 101)

  fun critRoll(denominator: Int): Boolean = random.nextInt(denominator) == 0

  fun coinFlip(): Boolean = random.nextBoolean()

  fun pick(bound: Int): Int = random.nextInt(bound)
}
