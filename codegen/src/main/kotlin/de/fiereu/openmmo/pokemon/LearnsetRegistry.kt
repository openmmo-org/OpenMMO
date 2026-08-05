package de.fiereu.openmmo.pokemon

import de.fiereu.openmmo.common.MAX_MOVE_SLOTS
import de.fiereu.openmmo.pokemon.generated.GeneratedLearnsets
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

data class LevelUpMove(val level: Int, val moveId: Int)

/** The level up learnsets from the decomp, keyed by national dex id and sorted by level. */
@Singleton
class LearnsetRegistry @Inject constructor() {

  private val learnsets = ConcurrentHashMap<Int, List<LevelUpMove>>()

  init {
    GeneratedLearnsets.loadInto(this)
  }

  fun register(dexId: Int, moves: List<LevelUpMove>) {
    learnsets[dexId] = moves
  }

  fun get(dexId: Int): List<LevelUpMove> = learnsets[dexId] ?: emptyList()

  fun movesAt(dexId: Int, level: Int): List<Int> =
      get(dexId).filter { it.level == level }.map { it.moveId }

  /**
   * The moveset a monster generated at this level starts with. Like the games, the learnset is
   * walked in order and a fifth move pushes out the oldest one.
   */
  fun initialMoveset(dexId: Int, level: Int): List<Int> {
    val slots = ArrayList<Int>(MAX_MOVE_SLOTS)
    for (entry in get(dexId)) {
      if (entry.level > level) break
      if (entry.moveId in slots) continue
      if (slots.size == MAX_MOVE_SLOTS) slots.removeAt(0)
      slots += entry.moveId
    }
    return slots
  }

  fun size(): Int = learnsets.size
}
