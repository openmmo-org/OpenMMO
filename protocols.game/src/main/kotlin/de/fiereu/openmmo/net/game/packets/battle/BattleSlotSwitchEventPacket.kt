package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class BattleSlotSwitchEventPacket(
    val slot: Byte,
    val switchType: Byte,
    val immediate: Boolean,
)

object BattleSlotSwitchEventPacketCodec : PacketCodec<BattleSlotSwitchEventPacket>() {
  override fun CodecScope<BattleSlotSwitchEventPacket>.body(): BattleSlotSwitchEventPacket {
    val slot = field(S8) { it.slot }
    val switchType = field(S8) { it.switchType }
    val immediate = field(Bool) { it.immediate }
    return BattleSlotSwitchEventPacket(slot, switchType, immediate)
  }
}
