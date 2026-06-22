package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class CameraFollowEntityPacket(
    val entityId: Long,
)

object CameraFollowEntityPacketCodec : PacketCodec<CameraFollowEntityPacket>() {
  override fun CodecScope<CameraFollowEntityPacket>.body(): CameraFollowEntityPacket {
    val entityId = field(S64LE) { it.entityId }
    return CameraFollowEntityPacket(entityId)
  }
}
