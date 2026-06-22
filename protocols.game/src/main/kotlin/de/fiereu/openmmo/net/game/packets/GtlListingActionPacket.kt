package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class GtlListingActionPacket(
    val entryKindId: Byte,
    val itemId: Short,
)

object GtlListingActionPacketCodec : PacketCodec<GtlListingActionPacket>() {
  override fun CodecScope<GtlListingActionPacket>.body(): GtlListingActionPacket {
    val entryKindId = field(S8) { it.entryKindId }
    val itemId = field(S16LE) { it.itemId }
    return GtlListingActionPacket(entryKindId, itemId)
  }
}
