package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ChatChannelUser(
    val channelId: Short,
    val rank: Byte,
)

data class ChatChannelUserListPacket(
    val channelType: Byte,
    val clear: Boolean,
    val refresh: Boolean,
    val unreadCount: Int,
    val unreadMentionCount: Int,
    val users: List<ChatChannelUser>,
)

private val ChatChannelUserCodec: Codec<ChatChannelUser> =
    object : PacketCodec<ChatChannelUser>() {
      override fun CodecScope<ChatChannelUser>.body(): ChatChannelUser {
        val channelId = field(S16LE) { it.channelId }
        val rank = field(S8) { it.rank }
        return ChatChannelUser(channelId, rank)
      }
    }

object ChatChannelUserListPacketCodec : PacketCodec<ChatChannelUserListPacket>() {
  override fun CodecScope<ChatChannelUserListPacket>.body(): ChatChannelUserListPacket {
    val channelType = field(S8) { it.channelType }
    val clear = field(Bool) { it.clear }
    val refresh = field(Bool) { it.refresh }
    val unreadCount = if (clear) field(S32LE) { it.unreadCount } else 0
    val unreadMentionCount = if (clear) field(S32LE) { it.unreadMentionCount } else 0
    val count = field(S16LE) { it.users.size.toShort() }.toInt() and 0xFFFF
    val users = (0 until count).map { i -> field(ChatChannelUserCodec) { it.users[i] } }
    return ChatChannelUserListPacket(
        channelType = channelType,
        clear = clear,
        refresh = refresh,
        unreadCount = unreadCount,
        unreadMentionCount = unreadMentionCount,
        users = users,
    )
  }
}
