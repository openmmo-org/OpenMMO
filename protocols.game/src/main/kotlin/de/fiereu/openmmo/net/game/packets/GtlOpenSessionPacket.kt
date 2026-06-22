package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class GtlOpenSessionPacket(
    val entryKindId: Byte,
    val sessionTimestamp: Long,
)

object GtlOpenSessionPacketCodec : PacketCodec<GtlOpenSessionPacket>() {
    override fun CodecScope<GtlOpenSessionPacket>.body(): GtlOpenSessionPacket {
        val entryKindId = field(S8) { it.entryKindId }
        val sessionTimestamp = field(S64LE) { it.sessionTimestamp }
        return GtlOpenSessionPacket(entryKindId, sessionTimestamp)
    }
}
