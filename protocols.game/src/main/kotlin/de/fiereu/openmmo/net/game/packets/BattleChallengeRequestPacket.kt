package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleChallengeRequestPacket(
    val flags: Byte,
    val entryKindId: Byte,
    val battleFormat: Byte,
    val levelCap: Short,
    val allowedSpecies: List<Short>,
    val ruleSet: Byte,
    val ruleParam: Short,
    val extraRule: ByteArray?,
) {
    override fun equals(other: Any?): Boolean =
        other is BattleChallengeRequestPacket &&
                flags == other.flags &&
                entryKindId == other.entryKindId &&
                battleFormat == other.battleFormat &&
                levelCap == other.levelCap &&
                allowedSpecies == other.allowedSpecies &&
                ruleSet == other.ruleSet &&
                ruleParam == other.ruleParam &&
                (extraRule?.contentEquals(other.extraRule ?: ByteArray(0)) ?: (other.extraRule == null))

    override fun hashCode(): Int {
        var r = flags.toInt()
        r = r * 31 + entryKindId
        r = r * 31 + battleFormat
        r = r * 31 + levelCap
        r = r * 31 + allowedSpecies.hashCode()
        r = r * 31 + ruleSet
        r = r * 31 + ruleParam
        r = r * 31 + (extraRule?.contentHashCode() ?: 0)
        return r
    }
}

private val ExtraRuleBytes: Codec<ByteArray> =
    object : Codec<ByteArray> {
        override fun read(buf: ReadBuffer): ByteArray {
            val data = ByteArray(buf.remaining())
            if (data.isNotEmpty()) buf.readBytes(data)
            return data
        }

        override fun write(buf: WriteBuffer, value: ByteArray) {
            if (value.isNotEmpty()) buf.writeBytes(value)
        }
    }

object BattleChallengeRequestPacketCodec : PacketCodec<BattleChallengeRequestPacket>() {
    override fun CodecScope<BattleChallengeRequestPacket>.body(): BattleChallengeRequestPacket {
        val flags = field(S8) { it.flags }
        val entryKindId = field(S8) { it.entryKindId }
        val battleFormat = field(S8) { it.battleFormat }
        val levelCap = field(S16LE) { it.levelCap }
        val allowedSpecies = field(S16LE.listPrefixed(U8)) { it.allowedSpecies }
        val ruleSet = field(S8) { it.ruleSet }
        val ruleParam = field(S16LE) { it.ruleParam }
        val present = field(U8) { if (it.extraRule != null) 1 else 0 }
        val extraRule =
            if (present != 0) {
                field(ExtraRuleBytes) {
                    it.extraRule ?: throw MalformedPacketException("extraRule required")
                }
            } else {
                null
            }
        return BattleChallengeRequestPacket(
            flags,
            entryKindId,
            battleFormat,
            levelCap,
            allowedSpecies,
            ruleSet,
            ruleParam,
            extraRule,
        )
    }
}
