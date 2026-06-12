package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ChunkedTransferBeginPacket(
    val transferId: Long,
    val totalSize: Int,
    val data: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is ChunkedTransferBeginPacket &&
                transferId == other.transferId &&
                totalSize == other.totalSize &&
                data.contentEquals(other.data)

    override fun hashCode(): Int {
        var r = transferId.hashCode()
        r = r * 31 + totalSize
        r = r * 31 + data.contentHashCode()
        return r
    }
}

object ChunkedTransferBeginPacketCodec : PacketCodec<ChunkedTransferBeginPacket>() {
    override fun CodecScope<ChunkedTransferBeginPacket>.body(): ChunkedTransferBeginPacket {
        val transferId = field(S64LE) { it.transferId }
        val totalSize = field(S32LE) { it.totalSize }
        val data = field(bytesPrefixed(U16LE)) { it.data }
        return ChunkedTransferBeginPacket(transferId, totalSize, data)
    }
}
