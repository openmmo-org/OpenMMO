package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GtlMarketEntry(
    val price: Int,
    val quantity: Long,
)

data class GtlItemMarketPacket(
    val itemType: Int,
    val variant: Short,
    val count: Int,
    val quantities: List<Int>,
    val entries: List<GtlMarketEntry>,
)

private object GtlMarketEntryCodec : PacketCodec<GtlMarketEntry>() {
  override fun CodecScope<GtlMarketEntry>.body(): GtlMarketEntry {
    val price = field(S32LE, GtlMarketEntry::price)
    val quantity = field(S64LE, GtlMarketEntry::quantity)
    return GtlMarketEntry(price, quantity)
  }
}

private val GtlMarketEntryListPrefixedU8: Codec<List<GtlMarketEntry>> =
    object : Codec<List<GtlMarketEntry>> {
      override fun read(buf: ReadBuffer): List<GtlMarketEntry> {
        val n = U8.read(buf)
        return List(n) { GtlMarketEntryCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<GtlMarketEntry>) {
        U8.write(buf, value.size)
        value.forEach { GtlMarketEntryCodec.write(buf, it) }
      }
    }

object GtlItemMarketPacketCodec : PacketCodec<GtlItemMarketPacket>() {
  override fun CodecScope<GtlItemMarketPacket>.body(): GtlItemMarketPacket {
    val itemType = field(U8, GtlItemMarketPacket::itemType)
    val variant = field(S16LE, GtlItemMarketPacket::variant)
    val count = field(S32LE, GtlItemMarketPacket::count)
    val quantities = field(S32LE.repeat(3), GtlItemMarketPacket::quantities)
    val entries = field(GtlMarketEntryListPrefixedU8, GtlItemMarketPacket::entries)
    return GtlItemMarketPacket(itemType, variant, count, quantities, entries)
  }
}
