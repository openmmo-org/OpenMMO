package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class ChatMessageSendPacket(
    val mode: Byte,
    val target: String,
    val message: String?,
)

object ChatMessageSendPacketCodec : PacketCodec<ChatMessageSendPacket>() {
    override fun CodecScope<ChatMessageSendPacket>.body(): ChatMessageSendPacket {
        val mode = field(S8) { it.mode }
        val target = field(Utf16LeNullTerminated) { it.target }
        val message =
            if (mode.toInt() == 4) field(Utf16LeNullTerminated) { it.message ?: "" } else null
        return ChatMessageSendPacket(mode, target, message)
    }
}
