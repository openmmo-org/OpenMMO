package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class WorldFlagSetPacket(
    val group: Byte,
    val index: Short,
    val value: Byte,
)

object WorldFlagSetPacketCodec : PacketCodec<WorldFlagSetPacket>() {
  override fun CodecScope<WorldFlagSetPacket>.body(): WorldFlagSetPacket {
    val group = field(S8) { it.group }
    val index = field(S16LE) { it.index }
    val value = field(S8) { it.value }
    return WorldFlagSetPacket(group, index, value)
  }
}
