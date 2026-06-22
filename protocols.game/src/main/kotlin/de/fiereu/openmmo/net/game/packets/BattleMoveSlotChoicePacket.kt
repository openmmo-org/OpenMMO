package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class BattleMoveSlotChoicePacket(val moveSlotIndex: Byte)

object BattleMoveSlotChoicePacketCodec : PacketCodec<BattleMoveSlotChoicePacket>() {
  override fun CodecScope<BattleMoveSlotChoicePacket>.body(): BattleMoveSlotChoicePacket {
    val moveSlotIndex = field(S8) { it.moveSlotIndex }
    return BattleMoveSlotChoicePacket(moveSlotIndex)
  }
}
