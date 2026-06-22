package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class PlayerStopMovingPacket

object PlayerStopMovingPacketCodec : PacketCodec<PlayerStopMovingPacket>() {
  override fun CodecScope<PlayerStopMovingPacket>.body(): PlayerStopMovingPacket {
    return PlayerStopMovingPacket()
  }
}
