package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class BattleSlotAnimationResetPacket(
    val entityId: Long,
    val slot: Byte,
)

object BattleSlotAnimationResetPacketCodec : PacketCodec<BattleSlotAnimationResetPacket>() {
  override fun CodecScope<BattleSlotAnimationResetPacket>.body(): BattleSlotAnimationResetPacket {
    val entityId = field(S64LE) { it.entityId }
    val slot = field(S8) { it.slot }
    return BattleSlotAnimationResetPacket(entityId, slot)
  }
}
