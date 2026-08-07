package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.openmmo.common.Pokemon

data class GtlPokemonListing(
    override val listingId: Long,
    override val price: Int,
    override val listedAt: Int,
    override val expiresAt: Int,
    override val quantity: Short,
    /** A row can say it carries no record. Every captured row carries one. */
    val pokemon: Pokemon?,
    /** The battle stats the board shows, in EV wire order hp, atk, def, spd, spAtk, spDef. */
    val stats: List<Short>,
) : GtlSearchListing
