package de.fiereu.openmmo.maps

import de.fiereu.openmmo.common.enums.EncounterMethod

/** One slot in a wild encounter table: a species, its level range, and its pick weight. */
data class WildEncounterSlot(
    val speciesId: Int,
    val minLevel: Int,
    val maxLevel: Int,
    val weight: Int,
)

/**
 * The wild monsters met by one [method] on a map. [encounterRate] drives how often a step meets
 * anything, the per-slot weights decide which one.
 */
data class WildEncounterTable(
    val method: EncounterMethod,
    val encounterRate: Int,
    val slots: List<WildEncounterSlot>,
)
