package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class CancelEntityMessagePacket(
    val entityId: Long,
)

object CancelEntityMessagePacketCodec : PacketCodec<CancelEntityMessagePacket>() {
  override fun CodecScope<CancelEntityMessagePacket>.body(): CancelEntityMessagePacket {
    val entityId = field(S64LE) { it.entityId }
    return CancelEntityMessagePacket(entityId)
  }
}
