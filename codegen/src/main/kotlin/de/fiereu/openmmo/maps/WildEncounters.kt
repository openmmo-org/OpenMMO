package de.fiereu.openmmo.maps

data class WildEncounterTable(val encounterRate: Int, val pokemon: List<WildPokemon>)

data class WildPokemon(val dexId: Int, val minLevel: Int, val maxLevel: Int, val weight: Int)
