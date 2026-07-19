package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

data class BattleBoardCellPacket(
    val row: Byte,
    val column: Short,
    val entityId: Long,
    val valueA: Short,
    val valueB: Short,
    val flags: Byte,
)

object BattleBoardCellPacketCodec : PacketCodec<BattleBoardCellPacket>() {
  override fun CodecScope<BattleBoardCellPacket>.body(): BattleBoardCellPacket {
    val row = field(S8) { it.row }
    val column = field(S16LE) { it.column }
    val entityId = field(S64LE) { it.entityId }
    val valueA = field(S16LE) { it.valueA }
    val valueB = field(S16LE) { it.valueB }
    val flags = field(S8) { it.flags }
    return BattleBoardCellPacket(row, column, entityId, valueA, valueB, flags)
  }
}
