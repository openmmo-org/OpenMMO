package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class BattleSlotEventEnumPacket(
    val slot: Byte,
    val eventType: Byte,
)

object BattleSlotEventEnumPacketCodec : PacketCodec<BattleSlotEventEnumPacket>() {
  override fun CodecScope<BattleSlotEventEnumPacket>.body(): BattleSlotEventEnumPacket {
    val slot = field(S8) { it.slot }
    val eventType = field(S8) { it.eventType }
    return BattleSlotEventEnumPacket(slot, eventType)
  }
}
