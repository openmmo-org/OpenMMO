package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class BattlePromptSwitchPacket(
    val enabled: Boolean,
)

object BattlePromptSwitchPacketCodec : PacketCodec<BattlePromptSwitchPacket>() {
  override fun CodecScope<BattlePromptSwitchPacket>.body(): BattlePromptSwitchPacket {
    val enabled = field(Bool) { it.enabled }
    return BattlePromptSwitchPacket(enabled)
  }
}
