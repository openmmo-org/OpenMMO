package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class FollowerAdvancePacket(
    val entityId: Long,
    val step: Short,
    val direction: Byte,
    val flag: Boolean,
)

object FollowerAdvancePacketCodec : PacketCodec<FollowerAdvancePacket>() {
    override fun CodecScope<FollowerAdvancePacket>.body(): FollowerAdvancePacket {
        val entityId = field(S64LE) { it.entityId }
        val step = field(S16LE) { it.step }
        val direction = field(S8) { it.direction }
        val flag = field(Bool) { it.flag }
        return FollowerAdvancePacket(entityId, step, direction, flag)
    }
}
