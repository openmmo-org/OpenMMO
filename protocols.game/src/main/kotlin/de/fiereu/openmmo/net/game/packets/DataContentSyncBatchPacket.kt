package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class DataContentSyncBatchPacket(
    val count: Int,
    val recordsData: ByteArray,
) {
  override fun equals(other: Any?): Boolean =
      other is DataContentSyncBatchPacket &&
          count == other.count &&
          recordsData.contentEquals(other.recordsData)

  override fun hashCode(): Int = count * 31 + recordsData.contentHashCode()
}

private val ContentRecordsBytes: Codec<ByteArray> =
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

object DataContentSyncBatchPacketCodec : PacketCodec<DataContentSyncBatchPacket>() {
  override fun CodecScope<DataContentSyncBatchPacket>.body(): DataContentSyncBatchPacket {
    val count = field(U8) { it.count }
    val recordsData = field(ContentRecordsBytes) { it.recordsData }
    return DataContentSyncBatchPacket(count, recordsData)
  }
}
