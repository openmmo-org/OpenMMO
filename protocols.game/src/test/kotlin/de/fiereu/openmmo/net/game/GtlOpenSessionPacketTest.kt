package de.fiereu.openmmo.net.game

import de.fiereu.openmmo.common.test.fixtureBuffer
import de.fiereu.openmmo.net.game.packets.CategoryFlagsPacketCodec
import de.fiereu.openmmo.net.game.packets.gtl.GtlOpenSessionPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GtlOpenSessionPacketTest :
    FunSpec({
      test("the first open carries no timestamp") {
        val buf = fixtureBuffer("game/c2s/a5/open_session_first_31914.bin")
        GtlOpenSessionPacketCodec.read(buf).sessionTimestamp shouldBe 0L
        buf.remaining() shouldBe 0
      }

      test("the next open echoes the timestamp the server just sent") {
        val flagBuf = fixtureBuffer("game/s2c/dc/category_flags_31914.bin")
        val flags = CategoryFlagsPacketCodec.read(flagBuf)
        flags.entryKind shouldBe 1
        flags.flagBits.size shouldBe 822
        flagBuf.remaining() shouldBe 0

        val reopen = fixtureBuffer("game/c2s/a5/open_session_reopen_31914.bin")
        GtlOpenSessionPacketCodec.read(reopen).sessionTimestamp shouldBe flags.timestampMillis
        reopen.remaining() shouldBe 0
      }
    })
