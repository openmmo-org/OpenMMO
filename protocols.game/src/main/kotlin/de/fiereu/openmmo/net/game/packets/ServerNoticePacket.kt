package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ServerNoticePacket(
    val type: Short,
    val id: Int,
    val message: String,
)

object ServerNoticePacketCodec : PacketCodec<ServerNoticePacket>() {
    override fun CodecScope<ServerNoticePacket>.body(): ServerNoticePacket {
        val type = field(S16LE, ServerNoticePacket::type)
        val id = field(S32LE, ServerNoticePacket::id)
        val message = field(Utf16LeNullTerminated, ServerNoticePacket::message)
        return ServerNoticePacket(type, id, message)
    }
}
