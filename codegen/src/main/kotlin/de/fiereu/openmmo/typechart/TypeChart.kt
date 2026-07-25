package de.fiereu.openmmo.typechart

import de.fiereu.openmmo.common.enums.PokemonType
import de.fiereu.openmmo.typechart.generated.GeneratedTypeChart
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/** Gen 3 type matchups as fixed point multipliers per 10, so 5 halves and 20 doubles. */
@Singleton
class TypeChart @Inject constructor() {

  private val matchups = ConcurrentHashMap<Pair<PokemonType, PokemonType>, Int>()

  init {
    GeneratedTypeChart.loadInto(this)
  }

  fun register(attacker: PokemonType, defender: PokemonType, multiplier: Int) {
    matchups[attacker to defender] = multiplier
  }

  fun multiplier(attacker: PokemonType, defender: PokemonType): Int =
      matchups[attacker to defender] ?: NEUTRAL

  /** Combined multiplier per 10 against both defender types. A mono type counts once. */
  fun effectiveness(attacker: PokemonType, def1: PokemonType, def2: PokemonType): Int {
    val first = multiplier(attacker, def1)
    if (def2 == def1) return first
    return first * multiplier(attacker, def2) / NEUTRAL
  }

  companion object {
    const val NEUTRAL = 10
  }
}
