package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class ExchangeItemRequestPacket(
    val itemEntityRef: Short,
    val quantity: Short,
    val exchangeTypeIndex: Byte,
)

object ExchangeItemRequestPacketCodec : PacketCodec<ExchangeItemRequestPacket>() {
    override fun CodecScope<ExchangeItemRequestPacket>.body(): ExchangeItemRequestPacket {
        val itemEntityRef = field(S16LE) { it.itemEntityRef }
        val quantity = field(S16LE) { it.quantity }
        val exchangeTypeIndex = field(S8) { it.exchangeTypeIndex }
        return ExchangeItemRequestPacket(itemEntityRef, quantity, exchangeTypeIndex)
    }
}
