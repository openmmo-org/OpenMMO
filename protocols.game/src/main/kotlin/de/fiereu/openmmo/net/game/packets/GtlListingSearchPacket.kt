package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE

data class GtlListingSearchPacket(
    val searchId: Int,
)

object GtlListingSearchPacketCodec : PacketCodec<GtlListingSearchPacket>() {
  override fun CodecScope<GtlListingSearchPacket>.body(): GtlListingSearchPacket {
    val searchId = field(S32LE) { it.searchId }
    return GtlListingSearchPacket(searchId)
  }
}
