package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class PongPacket(
    val isServerPong: Boolean,
    val timestamp: Long,
)

object PongPacketCodec : PacketCodec<PongPacket>() {
    override fun CodecScope<PongPacket>.body(): PongPacket {
        val isServerPong = field(Bool) { it.isServerPong }
        val timestamp =
            if (isServerPong) {
                field(S64LE) { it.timestamp }
            } else {
                field(S64LE) { if (it.timestamp == -1L) System.currentTimeMillis() else it.timestamp }
            }
        return PongPacket(isServerPong, timestamp)
    }
}
