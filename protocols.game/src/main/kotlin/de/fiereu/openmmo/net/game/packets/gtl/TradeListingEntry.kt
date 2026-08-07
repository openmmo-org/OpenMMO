package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S64LE

data class TradeListingEntry(
    val a: Int,
    val b: Int,
    val c: Long,
)

internal val TradeListingEntryCodec: Codec<TradeListingEntry> =
    object : PacketCodec<TradeListingEntry>() {
      override fun CodecScope<TradeListingEntry>.body(): TradeListingEntry {
        val a = field(S32LE) { it.a }
        val b = field(S32LE) { it.b }
        val c = field(S64LE) { it.c }
        return TradeListingEntry(a, b, c)
      }
    }
