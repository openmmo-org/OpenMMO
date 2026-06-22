package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class BattleActionPromptTogglePacket(
    val shown: Boolean,
)

object BattleActionPromptTogglePacketCodec : PacketCodec<BattleActionPromptTogglePacket>() {
    override fun CodecScope<BattleActionPromptTogglePacket>.body(): BattleActionPromptTogglePacket {
        val shown = field(Bool) { it.shown }
        return BattleActionPromptTogglePacket(shown)
    }
}
