package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class TradeListingEntry(
    val a: Int,
    val b: Int,
    val c: Long,
)

data class TradeListing(
    val typeId: Short,
    val price: Int,
    val fieldB: Int,
    val fieldC: Int,
    val fieldD: Int,
    val entries: List<TradeListingEntry>,
)

data class GtlListingsPagePacket(
    val page: Byte,
    val entryKind: Int,
    val totalCount: Int,
    val listings: List<TradeListing>,
)

private val TradeListingEntryCodec: Codec<TradeListingEntry> =
    object : PacketCodec<TradeListingEntry>() {
        override fun CodecScope<TradeListingEntry>.body(): TradeListingEntry {
            val a = field(S32LE) { it.a }
            val b = field(S32LE) { it.b }
            val c = field(S64LE) { it.c }
            return TradeListingEntry(a, b, c)
        }
    }

private val TradeListingCodec: Codec<TradeListing> =
    object : PacketCodec<TradeListing>() {
        override fun CodecScope<TradeListing>.body(): TradeListing {
            val typeId = field(S16LE) { it.typeId }
            val price = field(S32LE) { it.price }
            val fieldB = field(S32LE) { it.fieldB }
            val fieldC = field(S32LE) { it.fieldC }
            val fieldD = field(S32LE) { it.fieldD }
            val entries = field(TradeListingEntryCodec.listPrefixed(U8)) { it.entries }
            return TradeListing(typeId, price, fieldB, fieldC, fieldD, entries)
        }
    }

object GtlListingsPagePacketCodec : PacketCodec<GtlListingsPagePacket>() {
    override fun CodecScope<GtlListingsPagePacket>.body(): GtlListingsPagePacket {
        val page = field(S8) { it.page }
        val entryKind = field(U8) { it.entryKind }
        field(S16LE) { 0 }
        val totalCount = field(S32LE) { it.totalCount }
        val listings = field(TradeListingCodec.listPrefixed(U8)) { it.listings }
        return GtlListingsPagePacket(page, entryKind, totalCount, listings)
    }
}
