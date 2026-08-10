package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.assertBytesRoundtrip
import de.fiereu.openmmo.common.test.decodeBytes
import de.fiereu.openmmo.common.test.fixture
import de.fiereu.openmmo.net.game.packets.ExchangeItemRequestPacketCodec
import de.fiereu.openmmo.net.game.packets.ShopCatalogPacket
import de.fiereu.openmmo.net.game.packets.ShopCatalogPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ShopCatalogPacketTest :
    FunSpec({
      test("the viridian mart catalog decodes to its shelves and their prices") {
        val bytes = fixture("game/s2c/23/mart_catalog.bin")

        val packet = ShopCatalogPacketCodec.decodeBytes(bytes)

        packet.kind shouldBe ShopCatalogPacket.OPEN
        packet.shopId shouldBe 103
        packet.npcEntityId shouldBe 0x1AD3B85EBE48E000L
        packet.items.map { it.itemId.toInt() } shouldBe listOf(5004, 5017, 5018, 5022, 5019)
        packet.items.map { it.price } shouldBe listOf(200, 200, 200, 300, 300)
        // Every line carries the same endless shelf marker.
        packet.items.map { it.stock.toInt() }.toSet() shouldBe setOf(0x7FFF)
        ShopCatalogPacketCodec.assertBytesRoundtrip(bytes)
      }

      test("a close carries nothing but its marker") {
        val bytes = fixture("game/s2c/23/shop_closed.bin")

        ShopCatalogPacketCodec.decodeBytes(bytes).kind shouldBe ShopCatalogPacket.CLOSED

        ShopCatalogPacketCodec.assertBytesRoundtrip(bytes)
      }

      test("a buy names the item and how many of it") {
        val bytes = fixture("game/c2s/23/buy_poke_balls.bin")

        val packet = ExchangeItemRequestPacketCodec.decodeBytes(bytes)

        packet.itemEntityRef.toInt() shouldBe 5004
        packet.quantity.toInt() shouldBe 3
        ExchangeItemRequestPacketCodec.assertBytesRoundtrip(bytes)
      }
    })
