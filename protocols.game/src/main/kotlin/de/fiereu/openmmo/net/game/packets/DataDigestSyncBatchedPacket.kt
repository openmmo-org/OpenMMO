package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.bytesPrefixed

data class DataDigestSyncBatchedPacket(
    val bitCount: Int,
    val digest: ByteArray,
) {
    override fun equals(other: Any?): Boolean =
        other is DataDigestSyncBatchedPacket &&
                bitCount == other.bitCount &&
                digest.contentEquals(other.digest)

    override fun hashCode(): Int = bitCount * 31 + digest.contentHashCode()
}

private val DigestBytes = bytesPrefixed(U16LE)

object DataDigestSyncBatchedPacketCodec : PacketCodec<DataDigestSyncBatchedPacket>() {
    override fun CodecScope<DataDigestSyncBatchedPacket>.body(): DataDigestSyncBatchedPacket {
        val bitCount = field(U16LE) { it.bitCount }
        val digest = field(DigestBytes) { it.digest }
        return DataDigestSyncBatchedPacket(bitCount, digest)
    }
}
