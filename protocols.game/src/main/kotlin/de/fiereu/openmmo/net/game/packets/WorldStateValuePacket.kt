package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE

data class WorldStateValuePacket(
    val value: Short,
)

object WorldStateValuePacketCodec : PacketCodec<WorldStateValuePacket>() {
  override fun CodecScope<WorldStateValuePacket>.body(): WorldStateValuePacket {
    val value = field(S16LE, WorldStateValuePacket::value)
    return WorldStateValuePacket(value)
  }
}
