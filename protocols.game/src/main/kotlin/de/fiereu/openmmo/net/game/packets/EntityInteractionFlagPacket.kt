package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class EntityInteractionFlagPacket(
    val entityId: Long,
    val flag: Byte,
)

object EntityInteractionFlagPacketCodec : PacketCodec<EntityInteractionFlagPacket>() {
    override fun CodecScope<EntityInteractionFlagPacket>.body(): EntityInteractionFlagPacket {
        val entityId = field(S64LE) { it.entityId }
        val flag = field(S8) { it.flag }
        return EntityInteractionFlagPacket(entityId, flag)
    }
}
