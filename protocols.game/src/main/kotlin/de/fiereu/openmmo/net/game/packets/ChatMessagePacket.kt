package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.enums.ChatType
import de.fiereu.openmmo.common.enums.Language

data class ChatMessagePacket(
    val type: ChatType,
    val language: Language?,
    val message: String,
    val sender: String?,
)

object ChatMessagePacketCodec : PacketCodec<ChatMessagePacket>() {
  override fun CodecScope<ChatMessagePacket>.body(): ChatMessagePacket {
    val type = ChatType.entries[field(U8) { it.type.ordinal }]
    return ChatMessagePacket(
        type = type,
        language = null,
        message = field(Utf16LeNullTerminated, ChatMessagePacket::message),
        sender = null,
    )
  }
}
