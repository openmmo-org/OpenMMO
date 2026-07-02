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

internal val COMMON_MOVEMENT_TYPES =
    mapOf(
        "MOVEMENT_TYPE_NONE" to 0,
        "MOVEMENT_TYPE_LOOK_AROUND" to 1,
        "MOVEMENT_TYPE_WANDER_AROUND" to 2,
        "MOVEMENT_TYPE_WANDER_UP_AND_DOWN" to 3,
        "MOVEMENT_TYPE_WANDER_DOWN_AND_UP" to 4,
        "MOVEMENT_TYPE_WANDER_LEFT_AND_RIGHT" to 5,
        "MOVEMENT_TYPE_WANDER_RIGHT_AND_LEFT" to 6,
        "MOVEMENT_TYPE_FACE_UP" to 7,
        "MOVEMENT_TYPE_FACE_DOWN" to 8,
        "MOVEMENT_TYPE_FACE_LEFT" to 9,
        "MOVEMENT_TYPE_FACE_RIGHT" to 10,
        "MOVEMENT_TYPE_PLAYER" to 11,
        "MOVEMENT_TYPE_BERRY_TREE_GROWTH" to 12,
        "MOVEMENT_TYPE_FACE_DOWN_AND_UP" to 13,
        "MOVEMENT_TYPE_FACE_LEFT_AND_RIGHT" to 14,
        "MOVEMENT_TYPE_FACE_UP_AND_LEFT" to 15,
        "MOVEMENT_TYPE_FACE_UP_AND_RIGHT" to 16,
        "MOVEMENT_TYPE_FACE_DOWN_AND_LEFT" to 17,
        "MOVEMENT_TYPE_FACE_DOWN_AND_RIGHT" to 18,
        "MOVEMENT_TYPE_FACE_DOWN_UP_AND_LEFT" to 19,
        "MOVEMENT_TYPE_FACE_DOWN_UP_AND_RIGHT" to 20,
        "MOVEMENT_TYPE_FACE_UP_LEFT_AND_RIGHT" to 21,
        "MOVEMENT_TYPE_FACE_DOWN_LEFT_AND_RIGHT" to 22,
        "MOVEMENT_TYPE_ROTATE_COUNTERCLOCKWISE" to 23,
        "MOVEMENT_TYPE_ROTATE_CLOCKWISE" to 24,
        "MOVEMENT_TYPE_WALK_UP_AND_DOWN" to 25,
        "MOVEMENT_TYPE_WALK_DOWN_AND_UP" to 26,
        "MOVEMENT_TYPE_WALK_LEFT_AND_RIGHT" to 27,
        "MOVEMENT_TYPE_WALK_RIGHT_AND_LEFT" to 28,
        "MOVEMENT_TYPE_WALK_IN_PLACE_DOWN" to 64,
        "MOVEMENT_TYPE_WALK_IN_PLACE_UP" to 65,
        "MOVEMENT_TYPE_WALK_IN_PLACE_LEFT" to 66,
        "MOVEMENT_TYPE_WALK_IN_PLACE_RIGHT" to 67,
        "MOVEMENT_TYPE_JOG_IN_PLACE_DOWN" to 68,
        "MOVEMENT_TYPE_JOG_IN_PLACE_UP" to 69,
        "MOVEMENT_TYPE_JOG_IN_PLACE_LEFT" to 70,
        "MOVEMENT_TYPE_JOG_IN_PLACE_RIGHT" to 71,
        "MOVEMENT_TYPE_RUN_IN_PLACE_DOWN" to 72,
        "MOVEMENT_TYPE_RUN_IN_PLACE_UP" to 73,
        "MOVEMENT_TYPE_RUN_IN_PLACE_LEFT" to 74,
        "MOVEMENT_TYPE_RUN_IN_PLACE_RIGHT" to 75,
        "MOVEMENT_TYPE_INVISIBLE" to 76,
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

internal fun movementTypeRef(name: String, known: Map<String, Int>): String =
    if (known.containsKey(name)) "MovementType." + name.removePrefix("MOVEMENT_TYPE_")
    else "MovementType.NONE"
