package de.fiereu.openmmo.server.game.domain

/** Clean-room Kotlin port of server/src/game/ShinyRoller.ts. */
object ShinyRoller {
  fun rollShiny(shinyRate: Int, rng: () -> Double = Math::random): Boolean {
    require(shinyRate > 0) { "shinyRate must be a positive finite number, got $shinyRate" }
    return kotlin.math.floor(rng() * shinyRate).toInt() == 0
  }

  fun shinyRateWithCharm(
      baseRate: Int,
      charmRate: Int? = null,
      hasShinyCharm: Boolean = false
  ): Int {
    if (!hasShinyCharm) return baseRate
    return if (charmRate != null && charmRate > 0) charmRate else baseRate
  }

  /** Public Gen III+ shiny check: low16(TID xor SID xor PID-high xor PID-low) < threshold. */
  fun isPidShiny(trainerId: Int, secretId: Int, personalityId: UInt, threshold: Int = 8): Boolean {
    val value =
        (trainerId and 0xffff) xor
            (secretId and 0xffff) xor
            ((personalityId.toLong().ushr(16).toInt()) and 0xffff) xor
            (personalityId.toInt() and 0xffff)
    return (value and 0xffff) < threshold
  }

  fun randomU32(rng: () -> Double = Math::random): UInt {
    return kotlin.math.floor(rng() * 0x1_0000_0000L.toDouble()).toLong().toUInt()
  }

  /**
   * Ratified deterministic path for server-created Pokemon: decide shininess from the PID. This
   * replaces the TS split between random rollShiny() and ShinyConfig's PID hash.
   */
  fun isPidSelectedShiny(
      personalityId: UInt,
      shinyRate: Int,
      charmRate: Int? = null,
      hasShinyCharm: Boolean = false,
      boosted: Boolean = false
  ): Boolean {
    var threshold = shinyRateWithCharm(shinyRate, charmRate, hasShinyCharm)
    require(threshold > 0) { "shinyRate must be positive, got $threshold" }
    if (boosted) threshold = maxOf(1, threshold / 2)

    var hash = personalityId
    hash = hash xor 0x5f3759dfu
    hash = (hash shl 13) or (hash shr 19)
    hash *= 0xcc9e2d51u
    return (hash % threshold.toUInt()) == 0u
  }
}
