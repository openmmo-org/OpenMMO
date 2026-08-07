package de.fiereu.openmmo.net.game.packets.gtl

data class GtlItemListing(
    override val listingId: Long,
    override val price: Int,
    override val listedAt: Int,
    override val expiresAt: Int,
    override val quantity: Short,
    val itemId: Short,
    /** Zero in every captured row. */
    val itemState: Byte,
) : GtlSearchListing
