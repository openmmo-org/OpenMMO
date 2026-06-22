package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class GtlListingCancelPacket(
    val listingId: String,
)

object GtlListingCancelPacketCodec : PacketCodec<GtlListingCancelPacket>() {
  override fun CodecScope<GtlListingCancelPacket>.body(): GtlListingCancelPacket {
    val listingId = field(Utf16LeNullTerminated) { it.listingId }
    return GtlListingCancelPacket(listingId)
  }
}
