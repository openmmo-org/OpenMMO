package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class EntityCoordSyncPacket(
    val entityId: Long,
    val valueA: Int,
    val flag: Boolean,
    val valueB: Int,
)

object EntityCoordSyncPacketCodec : PacketCodec<EntityCoordSyncPacket>() {
    override fun CodecScope<EntityCoordSyncPacket>.body(): EntityCoordSyncPacket {
        val entityId = field(S64LE) { it.entityId }
        val valueA = field(S32LE) { it.valueA }
        val flag = field(U8) { if (it.flag) 1 else 0 } == 1
        field(S8) { 0 }
        val valueB = field(S32LE) { it.valueB }
        return EntityCoordSyncPacket(entityId, valueA, flag, valueB)
    }
}
