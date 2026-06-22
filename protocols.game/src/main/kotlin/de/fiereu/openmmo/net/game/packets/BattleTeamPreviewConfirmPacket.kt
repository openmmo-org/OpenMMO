package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE

data class BattleTeamPreviewConfirmPacket(
    val side: Short,
    val isOwnTeam: Boolean,
)

object BattleTeamPreviewConfirmPacketCodec : PacketCodec<BattleTeamPreviewConfirmPacket>() {
    override fun CodecScope<BattleTeamPreviewConfirmPacket>.body(): BattleTeamPreviewConfirmPacket {
        val side = field(S16LE) { it.side }
        val isOwnTeam = field(Bool) { it.isOwnTeam }
        return BattleTeamPreviewConfirmPacket(side, isOwnTeam)
    }
}
