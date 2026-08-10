package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

/** [stock] is 0x7FFF on every captured mart line, which the client reads as an endless shelf. */
data class ShopItem(
    val itemId: Short,
    val unknownA: Short,
    val stock: Short,
    val price: Int,
)

object ShopItemCodec : PacketCodec<ShopItem>() {
  override fun CodecScope<ShopItem>.body(): ShopItem {
    val itemId = field(S16LE) { it.itemId }
    val unknownA = field(S16LE) { it.unknownA }
    val stock = field(S16LE) { it.stock }
    val price = field(S32LE) { it.price }
    return ShopItem(itemId, unknownA, stock, price)
  }
}

/**
 * Opens or closes the shop window. A close is the single byte [CLOSED] with no body, so everything
 * after the first byte only rides along on an open.
 */
data class ShopCatalogPacket(
    val kind: Byte,
    val shopId: Short? = null,
    val unknownA: Int? = null,
    val npcEntityId: Long? = null,
    val items: List<ShopItem> = emptyList(),
) {
  companion object {
    const val OPEN: Byte = 0
    const val CLOSED: Byte = -1

    /**
     * [unknownA] is the same value in every capture, across three different shops, so it is sent
     * back rather than derived.
     */
    fun open(shopId: Short, npcEntityId: Long, items: List<ShopItem>): ShopCatalogPacket =
        ShopCatalogPacket(OPEN, shopId, CAPTURED_UNKNOWN_A, npcEntityId, items)

    private const val CAPTURED_UNKNOWN_A = 0x1A5190
  }
}

object ShopCatalogPacketCodec : PacketCodec<ShopCatalogPacket>() {
  override fun CodecScope<ShopCatalogPacket>.body(): ShopCatalogPacket {
    val kind = field(S8) { it.kind }
    val open = kind == ShopCatalogPacket.OPEN
    val shopId = optionalField(open, S16LE) { it.shopId }
    val unknownA = optionalField(open, S32LE) { it.unknownA }
    val npcEntityId = optionalField(open, S64LE) { it.npcEntityId }
    val items = optionalField(open, ShopItemCodec.listPrefixed(U16LE)) { it.items }
    return ShopCatalogPacket(kind, shopId, unknownA, npcEntityId, items ?: emptyList())
  }
}
