package de.fiereu.openmmo.common

import de.fiereu.openmmo.common.enums.Direction

/**
 * A destination set at runtime by a script (the decomp setdynamicwarp) that a MAP_DYNAMIC warp tile
 * resolves to when the player steps on it. Used for the intro truck exit.
 */
data class DynamicWarp(
    val regionId: Byte,
    val bankId: Byte,
    val mapId: Byte,
    val x: Short,
    val y: Short,
    val facing: Direction,
)
