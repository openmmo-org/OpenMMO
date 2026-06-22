package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class PlayerFacingChangePacket(val facingDirection: Byte)

object PlayerFacingChangePacketCodec : PacketCodec<PlayerFacingChangePacket>() {
  override fun CodecScope<PlayerFacingChangePacket>.body(): PlayerFacingChangePacket {
    val facingDirection = field(S8) { it.facingDirection }
    return PlayerFacingChangePacket(facingDirection)
  }
}
