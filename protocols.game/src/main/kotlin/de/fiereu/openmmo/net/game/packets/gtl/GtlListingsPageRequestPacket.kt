package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.padding

/** [requestId] counts up per board and comes back in [GtlListingsPagePacket]. */
data class GtlListingsPageRequestPacket(
    val requestId: Byte,
    val listKind: GtlListKind,
    val categoryId: Byte,
    val page: Short,
)

object GtlListingsPageRequestPacketCodec : PacketCodec<GtlListingsPageRequestPacket>() {
  override fun CodecScope<GtlListingsPageRequestPacket>.body(): GtlListingsPageRequestPacket {
    val requestId = field(S8) { it.requestId }
    val listKind = field(GtlListKindCodec) { it.listKind }
    val categoryId = field(S8) { it.categoryId }
    val page = field(S16LE) { it.page }
    padding(1)
    // Price filters the client leaves unset.
    padding(3, 0xFF.toByte())
    padding(1)
    return GtlListingsPageRequestPacket(requestId, listKind, categoryId, page)
  }
}
