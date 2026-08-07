package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.ByteArrayReadBuffer
import de.fiereu.openmmo.common.test.fixtureBuffer
import de.fiereu.openmmo.net.game.packets.SceneObjectStatesPacketCodec
import de.fiereu.openmmo.net.game.packets.gtl.GtlTradeLogRequestPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GtlTradeLogRequestPacketTest :
    FunSpec({
      test("an empty trade log is a single zero byte") {
        val request = ByteArrayReadBuffer(ByteArray(0))
        GtlTradeLogRequestPacketCodec.read(request)
        request.remaining() shouldBe 0

        val reply = fixtureBuffer("game/s2c/5e/empty_trade_log_31914.bin")
        SceneObjectStatesPacketCodec.read(reply).objects shouldBe emptyList()
        reply.remaining() shouldBe 0
      }
    })
