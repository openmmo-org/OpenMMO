package de.fiereu.openmmo.maps

data class NpcDef(
    val entityIdx: Int,
    val graphicsId: Int,
    val x: Int,
    val y: Int,
    val elevation: Int,
    val movementType: Int,
    val movementRangeX: Int,
    val movementRangeY: Int,
    val trainerType: Int,
    val facing: Int,
    val script: String = "0x0",
)
