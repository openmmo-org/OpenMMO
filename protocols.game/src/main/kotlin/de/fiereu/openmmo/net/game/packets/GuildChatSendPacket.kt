package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class GuildChatSendPacket(
    val message: String,
)

object GuildChatSendPacketCodec : PacketCodec<GuildChatSendPacket>() {
  override fun CodecScope<GuildChatSendPacket>.body(): GuildChatSendPacket {
    val message = field(Utf16LeNullTerminated) { it.message }
    return GuildChatSendPacket(message)
  }
}
