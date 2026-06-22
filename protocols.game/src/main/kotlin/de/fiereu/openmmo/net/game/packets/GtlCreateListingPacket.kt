package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GtlCreateListingPacket(
    val categoryIndex: Byte,
    val listingCount: Byte,
    val itemKind: Byte,
    val priceShort: Short,
    val filterCriteria: ByteArray,
) {
  override fun equals(other: Any?): Boolean =
      other is GtlCreateListingPacket &&
          categoryIndex == other.categoryIndex &&
          listingCount == other.listingCount &&
          itemKind == other.itemKind &&
          priceShort == other.priceShort &&
          filterCriteria.contentEquals(other.filterCriteria)

  override fun hashCode(): Int {
    var r = categoryIndex.toInt()
    r = r * 31 + listingCount
    r = r * 31 + itemKind
    r = r * 31 + priceShort
    r = r * 31 + filterCriteria.contentHashCode()
    return r
  }
}

private val FilterCriteriaBytes: Codec<ByteArray> =
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

object GtlCreateListingPacketCodec : PacketCodec<GtlCreateListingPacket>() {
  override fun CodecScope<GtlCreateListingPacket>.body(): GtlCreateListingPacket {
    val categoryIndex = field(S8) { it.categoryIndex }
    val listingCount = field(S8) { it.listingCount }
    val itemKind = field(S8) { it.itemKind }
    val priceShort = field(S16LE) { it.priceShort }
    val filterCriteria = field(FilterCriteriaBytes) { it.filterCriteria }
    return GtlCreateListingPacket(categoryIndex, listingCount, itemKind, priceShort, filterCriteria)
  }
}
