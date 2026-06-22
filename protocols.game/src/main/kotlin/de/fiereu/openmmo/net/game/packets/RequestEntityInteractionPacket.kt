package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class RequestEntityInteractionPacket(
    val entityId: Long,
    val checksum: Long,
)

object RequestEntityInteractionPacketCodec : PacketCodec<RequestEntityInteractionPacket>() {
  override fun CodecScope<RequestEntityInteractionPacket>.body(): RequestEntityInteractionPacket {
    val entityId = field(S64LE) { it.entityId }
    val checksum = field(S64LE) { it.checksum }
    return RequestEntityInteractionPacket(entityId, checksum)
  }
}
