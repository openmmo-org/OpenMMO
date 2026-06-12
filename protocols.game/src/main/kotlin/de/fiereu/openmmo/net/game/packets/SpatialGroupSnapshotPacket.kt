package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SpatialGroupEntry(
    val entityId: Long,
    val value: Short,
    val extraValue: Short?,
)

data class SpatialGroupSnapshotPacket(
    val group: Int,
    val extraCoordPresent: Boolean,
    val entries: List<SpatialGroupEntry>,
)

object SpatialGroupSnapshotPacketCodec : PacketCodec<SpatialGroupSnapshotPacket>() {
    override fun CodecScope<SpatialGroupSnapshotPacket>.body(): SpatialGroupSnapshotPacket {
        val group = field(U8) { it.group }
        val extraCoordPresent = false
        val count = field(U16LE) { it.entries.size }
        val entries = ArrayList<SpatialGroupEntry>(count)
        repeat(count) { i ->
            val entityId = field(S64LE) { it.entries[i].entityId }
            val value = field(S16LE) { it.entries[i].value }
            val extraValue: Short? =
                if (extraCoordPresent) field(S16LE) { it.entries[i].extraValue ?: 0 } else null
            entries.add(SpatialGroupEntry(entityId, value, extraValue))
        }
        return SpatialGroupSnapshotPacket(group, extraCoordPresent, entries)
    }
}
