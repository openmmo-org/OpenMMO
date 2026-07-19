package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class BattleEnumPairEventPacket(
    val eventType: Byte,
    val first: Byte,
    val second: Byte,
)

object BattleEnumPairEventPacketCodec : PacketCodec<BattleEnumPairEventPacket>() {
  override fun CodecScope<BattleEnumPairEventPacket>.body(): BattleEnumPairEventPacket {
    val eventType = field(S8) { it.eventType }
    val first = field(S8) { it.first }
    val second = field(S8) { it.second }
    return BattleEnumPairEventPacket(eventType, first, second)
  }
}
