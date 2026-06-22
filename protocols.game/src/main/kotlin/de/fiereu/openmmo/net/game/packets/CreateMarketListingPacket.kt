package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class CreateMarketListingPacket(
    val entryKind: Byte,
    val entityId: Long,
    val price: Int,
    val quantity: Short,
)

object CreateMarketListingPacketCodec : PacketCodec<CreateMarketListingPacket>() {
  override fun CodecScope<CreateMarketListingPacket>.body(): CreateMarketListingPacket {
    val entryKind = field(S8) { it.entryKind }
    val entityId = field(S64LE) { it.entityId }
    val price = field(S32LE) { it.price }
    val quantity = field(S16LE) { it.quantity }
    return CreateMarketListingPacket(entryKind, entityId, price, quantity)
  }
}
