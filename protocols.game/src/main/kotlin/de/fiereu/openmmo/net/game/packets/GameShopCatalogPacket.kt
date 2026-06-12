package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GameShopItem(
    val itemId: Long,
    val value1: Int,
    val value2: Int,
    val category: Byte,
    val cost: Int,
)

data class GameShopCatalogPacket(
    val points: Int,
    val items: List<GameShopItem>,
)

private object GameShopItemCodec : PacketCodec<GameShopItem>() {
    override fun CodecScope<GameShopItem>.body(): GameShopItem {
        field(S64LE) { 0L }
        field(S32LE) { 0 }
        val itemId = field(S64LE, GameShopItem::itemId)
        val value1 = field(S32LE, GameShopItem::value1)
        val value2 = field(S32LE, GameShopItem::value2)
        reserved(byte = 0)
        val category = field(S8, GameShopItem::category)
        val cost = field(S32LE, GameShopItem::cost)
        field(S32LE) { 0 }
        return GameShopItem(itemId, value1, value2, category, cost)
    }
}

private val GameShopItemListPrefixedU8: Codec<List<GameShopItem>> =
    object : Codec<List<GameShopItem>> {
        override fun read(buf: ReadBuffer): List<GameShopItem> {
            val n = U8.read(buf)
            return List(n) { GameShopItemCodec.read(buf) }
        }

        override fun write(buf: WriteBuffer, value: List<GameShopItem>) {
            U8.write(buf, value.size)
            value.forEach { GameShopItemCodec.write(buf, it) }
        }
    }

object GameShopCatalogPacketCodec : PacketCodec<GameShopCatalogPacket>() {
    override fun CodecScope<GameShopCatalogPacket>.body(): GameShopCatalogPacket {
        val points = field(S32LE, GameShopCatalogPacket::points)
        val items = field(GameShopItemListPrefixedU8, GameShopCatalogPacket::items)
        return GameShopCatalogPacket(points, items)
    }
}
