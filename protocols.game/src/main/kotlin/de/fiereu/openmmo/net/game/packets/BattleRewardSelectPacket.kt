package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S64LE

data class BattleRewardSelectPacket(
    val targetEntityId: Long,
    val rewardIndex: Int,
)

object BattleRewardSelectPacketCodec : PacketCodec<BattleRewardSelectPacket>() {
    override fun CodecScope<BattleRewardSelectPacket>.body(): BattleRewardSelectPacket {
        val targetEntityId = field(S64LE) { it.targetEntityId }
        val rewardIndex = field(S32LE) { it.rewardIndex }
        return BattleRewardSelectPacket(targetEntityId, rewardIndex)
    }
}
