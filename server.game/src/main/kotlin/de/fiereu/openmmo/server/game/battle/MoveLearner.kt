package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.MAX_MOVE_SLOTS
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.pokemon.LearnsetRegistry
import javax.inject.Inject
import javax.inject.Singleton

/** A move learned on level up, ready to be announced to the player. */
data class LearnedMove(val level: Int, val moveId: Int, val name: String)

/** Teaches the level up moves a monster gained from a level range. */
@Singleton
class MoveLearner
@Inject
constructor(
    private val learnsets: LearnsetRegistry,
    private val moves: MoveRegistry,
) {

  /**
   * Adds every move learned above [fromLevel] up to [toLevel] that fits a free slot and returns
   * them. A monster that already knows four moves learns nothing.
   */
  // TODO Let the player replace a move when the moveset is full
  //  A monster with four moves silently skips every move it would learn on level up. The client's
  //  move learn prompt packets are unknown, ByteDex holds no capture of them. Capture the prompt
  //  and the answer, then apply the chosen slot here.
  fun learn(known: MutableList<PokemonMove>, dexId: Int, fromLevel: Int, toLevel: Int) = buildList {
    for (level in fromLevel + 1..toLevel) {
      for (moveId in learnsets.movesAt(dexId, level)) {
        if (known.any { it.id.toInt() == moveId }) continue
        val def = moves.get(moveId) ?: continue
        val slot = known.indexOfFirst { it.id.toInt() == 0 }
        if (slot < 0 && known.size >= MAX_MOVE_SLOTS) continue
        val learned = PokemonMove(moveId.toShort(), def.pp.toByte())
        if (slot < 0) known += learned else known[slot] = learned
        add(LearnedMove(level, moveId, def.name))
      }
    }
  }
}
