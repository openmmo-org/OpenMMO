package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.listPrefixed

/** [requestId] counts up per board and comes back in [GtlSearchPagePacket]. */
data class GtlSearchPageRequestPacket(
    val requestId: Byte,
    val listKind: GtlListKind,
    val categoryId: Byte,
    val page: Short,
    val filters: List<GtlSearchFilter> = emptyList(),
)

object GtlSearchPageRequestPacketCodec : PacketCodec<GtlSearchPageRequestPacket>() {
  override fun CodecScope<GtlSearchPageRequestPacket>.body(): GtlSearchPageRequestPacket {
    val requestId = field(S8) { it.requestId }
    val listKind = field(GtlListKindCodec) { it.listKind }
    val categoryId = field(S8) { it.categoryId }
    val page = field(S16LE) { it.page }
    val filters = field(GtlSearchFilterCodec.listPrefixed(U8)) { it.filters }
    return GtlSearchPageRequestPacket(requestId, listKind, categoryId, page, filters)
  }
}
