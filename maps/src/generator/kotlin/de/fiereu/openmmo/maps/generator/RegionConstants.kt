package de.fiereu.openmmo.maps.generator

open class RegionConstants(
    val name: String,
    val regionId: Int,
    val defaultVisibleNpcs: Map<String, List<Int>> = emptyMap(),
    val dirMap: Map<String, String> = COMMON_DIR_MAP,
    val movementTypes: Map<String, Int> = COMMON_MOVEMENT_TYPES,
    val weatherMap: Map<String, String> = COMMON_WEATHER_MAP,
    val mapTypeMap: Map<String, String> = COMMON_MAP_TYPE_MAP,
    val encounterMap: Map<String, String> = COMMON_ENCOUNTER_MAP,
)

val REGIONS: Map<String, RegionConstants> =
    listOf(HoennConstants, KantoConstants).associateBy { it.name }
