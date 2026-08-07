package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.reserved

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

internal object GtlMarketListingCodec : PacketCodec<GtlMarketListing>() {
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
    reserved(byte = 1)
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
