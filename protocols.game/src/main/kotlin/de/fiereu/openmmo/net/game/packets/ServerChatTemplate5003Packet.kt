package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class ServerChatTemplate5003Packet(
    val argument: String,
)

object ServerChatTemplate5003PacketCodec : PacketCodec<ServerChatTemplate5003Packet>() {
  override fun CodecScope<ServerChatTemplate5003Packet>.body(): ServerChatTemplate5003Packet {
    field(S64LE) { 0L }
    val argument = field(Utf16LeNullTerminated) { it.argument }
    return ServerChatTemplate5003Packet(argument)
  }
}
