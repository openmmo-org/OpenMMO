package de.fiereu.openmmo.maps

import de.fiereu.openmmo.common.enums.Direction

data class WarpTile(
    val x: Int,
    val y: Int,
    val elevation: Int = 0,
    val targetRegionId: Byte,
    val targetBankId: Byte,
    val targetMapId: Byte,
    val targetX: Int,
    val targetY: Int,
    val targetElevation: Int = 0,
    val facingDirection: Direction? = null,
)
