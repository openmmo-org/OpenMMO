package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class BattleStatusFlagPacket(
    val scope: Byte,
    val flagId: Short,
    val flagValue: Short,
)

object BattleStatusFlagPacketCodec : PacketCodec<BattleStatusFlagPacket>() {
  override fun CodecScope<BattleStatusFlagPacket>.body(): BattleStatusFlagPacket {
    val scope = field(S8) { it.scope }
    val flagId = field(S16LE) { it.flagId }
    val flagValue = field(S16LE) { it.flagValue }
    return BattleStatusFlagPacket(scope, flagId, flagValue)
  }
}
