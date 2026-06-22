package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ShopPriceTablePacket(
    val category: Byte,
    val prices: List<Pair<Short, Short>>,
)

private val PriceEntryCodec: Codec<Pair<Short, Short>> = S16LE.then(S16LE)

object ShopPriceTablePacketCodec : PacketCodec<ShopPriceTablePacket>() {
    override fun CodecScope<ShopPriceTablePacket>.body(): ShopPriceTablePacket {
        val category = field(S8) { it.category }
        val prices = field(PriceEntryCodec.listPrefixed(U16LE)) { it.prices }
        return ShopPriceTablePacket(category, prices)
    }
}
