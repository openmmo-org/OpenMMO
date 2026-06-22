package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class RequestSocialProfilePacket(
    val targetId: Long,
)

object RequestSocialProfilePacketCodec : PacketCodec<RequestSocialProfilePacket>() {
  override fun CodecScope<RequestSocialProfilePacket>.body(): RequestSocialProfilePacket {
    val targetId = field(S64LE) { it.targetId }
    return RequestSocialProfilePacket(targetId)
  }
}
