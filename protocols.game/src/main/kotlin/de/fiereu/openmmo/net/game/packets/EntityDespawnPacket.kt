package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.reserved

data class EntityDespawnPacket(
    val entityId: Short,
)

object EntityDespawnPacketCodec : PacketCodec<EntityDespawnPacket>() {
    override fun CodecScope<EntityDespawnPacket>.body(): EntityDespawnPacket {
        reserved(byte = 0)
        val entityId = field(S16LE) { it.entityId }
        return EntityDespawnPacket(entityId)
    }
}
