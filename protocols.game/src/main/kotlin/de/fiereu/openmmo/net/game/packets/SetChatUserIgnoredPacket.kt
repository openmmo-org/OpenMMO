package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SetChatUserIgnoredPacket(
    val channelType: Byte,
    val userId: Short,
    val ignored: Boolean,
)

object SetChatUserIgnoredPacketCodec : PacketCodec<SetChatUserIgnoredPacket>() {
  override fun CodecScope<SetChatUserIgnoredPacket>.body(): SetChatUserIgnoredPacket {
    val channelType = field(S8) { it.channelType }
    val userId = field(S16LE) { it.userId }
    val ignored = field(Bool) { it.ignored }
    return SetChatUserIgnoredPacket(channelType, userId, ignored)
  }
}
