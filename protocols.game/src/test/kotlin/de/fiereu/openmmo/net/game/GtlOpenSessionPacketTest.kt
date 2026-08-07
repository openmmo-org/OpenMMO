package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.ByteArrayReadBuffer
import de.fiereu.openmmo.common.utils.hexToBytes
import de.fiereu.openmmo.net.game.packets.CategoryFlagsPacketCodec
import de.fiereu.openmmo.net.game.packets.gtl.GtlOpenSessionPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class GtlOpenSessionPacketTest :
    FunSpec({
      test("the first open carries no timestamp") {
        val buf = fixture("game/c2s/a5/open_session_first_31914.bin")
        GtlOpenSessionPacketCodec.read(buf).sessionTimestamp shouldBe 0L
        buf.remaining() shouldBe 0
      }

      test("the next open echoes the timestamp the server just sent") {
        // The flag bytes are opaque, so only the header comes from the capture.
        val flagBytes = ("01" + "85C2F6559E010000" + "3603" + "00".repeat(822)).hexToBytes()
        val flagBuf = ByteArrayReadBuffer(flagBytes)
        val flags = CategoryFlagsPacketCodec.read(flagBuf)
        flags.entryKind shouldBe 1
        flags.flagBits.size shouldBe 822
        flagBuf.remaining() shouldBe 0

        val reopen = fixture("game/c2s/a5/open_session_reopen_31914.bin")
        GtlOpenSessionPacketCodec.read(reopen).sessionTimestamp shouldBe flags.timestampMillis
        reopen.remaining() shouldBe 0
      }
    })
