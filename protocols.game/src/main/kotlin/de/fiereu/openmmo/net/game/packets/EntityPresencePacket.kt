package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class EntityPresencePacket(
    val entityId: Long,
    val status: Byte,
)

object EntityPresencePacketCodec : PacketCodec<EntityPresencePacket>() {
    override fun CodecScope<EntityPresencePacket>.body(): EntityPresencePacket {
        val entityId = field(S64LE) { it.entityId }
        val status = field(S8) { it.status }
        return EntityPresencePacket(entityId, status)
    }
}
