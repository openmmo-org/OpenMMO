package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleRatingEntry(
    val entityId: Long,
    val tier: Byte,
    val bracket: Byte,
    val elo: Float,
    val short1: Short,
    val byte1: Byte,
    val int1: Int,
    val int2: Int,
)

private val BattleRatingEntryCodec: Codec<BattleRatingEntry> =
    object : PacketCodec<BattleRatingEntry>() {
        override fun CodecScope<BattleRatingEntry>.body(): BattleRatingEntry {
            val entityId = field(S64LE) { it.entityId }
            val tier = field(S8) { it.tier }
            val bracket = field(S8) { it.bracket }
            val elo = field(F32LE) { it.elo }
            val short1 = field(S16LE) { it.short1 }
            val byte1 = field(S8) { it.byte1 }
            val int1 = field(S32LE) { it.int1 }
            val int2 = field(S32LE) { it.int2 }
            return BattleRatingEntry(entityId, tier, bracket, elo, short1, byte1, int1, int2)
        }
    }

data class BattleRatingBulkPacket(
    val ratings: List<BattleRatingEntry>,
)

object BattleRatingBulkPacketCodec : PacketCodec<BattleRatingBulkPacket>() {
    override fun CodecScope<BattleRatingBulkPacket>.body(): BattleRatingBulkPacket {
        val ratings = field(BattleRatingEntryCodec.listPrefixed(U8)) { it.ratings }
        return BattleRatingBulkPacket(ratings)
    }
}
