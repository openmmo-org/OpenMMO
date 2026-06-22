package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class DataDigestSyncResponsePacket(
    val count: Int,
    val entriesData: ByteArray,
) {
  override fun equals(other: Any?): Boolean =
      other is DataDigestSyncResponsePacket &&
          count == other.count &&
          entriesData.contentEquals(other.entriesData)

  override fun hashCode(): Int = count * 31 + entriesData.contentHashCode()
}

private val DigestEntriesBytes: Codec<ByteArray> =
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

object DataDigestSyncResponsePacketCodec : PacketCodec<DataDigestSyncResponsePacket>() {
  override fun CodecScope<DataDigestSyncResponsePacket>.body(): DataDigestSyncResponsePacket {
    val count = field(U8) { it.count }
    val entriesData = field(DigestEntriesBytes) { it.entriesData }
    return DataDigestSyncResponsePacket(count, entriesData)
  }
}
