package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class DialogDataPacket(
    val entityId: Long,
    val unk1: Int,
    val type: Int,
    val data: ByteArray,
) {
  override fun equals(other: Any?): Boolean =
      other is DialogDataPacket &&
          entityId == other.entityId &&
          unk1 == other.unk1 &&
          type == other.type &&
          data.contentEquals(other.data)

  override fun hashCode(): Int {
    var result = entityId.hashCode()
    result = 31 * result + unk1
    result = 31 * result + type
    result = 31 * result + data.contentHashCode()
    return result
  }
}

object RemainingBytes : Codec<ByteArray> {
  override fun read(buf: ReadBuffer): ByteArray {
    val n = buf.remaining()
    val arr = ByteArray(n)
    if (n > 0) buf.readBytes(arr)
    return arr
  }

  override fun write(buf: WriteBuffer, value: ByteArray) {
    if (value.isNotEmpty()) buf.writeBytes(value)
  }
}

object DialogDataPacketCodec : PacketCodec<DialogDataPacket>() {
  override fun CodecScope<DialogDataPacket>.body(): DialogDataPacket {
    val entityId = field(S64LE) { it.entityId }
    val unk1 = field(U8) { it.unk1 }
    val type = field(U8) { it.type }
    val data = field(RemainingBytes) { it.data }
    return DialogDataPacket(entityId, unk1, type, data)
  }
}
