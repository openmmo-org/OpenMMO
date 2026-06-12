package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class NpcUpdatePacket(
    val entityId: Long,
    val regionId: Int,
    val bankId: Int,
    val mapId: Int,
    val x: Int,
    val y: Int,
    val facing: Int,
    val unk: Int,
)

object NpcUpdatePacketCodec : PacketCodec<NpcUpdatePacket>() {
    override fun CodecScope<NpcUpdatePacket>.body(): NpcUpdatePacket {
        val entityId = field(S64LE) { it.entityId }
        val regionId = field(U8) { it.regionId }
        val bankId = field(U8) { it.bankId }
        val mapId = field(U8) { it.mapId }
        val x = field(U16LE) { it.x }
        val y = field(U16LE) { it.y }
        val facing = field(U8) { it.facing }
        val unk = field(U8) { it.unk }
        return NpcUpdatePacket(entityId, regionId, bankId, mapId, x, y, facing, unk)
    }
}
