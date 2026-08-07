package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S32LE

data class GtlItemQuote(
    val itemId: Short,
    val price: Int,
)

internal val GtlItemQuoteCodec: Codec<GtlItemQuote> =
    object : PacketCodec<GtlItemQuote>() {
      override fun CodecScope<GtlItemQuote>.body(): GtlItemQuote {
        val itemId = field(S16LE) { it.itemId }
        val price = field(S32LE) { it.price }
        return GtlItemQuote(itemId, price)
      }
    }
