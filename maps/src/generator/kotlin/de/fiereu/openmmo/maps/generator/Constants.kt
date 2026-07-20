package de.fiereu.openmmo.maps.generator

internal const val BANK_GROUP_OFFSET = 50

internal val COMMON_DIR_MAP =
    mapOf(
        "down" to "Direction.DOWN",
        "up" to "Direction.UP",
        "left" to "Direction.LEFT",
        "right" to "Direction.RIGHT",
        "dive" to "Direction.DIVE",
        "emerge" to "Direction.EMERGE",
    )

internal val COMMON_WEATHER_MAP =
    mapOf(
        "WEATHER_NONE" to "Weather.IN_HOUSE_WEATHER",
        "WEATHER_SUNNY" to "Weather.REGULAR_WEATHER",
        "WEATHER_RAIN" to "Weather.RAINY_WEATHER",
        "WEATHER_SNOW" to "Weather.THREE_SNOW_FLAKES",
        "WEATHER_FOG_HORIZONTAL" to "Weather.STEADY_MIST",
        "WEATHER_FOG_DIAGONAL" to "Weather.STEADY_MIST",
        "WEATHER_SHADE" to "Weather.CLOUDY",
        "WEATHER_UNDERWATER_BUBBLES" to "Weather.UNDERWATER_MIST",
        "WEATHER_VOLCANIC_ASH" to "Weather.DENSE_BRIGHT_MIST",
    )

internal val COMMON_MAP_TYPE_MAP =
    mapOf(
        "MAP_TYPE_INDOOR" to "MapType.INSIDE",
        "MAP_TYPE_TOWN" to "MapType.UNKNOWN_0x01",
        "MAP_TYPE_CITY" to "MapType.CITY",
        "MAP_TYPE_ROUTE" to "MapType.ROUTE",
        "MAP_TYPE_UNDERGROUND" to "MapType.UNDERGROUND",
        "MAP_TYPE_UNDERWATER" to "MapType.UNDERWATER",
        "MAP_TYPE_OCEAN_ROUTE" to "MapType.ROUTE",
        "MAP_TYPE_SECRET_BASE" to "MapType.SECRET_BASE",
    )

private const val ENCOUNTER_RANDOM = "EncounterType.RANDOM"
private const val ENCOUNTER_UNKNOWN_0X03 = "EncounterType.UNKNOWN_0x03"

internal val COMMON_ENCOUNTER_MAP =
    mapOf(
        "MAP_TYPE_INDOOR" to ENCOUNTER_RANDOM,
        "MAP_TYPE_TOWN" to ENCOUNTER_RANDOM,
        "MAP_TYPE_CITY" to ENCOUNTER_RANDOM,
        "MAP_TYPE_ROUTE" to ENCOUNTER_RANDOM,
        "MAP_TYPE_UNDERGROUND" to ENCOUNTER_RANDOM,
        "MAP_TYPE_UNDERWATER" to ENCOUNTER_UNKNOWN_0X03,
        "MAP_TYPE_OCEAN_ROUTE" to ENCOUNTER_RANDOM,
        "MAP_TYPE_SECRET_BASE" to ENCOUNTER_UNKNOWN_0X03,
    )

private val FACING_REFS =
    listOf("Direction.DOWN", "Direction.UP", "Direction.LEFT", "Direction.RIGHT")

internal fun movementToFacingRef(movementType: Int): String =
    FACING_REFS[
        when (movementType) {
          7 -> 1
          8 -> 0
          9 -> 2
          10 -> 3
          in 64..67 -> movementType - 64
          in 68..71 -> movementType - 68
          in 72..75 -> movementType - 72
          else -> 0
        }]
