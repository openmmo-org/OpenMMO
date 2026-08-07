package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.ReadBuffer
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.WriteBuffer
import de.fiereu.bytecodec.bytesPrefixed

data class GtlMarketListingsPacket(
    val fullReset: Boolean,
    val applyToBoards: Boolean,
    val referenceData: ByteArray?,
    val listings: List<GtlMarketListing>,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is GtlMarketListingsPacket) return false
    return fullReset == other.fullReset &&
        applyToBoards == other.applyToBoards &&
        (referenceData?.contentEquals(other.referenceData) ?: (other.referenceData == null)) &&
        listings == other.listings
  }

  override fun hashCode(): Int {
    var result = fullReset.hashCode()
    result = 31 * result + applyToBoards.hashCode()
    result = 31 * result + (referenceData?.contentHashCode() ?: 0)
    result = 31 * result + listings.hashCode()
    return result
  }
}

private val GtlMarketListingListPrefixedU8: Codec<List<GtlMarketListing>> =
    object : Codec<List<GtlMarketListing>> {
      override fun read(buf: ReadBuffer): List<GtlMarketListing> {
        val n = U8.read(buf)
        return List(n) { GtlMarketListingCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<GtlMarketListing>) {
        U8.write(buf, value.size)
        value.forEach { GtlMarketListingCodec.write(buf, it) }
      }
    }

object GtlMarketListingsPacketCodec : PacketCodec<GtlMarketListingsPacket>() {
  override fun CodecScope<GtlMarketListingsPacket>.body(): GtlMarketListingsPacket {
    val fullReset = field(Bool, GtlMarketListingsPacket::fullReset)
    val applyToBoards = field(Bool, GtlMarketListingsPacket::applyToBoards)
    val referenceData =
        if (fullReset) field(bytesPrefixed(U8)) { it.referenceData ?: ByteArray(0) } else null
    val listings = field(GtlMarketListingListPrefixedU8, GtlMarketListingsPacket::listings)
    return GtlMarketListingsPacket(fullReset, applyToBoards, referenceData, listings)
  }
}
