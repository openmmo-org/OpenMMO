package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleActionPacket(
    val actionTypeId: Byte,
    val isSlotTarget: Boolean,
    val targetSlot: Short?,
    val targetEntityId: Long?,
    val extraFlag: Byte,
)

object BattleActionPacketCodec : PacketCodec<BattleActionPacket>() {
  override fun CodecScope<BattleActionPacket>.body(): BattleActionPacket {
    val actionTypeId = field(S8) { it.actionTypeId }
    val isSlotTarget = field(Bool) { it.isSlotTarget }
    val targetSlot: Short? = if (isSlotTarget) field(S16LE) { it.targetSlot!! } else null
    val targetEntityId: Long? = if (!isSlotTarget) field(S64LE) { it.targetEntityId!! } else null
    val extraFlag = field(S8) { it.extraFlag }
    return BattleActionPacket(actionTypeId, isSlotTarget, targetSlot, targetEntityId, extraFlag)
  }
}
