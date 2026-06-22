package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64BE

data class EntityLeavePacket(val entityId: Long)

object EntityLeavePacketCodec : PacketCodec<EntityLeavePacket>() {
    override fun CodecScope<EntityLeavePacket>.body(): EntityLeavePacket {
        val entityId = field(S64BE) { it.entityId }
        return EntityLeavePacket(entityId)
    }
}
