package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GtlPurchasePacket(
    val entryKindId: Byte,
    val itemTypeId: Short,
    val quantity: Short,
    val totalPrice: Int,
    val listingFlag: Byte,
)

object GtlPurchasePacketCodec : PacketCodec<GtlPurchasePacket>() {
    override fun CodecScope<GtlPurchasePacket>.body(): GtlPurchasePacket {
        val entryKindId = field(S8) { it.entryKindId }
        val itemTypeId = field(S16LE) { it.itemTypeId }
        val quantity = field(S16LE) { it.quantity }
        val totalPrice = field(S32LE) { it.totalPrice }
        val listingFlag = field(S8) { it.listingFlag }
        return GtlPurchasePacket(entryKindId, itemTypeId, quantity, totalPrice, listingFlag)
    }
}
