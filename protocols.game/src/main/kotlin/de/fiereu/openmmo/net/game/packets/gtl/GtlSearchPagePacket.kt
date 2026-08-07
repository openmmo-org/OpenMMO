package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.listPrefixed

/** [requestId] is echoed from the request that asked for this page. */
data class GtlSearchPagePacket(
    val requestId: Byte,
    val listKind: GtlListKind,
    val page: Short,
    val totalMatches: Int,
    val listings: List<GtlSearchListing>,
    val quotes: List<GtlItemQuote>?,
)

object GtlSearchPagePacketCodec : PacketCodec<GtlSearchPagePacket>() {
  override fun CodecScope<GtlSearchPagePacket>.body(): GtlSearchPagePacket {
    val requestId = field(S8) { it.requestId }
    val listKind = field(GtlListKindCodec) { it.listKind }
    val page = field(S16LE) { it.page }
    val totalMatches = field(S32LE) { it.totalMatches }
    val listings = field(gtlSearchListingCodec(listKind).listPrefixed(U8)) { it.listings }
    val quotes =
        if (listKind == GtlListKind.ITEM) {
          field(GtlItemQuoteCodec.listPrefixed(U8)) { it.quotes ?: emptyList() }
        } else {
          null
        }
    return GtlSearchPagePacket(requestId, listKind, page, totalMatches, listings, quotes)
  }
}
