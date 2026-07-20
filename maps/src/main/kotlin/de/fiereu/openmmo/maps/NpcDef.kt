package de.fiereu.openmmo.maps

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.MovementType

data class NpcDef(
    val entityIdx: Int,
    val graphicsId: Int,
    val x: Int,
    val y: Int,
    val elevation: Int,
    val movementType: MovementType,
    val movementRangeX: Int,
    val movementRangeY: Int,
    val trainerType: Int,
    val facing: Direction,
    val script: String = "0x0",
)
