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
    return if (type == ChatType.TEAM) {
      ChatMessagePacket(
          type = type,
          language = null,
          message = field(Utf16LeNullTerminated, ChatMessagePacket::message),
          sender = null,
      )
    } else {
      field(S64LE) { 0L }
      val sender =
          field(Utf16LeNullTerminated) {
            it.sender ?: throw MalformedPacketException("sender must not be null")
          }
      val language =
          Language.entries[
                  field(U8) {
                    (it.language ?: throw MalformedPacketException("language must not be null"))
                        .ordinal
                  }]
      field(S8) { -1 }
      val message = field(Utf16LeNullTerminated, ChatMessagePacket::message)
      ChatMessagePacket(
          type = type,
          language = language,
          message = message,
          sender = sender,
      )
    }
  }
}
