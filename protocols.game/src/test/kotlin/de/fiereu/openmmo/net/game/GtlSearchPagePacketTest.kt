package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.fixtureBuffer
import de.fiereu.openmmo.net.game.packets.gtl.GtlItemListing
import de.fiereu.openmmo.net.game.packets.gtl.GtlListKind
import de.fiereu.openmmo.net.game.packets.gtl.GtlPokemonListing
import de.fiereu.openmmo.net.game.packets.gtl.GtlSearchPagePacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GtlSearchPagePacketTest :
    FunSpec({
      test("a captured item page is consumed to the last byte") {
        val buf = fixtureBuffer("game/s2c/9b/item_page_32710.bin")
        val page = GtlSearchPagePacketCodec.read(buf)

        page.requestId shouldBe 7.toByte()
        page.listKind shouldBe GtlListKind.ITEM
        page.page shouldBe 0.toShort()
        page.totalMatches shouldBe 73
        page.listings.size shouldBe 10

        val first = page.listings.first() as GtlItemListing
        first.listingId shouldBe 0x1ACFDD4BEA482000L
        first.price shouldBe 1900
        first.quantity shouldBe 2.toShort()
        first.itemId shouldBe 1111.toShort()

        page.quotes!!.map { it.itemId } shouldBe listOf<Short>(1118, 1116, 1115, 1114, 1113, 1111)
        buf.remaining() shouldBe 0
      }

      test("a captured monster page is consumed to the last byte") {
        val buf = fixtureBuffer("game/s2c/9b/monster_page_32710.bin")
        val page = GtlSearchPagePacketCodec.read(buf)

        page.requestId shouldBe 1.toByte()
        page.listKind shouldBe GtlListKind.POKEMON
        page.totalMatches shouldBe 216090
        page.listings.size shouldBe 10

        val first = page.listings.first() as GtlPokemonListing
        first.listingId shouldBe 0x1ACFF6ACDDC82000L
        first.price shouldBe 11000
        first.pokemon!!.dexId shouldBe 246
        first.pokemon!!.ot shouldBe "DraganwMax"
        first.pokemon!!.level shouldBe 54
        // The board repeats the record's own hp as the first stat.
        first.stats shouldBe listOf<Short>(127, 88, 68, 64, 61, 68)
        first.pokemon!!.hp shouldBe 127

        page.quotes shouldBe null
        buf.remaining() shouldBe 0
      }

      test("a page that is not an item board carries no quote strip") {
        val buf = fixtureBuffer("game/s2c/9b/own_listings_page_31914.bin")
        val page = GtlSearchPagePacketCodec.read(buf)

        page.listKind shouldBe GtlListKind.OWN_LISTINGS
        page.totalMatches shouldBe 0
        page.listings shouldBe emptyList()
        page.quotes shouldBe null
        buf.remaining() shouldBe 0
      }
    })
