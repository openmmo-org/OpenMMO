package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GtlSellPokemonListingPacket(
    val listingType: Byte,
    val entryKindId: Byte,
    val listingCategory: Byte,
    val itemTypeId: Short,
    val criteriaCount: Int,
    val criteriaData: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is GtlSellPokemonListingPacket &&
                listingType == other.listingType &&
                entryKindId == other.entryKindId &&
                listingCategory == other.listingCategory &&
                itemTypeId == other.itemTypeId &&
                criteriaCount == other.criteriaCount &&
                criteriaData.contentEquals(other.criteriaData)

    override fun hashCode(): Int {
        var r = listingType.toInt()
        r = r * 31 + entryKindId
        r = r * 31 + listingCategory
        r = r * 31 + itemTypeId
        r = r * 31 + criteriaCount
        r = r * 31 + criteriaData.contentHashCode()
        return r
    }
}

object GtlSellPokemonListingPacketCodec : PacketCodec<GtlSellPokemonListingPacket>() {
    override fun CodecScope<GtlSellPokemonListingPacket>.body(): GtlSellPokemonListingPacket {
        val listingType = field(S8) { it.listingType }
        val entryKindId = field(S8) { it.entryKindId }
        val listingCategory = field(S8) { it.listingCategory }
        val itemTypeId = field(S16LE) { it.itemTypeId }
        val criteriaCount = field(U8) { it.criteriaCount }
        val criteriaData = field(RemainingBytes) { it.criteriaData }
        return GtlSellPokemonListingPacket(
            listingType, entryKindId, listingCategory, itemTypeId, criteriaCount, criteriaData
        )
    }
}
