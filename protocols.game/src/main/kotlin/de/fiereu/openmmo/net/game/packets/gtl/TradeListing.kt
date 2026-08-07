package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.listPrefixed

data class TradeListing(
    val typeId: Short,
    val price: Int,
    val fieldB: Int,
    val fieldC: Int,
    val fieldD: Int,
    val entries: List<TradeListingEntry>,
)

internal val TradeListingCodec: Codec<TradeListing> =
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
