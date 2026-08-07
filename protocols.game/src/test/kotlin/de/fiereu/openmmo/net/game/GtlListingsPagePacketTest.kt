package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.net.game.packets.gtl.GtlListKind
import de.fiereu.openmmo.net.game.packets.gtl.GtlListingsPagePacketCodec
import de.fiereu.openmmo.net.game.packets.gtl.GtlListingsPageRequestPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GtlListingsPagePacketTest :
    FunSpec({
      test("a captured price list request round trips") {
        val buf = fixture("game/c2s/e4/price_list_request_31914.bin")
        val request = GtlListingsPageRequestPacketCodec.read(buf)

        request.requestId shouldBe 3.toByte()
        request.listKind shouldBe GtlListKind.ITEM
        request.categoryId shouldBe 0.toByte()
        request.page shouldBe 2.toShort()
        buf.remaining() shouldBe 0
      }

      test("a captured price list page is consumed to the last byte") {
        val buf = fixture("game/s2c/fe/price_history_32710.bin")
        val page = GtlListingsPagePacketCodec.read(buf)

        page.requestId shouldBe 1.toByte()
        page.listKind shouldBe GtlListKind.ITEM
        page.page shouldBe 0.toShort()
        page.totalCount shouldBe 1184
        page.listings.size shouldBe 10
        page.listings.first().typeId shouldBe 5072.toShort()
        page.listings.first().price shouldBe 1773
        page.listings.first().entries.size shouldBe 1
        buf.remaining() shouldBe 0
      }

      test("a later price list page reports which page it answers") {
        val buf = fixture("game/s2c/fe/price_history_page2_31914.bin")
        val page = GtlListingsPagePacketCodec.read(buf)

        page.requestId shouldBe 3.toByte()
        page.page shouldBe 2.toShort()
        page.listings.size shouldBe 10
        buf.remaining() shouldBe 0
      }
    })
