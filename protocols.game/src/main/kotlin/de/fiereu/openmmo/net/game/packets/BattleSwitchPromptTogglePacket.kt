package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class BattleSwitchPromptTogglePacket(
    val shown: Boolean,
)

object BattleSwitchPromptTogglePacketCodec : PacketCodec<BattleSwitchPromptTogglePacket>() {
  override fun CodecScope<BattleSwitchPromptTogglePacket>.body(): BattleSwitchPromptTogglePacket {
    val shown = field(Bool) { it.shown }
    return BattleSwitchPromptTogglePacket(shown)
  }
}
