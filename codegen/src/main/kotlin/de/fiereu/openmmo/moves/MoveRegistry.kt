package de.fiereu.openmmo.moves

import de.fiereu.openmmo.moves.generated.GeneratedMoves
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoveRegistry @Inject constructor() {

  private val moves = ConcurrentHashMap<Int, MoveDef>()

  init {
    GeneratedMoves.loadInto(this)
  }

  fun register(move: MoveDef) {
    moves[move.id] = move
  }

  fun get(id: Int): MoveDef? = moves[id]

  fun all(): Collection<MoveDef> = moves.values

  fun size(): Int = moves.size
}
