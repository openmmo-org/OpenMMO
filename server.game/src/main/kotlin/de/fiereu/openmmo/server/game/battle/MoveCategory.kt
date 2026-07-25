package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.enums.PokemonType

/** Gen 3 splits damage into physical and special by move type, not per move. */
object MoveCategory {

  private val PHYSICAL =
      setOf(
          PokemonType.NORMAL,
          PokemonType.FIGHTING,
          PokemonType.FLYING,
          PokemonType.GROUND,
          PokemonType.ROCK,
          PokemonType.BUG,
          PokemonType.GHOST,
          PokemonType.POISON,
          PokemonType.STEEL,
      )

  fun isPhysical(type: PokemonType): Boolean = type in PHYSICAL
}
