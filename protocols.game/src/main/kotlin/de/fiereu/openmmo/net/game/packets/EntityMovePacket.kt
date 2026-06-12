package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.enums.Direction

data class EntityMovePacket(
    val entityId: Long,
    val marker: Byte = 0x33,
    val subtype: Byte = 0x03,
    val seq: Byte = 0,
    val x: Byte,
    val y: Byte,
    val direction: Direction,
)

object EntityMovePacketCodec : PacketCodec<EntityMovePacket>() {
    private val DirectionCodec = enumByOrdinalByte<Direction>()

    override fun CodecScope<EntityMovePacket>.body(): EntityMovePacket {
        val entityId = field(S64BE) { it.entityId }
        val marker = field(S8) { it.marker }
        val subtype = field(S8) { it.subtype }
        val seq = field(S8) { it.seq }
        val x = field(S8) { it.x }
        val y = field(S8) { it.y }
        val direction = field(DirectionCodec) { it.direction }
        return EntityMovePacket(entityId, marker, subtype, seq, x, y, direction)
    }
}
