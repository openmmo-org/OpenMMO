package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE

data class QueuePositionPacket(
    val position: Short,
    val total: Short,
)

object QueuePositionPacketCodec : PacketCodec<QueuePositionPacket>() {
    override fun CodecScope<QueuePositionPacket>.body(): QueuePositionPacket {
        val position = field(S16LE) { it.position }
        val total = field(S16LE) { it.total }
        return QueuePositionPacket(position, total)
    }
}
