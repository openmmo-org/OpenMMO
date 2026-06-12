package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U8

data class SpatialGroupDeletePacket(
    val group: Int,
    val entityId: Long,
)

object SpatialGroupDeletePacketCodec : PacketCodec<SpatialGroupDeletePacket>() {
    override fun CodecScope<SpatialGroupDeletePacket>.body(): SpatialGroupDeletePacket {
        val group = field(U8) { it.group }
        val entityId = field(S64LE) { it.entityId }
        return SpatialGroupDeletePacket(group, entityId)
    }
}
