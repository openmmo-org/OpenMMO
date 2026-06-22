package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ChatChannelMessagesPacket(
    val channelType: Byte,
    val clear: Boolean,
    val refresh: Boolean,
    val messageCount: Int,
)

object ChatChannelMessagesPacketCodec : PacketCodec<ChatChannelMessagesPacket>() {
  override fun CodecScope<ChatChannelMessagesPacket>.body(): ChatChannelMessagesPacket {
    val channelType = field(S8, ChatChannelMessagesPacket::channelType)
    val clear = field(Bool, ChatChannelMessagesPacket::clear)
    val refresh = field(Bool, ChatChannelMessagesPacket::refresh)
    val messageCount = field(U16LE, ChatChannelMessagesPacket::messageCount)
    return ChatChannelMessagesPacket(channelType, clear, refresh, messageCount)
  }
}
