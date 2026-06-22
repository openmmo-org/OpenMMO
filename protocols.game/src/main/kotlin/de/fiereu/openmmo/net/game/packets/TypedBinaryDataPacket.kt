package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class TypedBinaryDataPacket(
    val dataType: Byte,
    val data: ByteArray,
) {
  override fun equals(other: Any?): Boolean =
      other is TypedBinaryDataPacket && dataType == other.dataType && data.contentEquals(other.data)

  override fun hashCode(): Int = dataType * 31 + data.contentHashCode()
}

private val TypedBinaryDataBytes = bytesPrefixed(U8)

object TypedBinaryDataPacketCodec : PacketCodec<TypedBinaryDataPacket>() {
  override fun CodecScope<TypedBinaryDataPacket>.body(): TypedBinaryDataPacket {
    val dataType = field(S8) { it.dataType }
    val data = field(TypedBinaryDataBytes) { it.data }
    return TypedBinaryDataPacket(dataType, data)
  }
}
