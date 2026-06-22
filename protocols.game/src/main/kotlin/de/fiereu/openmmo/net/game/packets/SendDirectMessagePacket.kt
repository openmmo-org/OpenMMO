package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class SendDirectMessagePacket(
    val senderContextId: Long,
    val recipientId: Long,
)

object SendDirectMessagePacketCodec : PacketCodec<SendDirectMessagePacket>() {
    override fun CodecScope<SendDirectMessagePacket>.body(): SendDirectMessagePacket {
        val senderContextId = field(S64LE) { it.senderContextId }
        val recipientId = field(S64LE) { it.recipientId }
        return SendDirectMessagePacket(senderContextId, recipientId)
    }
}
