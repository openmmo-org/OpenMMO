package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.enumByOrdinalByte
import de.fiereu.openmmo.common.enums.Direction

/** Opcode 0xE4 (s2c). Another entity's tile after one step and the direction it walked. */
data class EntityMovePacket(
    val entityId: Long,
    val x: Int,
    val y: Int,
    val direction: Direction,
)

object EntityMovePacketCodec : PacketCodec<EntityMovePacket>() {
  private val DirectionCodec = enumByOrdinalByte<Direction>()

  override fun CodecScope<EntityMovePacket>.body(): EntityMovePacket {
    val entityId = field(S64LE) { it.entityId }
    val x = field(S16LE) { it.x.toShort() }.toInt()
    val y = field(S16LE) { it.y.toShort() }.toInt()
    val direction = field(DirectionCodec) { it.direction }
    return EntityMovePacket(entityId, x, y, direction)
  }
}
