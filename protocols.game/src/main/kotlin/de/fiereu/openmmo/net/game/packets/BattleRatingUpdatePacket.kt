package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleRatingUpdatePacket(
    val entityId: Long,
    val tier: Byte,
    val bracket: Byte,
    val elo: Float,
    val short1: Short,
    val byte1: Byte,
    val int1: Int,
    val int2: Int,
)

object BattleRatingUpdatePacketCodec : PacketCodec<BattleRatingUpdatePacket>() {
    override fun CodecScope<BattleRatingUpdatePacket>.body(): BattleRatingUpdatePacket {
        val entityId = field(S64LE) { it.entityId }
        val tier = field(S8) { it.tier }
        val bracket = field(S8) { it.bracket }
        val elo = field(F32LE) { it.elo }
        val short1 = field(S16LE) { it.short1 }
        val byte1 = field(S8) { it.byte1 }
        val int1 = field(S32LE) { it.int1 }
        val int2 = field(S32LE) { it.int2 }
        return BattleRatingUpdatePacket(entityId, tier, bracket, elo, short1, byte1, int1, int2)
    }
}
