package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ChunkedTransferDataPacket(
    val transferId: Long,
    val totalSize: Int,
    val chunkData: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is ChunkedTransferDataPacket &&
                transferId == other.transferId &&
                totalSize == other.totalSize &&
                chunkData.contentEquals(other.chunkData)

    override fun hashCode(): Int {
        var r = transferId.hashCode()
        r = r * 31 + totalSize
        r = r * 31 + chunkData.contentHashCode()
        return r
    }
}

private val ChunkBytes: Codec<ByteArray> =
    object : Codec<ByteArray> {
        override fun read(buf: ReadBuffer): ByteArray {
            val length = S16LE.read(buf).toInt()
            val data = ByteArray(length)
            if (length > 0) buf.readBytes(data)
            return data
        }

        override fun write(buf: WriteBuffer, value: ByteArray) {
            S16LE.write(buf, value.size.toShort())
            if (value.isNotEmpty()) buf.writeBytes(value)
        }
    }

object ChunkedTransferDataPacketCodec : PacketCodec<ChunkedTransferDataPacket>() {
    override fun CodecScope<ChunkedTransferDataPacket>.body(): ChunkedTransferDataPacket {
        val transferId = field(S64LE) { it.transferId }
        val totalSize = field(S32LE) { it.totalSize }
        val chunkData = field(ChunkBytes) { it.chunkData }
        return ChunkedTransferDataPacket(transferId, totalSize, chunkData)
    }
}
