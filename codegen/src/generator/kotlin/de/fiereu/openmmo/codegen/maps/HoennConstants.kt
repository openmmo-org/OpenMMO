package de.fiereu.openmmo.codegen.maps

object HoennConstants :
    RegionConstants(
        name = "hoenn",
        regionId = 1,
        // Emerald uses offset map and tileset namespaces.
        gbaBankOffset = 50,
        gbaPaletteOffset = 100,
        defaultVisibleNpcs =
            mapOf(
                "LittlerootTown_MaysHouse_1F" to listOf(0),
                "LittlerootTown_BrendansHouse_1F" to listOf(3, 5),
                "LittlerootTown_ProfessorBirchsLab" to listOf(0),
            ),
    )
