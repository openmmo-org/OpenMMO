package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ChunkedImagePacket(
    val controlType: Byte,
    val imageType: Byte?,
    val chunkIndex: Int?,
    val last: Boolean?,
    val data: ByteArray?,
) {
    override fun equals(other: Any?): Boolean =
        other is ChunkedImagePacket &&
                controlType == other.controlType &&
                imageType == other.imageType &&
                chunkIndex == other.chunkIndex &&
                last == other.last &&
                (if (data == null) other.data == null
                else other.data != null && data.contentEquals(other.data))

    override fun hashCode(): Int {
        var r = controlType.toInt()
        r = r * 31 + (imageType?.toInt() ?: 0)
        r = r * 31 + (chunkIndex ?: 0)
        r = r * 31 + (last?.hashCode() ?: 0)
        r = r * 31 + (data?.contentHashCode() ?: 0)
        return r
    }
}

object ChunkedImagePacketCodec : PacketCodec<ChunkedImagePacket>() {
    override fun CodecScope<ChunkedImagePacket>.body(): ChunkedImagePacket {
        val controlType = field(S8) { it.controlType }
        val present = controlType.toInt() != 2
        val imageType = if (present) field(S8) { it.imageType!! } else null
        val chunkIndex = if (present) field(U8) { it.chunkIndex!! } else null
        val last = if (present) field(U8) { if (it.last!!) 1 else 0 } == 1 else null
        val data = if (present) field(bytesPrefixed(U16LE)) { it.data!! } else null
        return ChunkedImagePacket(controlType, imageType, chunkIndex, last, data)
    }
}
