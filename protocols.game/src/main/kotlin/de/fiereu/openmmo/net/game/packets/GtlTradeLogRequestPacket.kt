package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class GtlTradeLogRequestPacket

object GtlTradeLogRequestPacketCodec : PacketCodec<GtlTradeLogRequestPacket>() {
    override fun CodecScope<GtlTradeLogRequestPacket>.body(): GtlTradeLogRequestPacket {
        return GtlTradeLogRequestPacket()
    }
}
