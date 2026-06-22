package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class MarketBoardEntry(
    val itemId: Short,
    val valueA: Int,
    val valueB: Int,
    val rank: Byte?,
)

data class MarketBoardPagePacket(
    val hasData: Byte,
    val page: Byte?,
    val category: Byte?,
    val total: Int?,
    val expiryOffsetMillis: Long?,
    val count: Int?,
    val categoryRanked: Boolean,
    val entries: List<MarketBoardEntry>,
)

object MarketBoardPagePacketCodec : PacketCodec<MarketBoardPagePacket>() {
  override fun CodecScope<MarketBoardPagePacket>.body(): MarketBoardPagePacket {
    val hasData = field(S8) { it.hasData }
    if (hasData < 1) {
      return MarketBoardPagePacket(hasData, null, null, null, null, null, false, emptyList())
    }
    val page = field(S8) { it.page!! }
    val category = field(S8) { it.category!! }
    val total = field(S32LE) { it.total!! }
    val expiryOffsetMillis = field(S64LE) { it.expiryOffsetMillis!! }
    val count = field(S32LE) { it.count!! }
    val categoryRanked = false
    val entryCount = field(S16LE) { it.entries.size.toShort() }.toInt()
    val entries = ArrayList<MarketBoardEntry>(entryCount)
    repeat(entryCount) { i ->
      val itemId = field(S16LE) { it.entries[i].itemId }
      val valueA = field(S32LE) { it.entries[i].valueA }
      val valueB = field(S32LE) { it.entries[i].valueB }
      val rank: Byte? = if (categoryRanked) field(S8) { it.entries[i].rank ?: 0 } else null
      entries.add(MarketBoardEntry(itemId, valueA, valueB, rank))
    }
    return MarketBoardPagePacket(
        hasData, page, category, total, expiryOffsetMillis, count, categoryRanked, entries)
  }
}
