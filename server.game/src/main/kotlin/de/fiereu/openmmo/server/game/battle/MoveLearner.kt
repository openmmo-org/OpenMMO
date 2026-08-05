package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.MAX_MOVE_SLOTS
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.pokemon.LearnsetRegistry
import javax.inject.Inject
import javax.inject.Singleton

/** A move learned on level up, ready to be announced to the player. */
data class LearnedMove(val level: Int, val moveId: Int, val name: String)

/** [learned] went into a free slot, [offered] needs the player to drop a move first. */
data class MoveLearnOutcome(
    val learned: List<LearnedMove>,
    val offered: List<LearnedMove>,
)

/** Teaches the level up moves a monster gained from a level range. */
@Singleton
class MoveLearner
@Inject
constructor(
    private val learnsets: LearnsetRegistry,
    private val moves: MoveRegistry,
) {

  /** Adds every move learned above [fromLevel] up to [toLevel] that fits a free slot. */
  fun learn(
      known: MutableList<PokemonMove>,
      dexId: Int,
      fromLevel: Int,
      toLevel: Int,
  ): MoveLearnOutcome {
    val learned = mutableListOf<LearnedMove>()
    val offered = mutableListOf<LearnedMove>()
    for (level in fromLevel + 1..toLevel) {
      for (moveId in learnsets.movesAt(dexId, level)) {
        if (known.any { it.id.toInt() == moveId }) continue
        if (offered.any { it.moveId == moveId }) continue
        val def = moves.get(moveId) ?: continue
        val entry = LearnedMove(level, moveId, def.name)
        val slot = known.indexOfFirst { it.id.toInt() == 0 }
        if (slot < 0 && known.size >= MAX_MOVE_SLOTS) {
          offered += entry
          continue
        }
        val move = PokemonMove(moveId.toShort(), def.pp.toByte())
        if (slot < 0) known += move else known[slot] = move
        learned += entry
      }
    }
    return MoveLearnOutcome(learned, offered)
  }

  /** Applies the picked moveset, keeping the pp of moves it already had. */
  fun apply(known: MutableList<PokemonMove>, chosen: List<Short>, offered: List<Short>): Boolean {
    if (chosen.size != known.size || chosen.size != chosen.distinct().size) return false
    val allowed = known.map { it.id } + offered
    if (chosen.any { it !in allowed }) return false
    val applied =
        chosen.map { id ->
          known.firstOrNull { it.id == id }
              ?: PokemonMove(id, moves.get(id.toInt())?.pp?.toByte() ?: 0)
        }
    known.clear()
    known += applied
    return true
  }
}
