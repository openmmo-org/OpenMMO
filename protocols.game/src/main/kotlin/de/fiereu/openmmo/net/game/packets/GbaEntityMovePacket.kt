package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.enumByOrdinalByte
import de.fiereu.openmmo.common.enums.Direction

/** Opcode 0xEA (s2c). A GBA-map entity's new tile after one step. */
data class GbaEntityMovePacket(
    val entityId: Long,
    val bankId: Int,
    val mapId: Int,
    val x: Int,
    val y: Int,
    val movementMode: Int = 2,
    val direction: Direction,
)

object GbaEntityMovePacketCodec : PacketCodec<GbaEntityMovePacket>() {
  private val DirectionCodec = enumByOrdinalByte<Direction>()

  override fun CodecScope<GbaEntityMovePacket>.body(): GbaEntityMovePacket {
    val entityId = field(S64LE) { it.entityId }
    val bankId = field(U8) { it.bankId }
    val mapId = field(U8) { it.mapId }
    val x = field(U8) { it.x }
    val y = field(U8) { it.y }
    val movementMode = field(U8) { it.movementMode }
    val direction = field(DirectionCodec) { it.direction }
    return GbaEntityMovePacket(entityId, bankId, mapId, x, y, movementMode, direction)
  }
}
