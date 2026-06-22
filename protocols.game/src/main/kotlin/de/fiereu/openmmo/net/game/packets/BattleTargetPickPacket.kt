package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class BattleTargetPickPacket(
    val targetEntityId: Long,
)

object BattleTargetPickPacketCodec : PacketCodec<BattleTargetPickPacket>() {
    override fun CodecScope<BattleTargetPickPacket>.body(): BattleTargetPickPacket {
        val targetEntityId = field(S64LE) { it.targetEntityId }
        return BattleTargetPickPacket(targetEntityId)
    }
}
