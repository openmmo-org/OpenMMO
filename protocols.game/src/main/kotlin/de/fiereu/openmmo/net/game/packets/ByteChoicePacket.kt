package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class ByteChoicePacket(val value: Byte)

object ByteChoicePacketCodec : PacketCodec<ByteChoicePacket>() {
  override fun CodecScope<ByteChoicePacket>.body(): ByteChoicePacket {
    val value = field(S8) { it.value }
    return ByteChoicePacket(value)
  }
}
