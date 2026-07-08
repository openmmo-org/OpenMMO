package de.fiereu.openmmo.server.game.domain

import kotlin.math.floor
import kotlin.math.sqrt

enum class CatchStatus {
  NONE,
  SLEEP,
  FREEZE,
  PARALYSIS,
  BURN,
  POISON
}

data class CatchAttemptInput(
    val maxHp: Int,
    val currentHp: Int,
    val speciesCatchRate: Int,
    val ballBonus: Double = 1.0,
    val status: CatchStatus = CatchStatus.NONE,
    val rateModifier: Double = 1.0,
    val rng: () -> Double = Math::random,
)

data class CatchAttemptResult(
    val caught: Boolean,
    val modifiedRate: Int,
    val shakeProbability: Double,
    val shakes: Int,
)

/**
 * Clean-room catch math helpers.
 *
 * IMPORTANT: this is NOT the live catch-resolution path. Wild-battle catch throws resolve inside
 * Opus's battle sidecar/BattleSession, whose TypeScript CatchCalculator is authoritative for that
 * flow. Kotlin handlers must forward the sidecar's catch outcome and persist the pre-generated wild
 * mon, not re-run this math. Keep this library for non-battle callers and parity tests only.
 */
object CatchCalculator {
  fun modifiedCatchRate(input: CatchAttemptInput): Int {
    val maxHp = maxOf(1, input.maxHp)
    val currentHp = input.currentHp.coerceIn(1, maxHp)
    val catchRate = input.speciesCatchRate.coerceIn(1, 255)
    val hpFactor =
        floor(((3 * maxHp - 2 * currentHp) * catchRate * input.ballBonus) / (3.0 * maxHp)).toInt()
    return floor(hpFactor * statusCatchBonus(input.status) * input.rateModifier)
        .toInt()
        .coerceIn(1, 255)
  }

  fun shakeProbability(modifiedRate: Int): Double {
    val a = modifiedRate.coerceIn(1, 255)
    if (a >= 255) return 1.0
    val b = 1_048_560.0 / sqrt(sqrt(16_711_680.0 / a))
    return (b / 65_536.0).coerceIn(0.0, 1.0)
  }

  fun attemptCatch(input: CatchAttemptInput): CatchAttemptResult {
    val a = modifiedCatchRate(input)
    if (a >= 255)
        return CatchAttemptResult(
            caught = true, modifiedRate = a, shakeProbability = 1.0, shakes = 4)
    val p = shakeProbability(a)
    var shakes = 0
    for (i in 0 until 4) {
      if (input.rng() >= p) break
      shakes += 1
    }
    return CatchAttemptResult(
        caught = shakes == 4, modifiedRate = a, shakeProbability = p, shakes = shakes)
  }

  fun statusCatchBonus(status: CatchStatus): Double =
      when (status) {
        CatchStatus.SLEEP,
        CatchStatus.FREEZE -> 2.0
        CatchStatus.PARALYSIS,
        CatchStatus.BURN,
        CatchStatus.POISON -> 1.5
        CatchStatus.NONE -> 1.0
      }
}
