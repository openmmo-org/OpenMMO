package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.listPrefixed

data class GtlListingsPagePacket(
    val requestId: Byte,
    val listKind: GtlListKind,
    val page: Short,
    val totalCount: Int,
    val listings: List<TradeListing>,
)

object GtlListingsPagePacketCodec : PacketCodec<GtlListingsPagePacket>() {
  override fun CodecScope<GtlListingsPagePacket>.body(): GtlListingsPagePacket {
    val requestId = field(S8) { it.requestId }
    val listKind = field(GtlListKindCodec) { it.listKind }
    val page = field(S16LE) { it.page }
    val totalCount = field(S32LE) { it.totalCount }
    val listings = field(TradeListingCodec.listPrefixed(U8)) { it.listings }
    return GtlListingsPagePacket(requestId, listKind, page, totalCount, listings)
  }
}
