package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S64LE

data class GtlConfirmPurchasePacket(
    val listingEntityId: Long,
    val quantity: Short,
)

object GtlConfirmPurchasePacketCodec : PacketCodec<GtlConfirmPurchasePacket>() {
    override fun CodecScope<GtlConfirmPurchasePacket>.body(): GtlConfirmPurchasePacket {
        val listingEntityId = field(S64LE) { it.listingEntityId }
        val quantity = field(S16LE) { it.quantity }
        return GtlConfirmPurchasePacket(listingEntityId, quantity)
    }
}
