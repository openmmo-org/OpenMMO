package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GtlMarketListing(
    val listingId: Int,
    val typeId: Byte,
    val sellerId: Byte,
    val speciesId: Short,
    val value1: Int,
    val value2: Int,
    val value3: Int,
    val ownListing: Boolean,
    val value4: Int,
    val value5: Int,
    val value6: Int,
    val value7: Int,
    val form: Byte,
    val rowIndex: Int,
)

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

private object GtlMarketListingCodec : PacketCodec<GtlMarketListing>() {
  override fun CodecScope<GtlMarketListing>.body(): GtlMarketListing {
    val listingId = field(S32LE, GtlMarketListing::listingId)
    val typeId = field(S8, GtlMarketListing::typeId)
    val sellerId = field(S8, GtlMarketListing::sellerId)
    val speciesId = field(S16LE, GtlMarketListing::speciesId)
    val value1 = field(S32LE, GtlMarketListing::value1)
    val value2 = field(S32LE, GtlMarketListing::value2)
    val value3 = field(S32LE, GtlMarketListing::value3)
    val ownListing = field(Bool, GtlMarketListing::ownListing)
    val value4 = field(S32LE, GtlMarketListing::value4)
    val value5 = field(S32LE, GtlMarketListing::value5)
    val value6 = field(S32LE, GtlMarketListing::value6)
    val value7 = field(S32LE, GtlMarketListing::value7)
    val form = field(S8, GtlMarketListing::form)
    reserved(byte = 0)
    val rowIndex = field(S32LE, GtlMarketListing::rowIndex)
    return GtlMarketListing(
        listingId,
        typeId,
        sellerId,
        speciesId,
        value1,
        value2,
        value3,
        ownListing,
        value4,
        value5,
        value6,
        value7,
        form,
        rowIndex,
    )
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
