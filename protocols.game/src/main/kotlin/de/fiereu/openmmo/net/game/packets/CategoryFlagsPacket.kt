package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class CategoryFlagsPacket(
    val entryKind: Int,
    val timestampMillis: Long,
    val flagBits: ByteArray,
) {
  override fun equals(other: Any?): Boolean =
      other is CategoryFlagsPacket &&
          entryKind == other.entryKind &&
          timestampMillis == other.timestampMillis &&
          flagBits.contentEquals(other.flagBits)

  override fun hashCode(): Int {
    var r = entryKind
    r = r * 31 + timestampMillis.hashCode()
    r = r * 31 + flagBits.contentHashCode()
    return r
  }
}

object CategoryFlagsPacketCodec : PacketCodec<CategoryFlagsPacket>() {
  override fun CodecScope<CategoryFlagsPacket>.body(): CategoryFlagsPacket {
    val entryKind = field(U8) { it.entryKind }
    val timestampMillis = field(S64LE) { it.timestampMillis }
    val flagBits = field(bytesPrefixed(U16LE)) { it.flagBits }
    return CategoryFlagsPacket(entryKind, timestampMillis, flagBits)
  }
}
