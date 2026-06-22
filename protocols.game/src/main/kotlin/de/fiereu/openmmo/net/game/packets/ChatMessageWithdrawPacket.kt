package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class ChatMessageWithdrawPacket(
    val authorId: Long,
)

object ChatMessageWithdrawPacketCodec : PacketCodec<ChatMessageWithdrawPacket>() {
  override fun CodecScope<ChatMessageWithdrawPacket>.body(): ChatMessageWithdrawPacket {
    val authorId = field(S64LE) { it.authorId }
    return ChatMessageWithdrawPacket(authorId)
  }
}
