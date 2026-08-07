package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.ByteArrayReadBuffer
import de.fiereu.openmmo.net.game.packets.gtl.GtlMarketListingsPacketCodec
import de.fiereu.openmmo.net.game.packets.gtl.GtlMarketListingsRequestPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GtlMarketListingsPacketTest :
    FunSpec({
      test("opening the board asks for every listing with an empty body") {
        val request = ByteArrayReadBuffer(ByteArray(0))
        GtlMarketListingsRequestPacketCodec.read(request)
        request.remaining() shouldBe 0
      }

      test("a captured board page is consumed to the last byte") {
        val buf = fixture("game/s2c/70/board_page_31914.bin")
        val board = GtlMarketListingsPacketCodec.read(buf)

        board.fullReset shouldBe false
        board.applyToBoards shouldBe true
        // The capture cuts the last row short, so it holds 7 rows and not the 8 it declares.
        board.listings.size shouldBe 7

        val first = board.listings.first()
        first.listingId shouldBe 301
        first.speciesId shouldBe 3384.toShort()
        first.value1 shouldBe 2000
        first.ownListing shouldBe true
        first.rowIndex shouldBe -1
        buf.remaining() shouldBe 0
      }
    })
