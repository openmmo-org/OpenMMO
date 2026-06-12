package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class MarketListingsRequestPacket(val tab: Byte, val categoryId: Byte)

object MarketListingsRequestPacketCodec : PacketCodec<MarketListingsRequestPacket>() {
    override fun CodecScope<MarketListingsRequestPacket>.body(): MarketListingsRequestPacket {
        val tab = field(S8) { it.tab }
        val categoryId = field(S8) { it.categoryId }
        return MarketListingsRequestPacket(tab, categoryId)
    }
}
