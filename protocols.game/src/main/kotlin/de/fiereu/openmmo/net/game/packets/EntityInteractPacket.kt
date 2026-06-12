package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class EntityInteractPacket(val entityId: Long, val token: Long)

object EntityInteractPacketCodec : PacketCodec<EntityInteractPacket>() {
    override fun CodecScope<EntityInteractPacket>.body(): EntityInteractPacket {
        val entityId = field(S64LE) { it.entityId }
        val token = field(S64LE) { it.token }
        return EntityInteractPacket(entityId, token)
    }
}
