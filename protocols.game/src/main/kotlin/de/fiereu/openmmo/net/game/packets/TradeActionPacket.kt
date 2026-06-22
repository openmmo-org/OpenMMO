package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class TradeActionPacket(val action: Byte)

object TradeActionPacketCodec : PacketCodec<TradeActionPacket>() {
  override fun CodecScope<TradeActionPacket>.body(): TradeActionPacket {
    val action = field(S8) { it.action }
    return TradeActionPacket(action)
  }
}
