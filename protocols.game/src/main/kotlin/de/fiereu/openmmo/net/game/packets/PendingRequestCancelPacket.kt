package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class PendingRequestCancelPacket(
    val requestEntityId: Long,
)

object PendingRequestCancelPacketCodec : PacketCodec<PendingRequestCancelPacket>() {
  override fun CodecScope<PendingRequestCancelPacket>.body(): PendingRequestCancelPacket {
    val requestEntityId = field(S64LE) { it.requestEntityId }
    return PendingRequestCancelPacket(requestEntityId)
  }
}
