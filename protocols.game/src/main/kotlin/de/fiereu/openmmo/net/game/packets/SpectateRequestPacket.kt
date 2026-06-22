package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class SpectateRequestPacket(
    val targetEntityId: Long,
)

object SpectateRequestPacketCodec : PacketCodec<SpectateRequestPacket>() {
  override fun CodecScope<SpectateRequestPacket>.body(): SpectateRequestPacket {
    val targetEntityId = field(S64LE) { it.targetEntityId }
    return SpectateRequestPacket(targetEntityId)
  }
}
