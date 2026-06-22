package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class WorldFlagTableResetPacket(
    val flagGroups: List<ByteArray>,
) {
    override fun equals(other: Any?): Boolean =
        other is WorldFlagTableResetPacket &&
                flagGroups.size == other.flagGroups.size &&
                flagGroups.indices.all { flagGroups[it].contentEquals(other.flagGroups[it]) }

    override fun hashCode(): Int {
        var r = 1
        for (g in flagGroups) r = r * 31 + g.contentHashCode()
        return r
    }
}

private val FlagGroupBytes = bytesPrefixed(U16LE)

object WorldFlagTableResetPacketCodec : PacketCodec<WorldFlagTableResetPacket>() {
    override fun CodecScope<WorldFlagTableResetPacket>.body(): WorldFlagTableResetPacket {
        val flagGroups = field(FlagGroupBytes.listPrefixed(U8)) { it.flagGroups }
        return WorldFlagTableResetPacket(flagGroups)
    }
}
