package de.fiereu.openmmo.codegen.maps

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
    val behaviorData: String,
    val encounters: List<ParsedEncounterTable>,
    val lighting: String,
    val weather: String,
    val mapType: String,
    val encounterType: String,
    val connections: List<ParsedConnection>,
    val warps: List<ParsedWarp>,
    val visibleNpcs: List<ParsedNpc>,
    val bgEvents: List<ParsedBgEvent>,
    // Decomp label of the map's ON_TRANSITION script, or "" when the map has none.
    val onTransitionScript: String,
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

data class ParsedEncounterTable(
    val method: String,
    val encounterRate: Int,
    val slots: List<ParsedEncounterSlot>,
)

data class ParsedEncounterSlot(
    val speciesId: Int,
    val minLevel: Int,
    val maxLevel: Int,
    val weight: Int,
)
