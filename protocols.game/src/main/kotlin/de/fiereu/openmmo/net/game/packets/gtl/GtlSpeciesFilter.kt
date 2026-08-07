package de.fiereu.openmmo.net.game.packets.gtl

/** Limits a monster search to these dex ids. */
data class GtlSpeciesFilter(val speciesIds: List<Short>) : GtlSearchFilter {
  override val kind = GtlFilterKind.SPECIES
}
