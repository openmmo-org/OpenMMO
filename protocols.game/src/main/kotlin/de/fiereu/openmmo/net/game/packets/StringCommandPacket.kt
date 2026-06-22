package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class StringCommandPacket(val command: String)

object StringCommandPacketCodec : PacketCodec<StringCommandPacket>() {
  override fun CodecScope<StringCommandPacket>.body(): StringCommandPacket {
    val command = field(Utf16LeNullTerminated) { it.command }
    return StringCommandPacket(command)
  }
}
