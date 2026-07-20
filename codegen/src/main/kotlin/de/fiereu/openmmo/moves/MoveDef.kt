package de.fiereu.openmmo.moves

import de.fiereu.openmmo.common.enums.MoveEffect
import de.fiereu.openmmo.common.enums.MoveFlag
import de.fiereu.openmmo.common.enums.MoveTarget
import de.fiereu.openmmo.common.enums.PokemonType

data class MoveDef(
    val id: Int,
    val name: String,
    val effect: MoveEffect,
    val power: Int,
    val type: PokemonType,
    val accuracy: Int,
    val pp: Int,
    val secondaryEffectChance: Int,
    val target: MoveTarget,
    val priority: Int,
    val flags: Set<MoveFlag> = emptySet(),
) {
  fun hasFlag(flag: MoveFlag): Boolean = flag in flags
}
