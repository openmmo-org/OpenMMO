package de.fiereu.openmmo.maps.generator

data class ParsedMap(
    val regionName: String,
    val sourceName: String,
    val groupName: String,
    val mapId: String,
    val region: Int,
    val bank: Int,
    val index: Int,
    val width: Int,
    val height: Int,
    val paletteIdx1: Int,
    val paletteIdx2: Int,
    val musicId: Int,
    val mapsecId: Int,
    val borderTiles: List<Int>,
    val blockData: String,
    val lighting: String,
    val weather: String,
    val mapType: String,
    val encounterType: String,
    val connections: List<ParsedConnection>,
    val warps: List<ParsedWarp>,
    val visibleNpcs: List<ParsedNpc>,
    val bgEvents: List<ParsedBgEvent>,
)

data class ParsedConnection(
    val direction: String,
    val offset: Int,
    val targetBank: Int,
    val targetMap: Int,
)

data class ParsedWarp(
    val x: Int,
    val y: Int,
    val elevation: Int,
    val targetRegion: Int,
    val targetBank: Int,
    val targetMap: Int,
    val targetX: Int,
    val targetY: Int,
    val targetElevation: Int,
)

data class ParsedNpc(
    val entityIdx: Int,
    val graphicsId: Int,
    val x: Int,
    val y: Int,
    val elevation: Int,
    val movementType: String,
    val movementRangeX: Int,
    val movementRangeY: Int,
    val trainerType: Int,
    val facing: String,
    val script: String,
)

data class ParsedBgEvent(
    val x: Int,
    val y: Int,
    val facingDir: String,
    val script: String,
)
