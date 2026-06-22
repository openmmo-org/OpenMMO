package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class BattleSlotMoveEventPacket(
    val slot: Byte,
    val moveType: Byte,
    val resolved: Boolean,
)

object BattleSlotMoveEventPacketCodec : PacketCodec<BattleSlotMoveEventPacket>() {
  override fun CodecScope<BattleSlotMoveEventPacket>.body(): BattleSlotMoveEventPacket {
    val slot = field(S8) { it.slot }
    val moveType = field(S8) { it.moveType }
    val resolved = field(Bool) { it.resolved }
    return BattleSlotMoveEventPacket(slot, moveType, resolved)
  }
}
