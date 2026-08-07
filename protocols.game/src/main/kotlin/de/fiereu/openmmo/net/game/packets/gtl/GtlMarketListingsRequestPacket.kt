package de.fiereu.openmmo.net.game.packets.gtl

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class GtlMarketListingsRequestPacket

object GtlMarketListingsRequestPacketCodec : PacketCodec<GtlMarketListingsRequestPacket>() {
  override fun CodecScope<GtlMarketListingsRequestPacket>.body(): GtlMarketListingsRequestPacket {
    return GtlMarketListingsRequestPacket()
  }
}
