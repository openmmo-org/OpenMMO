package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class TextArgumentPacket(val text: String)

object TextArgumentPacketCodec : PacketCodec<TextArgumentPacket>() {
  override fun CodecScope<TextArgumentPacket>.body(): TextArgumentPacket {
    val text = field(Utf16LeNullTerminated) { it.text }
    return TextArgumentPacket(text)
  }
}
