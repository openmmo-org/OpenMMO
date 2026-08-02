package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

/** Empty payload means the client is ready. */
data class MapLoadedAckPacket(val data: ByteArray = byteArrayOf()) {
  override fun equals(other: Any?): Boolean =
      other is MapLoadedAckPacket && data.contentEquals(other.data)

  override fun hashCode(): Int = data.contentHashCode()
}

private val MapLoadedAckBytes: Codec<ByteArray> =
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

object MapLoadedAckPacketCodec : PacketCodec<MapLoadedAckPacket>() {
  override fun CodecScope<MapLoadedAckPacket>.body(): MapLoadedAckPacket {
    return MapLoadedAckPacket(field(MapLoadedAckBytes, MapLoadedAckPacket::data))
  }
}
