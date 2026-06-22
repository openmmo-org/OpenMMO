package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.bytesPrefixed

data class ChunkedTransferAppendPacket(
    val data: ByteArray,
) {
  override fun equals(other: Any?): Boolean =
      other is ChunkedTransferAppendPacket && data.contentEquals(other.data)

  override fun hashCode(): Int = data.contentHashCode()
}

object ChunkedTransferAppendPacketCodec : PacketCodec<ChunkedTransferAppendPacket>() {
  override fun CodecScope<ChunkedTransferAppendPacket>.body(): ChunkedTransferAppendPacket {
    val data = field(bytesPrefixed(U16LE)) { it.data }
    return ChunkedTransferAppendPacket(data)
  }
}
