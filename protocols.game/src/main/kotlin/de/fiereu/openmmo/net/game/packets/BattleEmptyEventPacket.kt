package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class BattleEmptyEventPacket(
    val unit: Unit = Unit,
)

object BattleEmptyEventPacketCodec : PacketCodec<BattleEmptyEventPacket>() {
  override fun CodecScope<BattleEmptyEventPacket>.body(): BattleEmptyEventPacket {
    return BattleEmptyEventPacket()
  }
}
