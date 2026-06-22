package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S64LE

data class ShopSellRequestPacket(
    val itemEntityId: Long,
    val quantity: Short,
)

object ShopSellRequestPacketCodec : PacketCodec<ShopSellRequestPacket>() {
  override fun CodecScope<ShopSellRequestPacket>.body(): ShopSellRequestPacket {
    val itemEntityId = field(S64LE) { it.itemEntityId }
    val quantity = field(S16LE) { it.quantity }
    return ShopSellRequestPacket(itemEntityId, quantity)
  }
}
