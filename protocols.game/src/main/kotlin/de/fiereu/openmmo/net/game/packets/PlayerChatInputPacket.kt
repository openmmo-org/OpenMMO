package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class PlayerChatInputPacket(
    val message: String,
)

object PlayerChatInputPacketCodec : PacketCodec<PlayerChatInputPacket>() {
    override fun CodecScope<PlayerChatInputPacket>.body(): PlayerChatInputPacket {
        val message = field(Utf16LeNullTerminated) { it.message }
        return PlayerChatInputPacket(message)
    }
}
