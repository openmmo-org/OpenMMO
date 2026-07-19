package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

data class BattleMoveUsePacket(
    val targetEntityId: Long,
    val moveSlotIndex: Byte,
    val moveId: Short,
)

object BattleMoveUsePacketCodec : PacketCodec<BattleMoveUsePacket>() {
  override fun CodecScope<BattleMoveUsePacket>.body(): BattleMoveUsePacket {
    val targetEntityId = field(S64LE) { it.targetEntityId }
    val moveSlotIndex = field(S8) { it.moveSlotIndex }
    val moveId = field(S16LE) { it.moveId }
    return BattleMoveUsePacket(targetEntityId, moveSlotIndex, moveId)
  }
}
