package de.fiereu.openmmo.maps.generator

internal const val BANK_GROUP_OFFSET = 50

internal val dirMap =
    mapOf("down" to 1, "up" to 2, "left" to 3, "right" to 4, "dive" to 5, "emerge" to 6)

internal val movementTypes =
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

internal val gfxIds =
    mapOf(
        "OBJ_EVENT_GFX_BRENDAN_NORMAL" to 0,
        "OBJ_EVENT_GFX_BRENDAN_MACH_BIKE" to 1,
        "OBJ_EVENT_GFX_BRENDAN_SURFING" to 2,
        "OBJ_EVENT_GFX_BRENDAN_FIELD_MOVE" to 3,
        "OBJ_EVENT_GFX_QUINTY_PLUMP" to 4,
        "OBJ_EVENT_GFX_NINJA_BOY" to 5,
        "OBJ_EVENT_GFX_TWIN" to 6,
        "OBJ_EVENT_GFX_BOY_1" to 7,
        "OBJ_EVENT_GFX_GIRL_1" to 8,
        "OBJ_EVENT_GFX_BOY_2" to 9,
        "OBJ_EVENT_GFX_GIRL_2" to 10,
        "OBJ_EVENT_GFX_LITTLE_BOY" to 11,
        "OBJ_EVENT_GFX_LITTLE_GIRL" to 12,
        "OBJ_EVENT_GFX_BOY_3" to 13,
        "OBJ_EVENT_GFX_GIRL_3" to 14,
        "OBJ_EVENT_GFX_RICH_BOY" to 15,
        "OBJ_EVENT_GFX_WOMAN_1" to 16,
        "OBJ_EVENT_GFX_FAT_MAN" to 17,
        "OBJ_EVENT_GFX_POKEFAN_F" to 18,
        "OBJ_EVENT_GFX_MAN_1" to 19,
        "OBJ_EVENT_GFX_WOMAN_2" to 20,
        "OBJ_EVENT_GFX_EXPERT_M" to 21,
        "OBJ_EVENT_GFX_EXPERT_F" to 22,
        "OBJ_EVENT_GFX_MAN_2" to 23,
        "OBJ_EVENT_GFX_WOMAN_3" to 24,
        "OBJ_EVENT_GFX_POKEFAN_M" to 25,
        "OBJ_EVENT_GFX_WOMAN_4" to 26,
        "OBJ_EVENT_GFX_COOK" to 27,
        "OBJ_EVENT_GFX_LINK_RECEPTIONIST" to 28,
        "OBJ_EVENT_GFX_OLD_MAN" to 29,
        "OBJ_EVENT_GFX_OLD_WOMAN" to 30,
        "OBJ_EVENT_GFX_CAMPER" to 31,
        "OBJ_EVENT_GFX_PICNICKER" to 32,
        "OBJ_EVENT_GFX_MAN_3" to 33,
        "OBJ_EVENT_GFX_WOMAN_5" to 34,
        "OBJ_EVENT_GFX_YOUNGSTER" to 35,
        "OBJ_EVENT_GFX_BUG_CATCHER" to 36,
        "OBJ_EVENT_GFX_PSYCHIC_M" to 37,
        "OBJ_EVENT_GFX_SCHOOL_KID_M" to 38,
        "OBJ_EVENT_GFX_MANIAC" to 39,
        "OBJ_EVENT_GFX_HEX_MANIAC" to 40,
        "OBJ_EVENT_GFX_RAYQUAZA_STILL" to 41,
        "OBJ_EVENT_GFX_SWIMMER_M" to 42,
        "OBJ_EVENT_GFX_SWIMMER_F" to 43,
        "OBJ_EVENT_GFX_BLACK_BELT" to 44,
        "OBJ_EVENT_GFX_BEAUTY" to 45,
        "OBJ_EVENT_GFX_SCIENTIST_1" to 46,
        "OBJ_EVENT_GFX_LASS" to 47,
        "OBJ_EVENT_GFX_GENTLEMAN" to 48,
        "OBJ_EVENT_GFX_SAILOR" to 49,
        "OBJ_EVENT_GFX_FISHERMAN" to 50,
        "OBJ_EVENT_GFX_RUNNING_TRIATHLETE_M" to 51,
        "OBJ_EVENT_GFX_RUNNING_TRIATHLETE_F" to 52,
        "OBJ_EVENT_GFX_TUBER_F" to 53,
        "OBJ_EVENT_GFX_TUBER_M" to 54,
        "OBJ_EVENT_GFX_HIKER" to 55,
        "OBJ_EVENT_GFX_CYCLING_TRIATHLETE_M" to 56,
        "OBJ_EVENT_GFX_CYCLING_TRIATHLETE_F" to 57,
        "OBJ_EVENT_GFX_NURSE" to 58,
        "OBJ_EVENT_GFX_ITEM_BALL" to 59,
        "OBJ_EVENT_GFX_BERRY_TREE" to 60,
        "OBJ_EVENT_GFX_BERRY_TREE_EARLY_STAGES" to 61,
        "OBJ_EVENT_GFX_BERRY_TREE_LATE_STAGES" to 62,
        "OBJ_EVENT_GFX_BRENDAN_ACRO_BIKE" to 63,
        "OBJ_EVENT_GFX_PROF_BIRCH" to 64,
        "OBJ_EVENT_GFX_MAN_4" to 65,
        "OBJ_EVENT_GFX_MAN_5" to 66,
        "OBJ_EVENT_GFX_REPORTER_M" to 67,
        "OBJ_EVENT_GFX_REPORTER_F" to 68,
        "OBJ_EVENT_GFX_BARD" to 69,
        "OBJ_EVENT_GFX_ANABEL" to 70,
        "OBJ_EVENT_GFX_TUCKER" to 71,
        "OBJ_EVENT_GFX_GRETA" to 72,
        "OBJ_EVENT_GFX_SPENSER" to 73,
        "OBJ_EVENT_GFX_NOLAND" to 74,
        "OBJ_EVENT_GFX_LUCY" to 75,
        "OBJ_EVENT_GFX_MART_EMPLOYEE" to 83,
        "OBJ_EVENT_GFX_TRUCK" to 94,
        "OBJ_EVENT_GFX_VIGOROTH_CARRYING_BOX" to 95,
        "OBJ_EVENT_GFX_VIGOROTH_FACING_AWAY" to 96,
        "OBJ_EVENT_GFX_RIVAL_BRENDAN_NORMAL" to 100,
        "OBJ_EVENT_GFX_RIVAL_MAY_NORMAL" to 105,
        "OBJ_EVENT_GFX_SCIENTIST_2" to 115,
        "OBJ_EVENT_GFX_NORMAN" to 129,
        "OBJ_EVENT_GFX_MOM" to 215,
        "OBJ_EVENT_GFX_VAR_0" to 240,
    )

internal val weatherMap =
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

internal val mapTypeMap =
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

internal val encounterMap =
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

internal val defaultVisibleNpcs =
    mapOf(
        "LittlerootTown_MaysHouse_1F" to listOf(0),
        "LittlerootTown_BrendansHouse_1F" to listOf(3, 5),
        "LittlerootTown_ProfessorBirchsLab" to listOf(0),
    )

internal fun movementToFacingDir(movementType: Int): Int =
    when (movementType) {
      7 -> 1
      8 -> 0
      9 -> 2
      10 -> 3
      in 64..67 -> movementType - 64
      in 68..71 -> movementType - 68
      in 72..75 -> movementType - 72
      else -> 0
    }
