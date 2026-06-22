package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE

data class TradeSelectMonPacket(val slotIndex: Int)

object TradeSelectMonPacketCodec : PacketCodec<TradeSelectMonPacket>() {
  override fun CodecScope<TradeSelectMonPacket>.body(): TradeSelectMonPacket {
    val slotIndex = field(S32LE) { it.slotIndex }
    return TradeSelectMonPacket(slotIndex)
  }
}
