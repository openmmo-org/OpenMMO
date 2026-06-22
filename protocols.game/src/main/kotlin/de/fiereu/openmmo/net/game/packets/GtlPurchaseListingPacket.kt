package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S8

data class GtlPurchaseListingPacket(
    val listingId: Int,
    val price: Int,
    val action: Byte,
)

object GtlPurchaseListingPacketCodec : PacketCodec<GtlPurchaseListingPacket>() {
    override fun CodecScope<GtlPurchaseListingPacket>.body(): GtlPurchaseListingPacket {
        val listingId = field(S32LE) { it.listingId }
        val price = field(S32LE) { it.price }
        val action = field(S8) { it.action }
        return GtlPurchaseListingPacket(listingId, price, action)
    }
}
