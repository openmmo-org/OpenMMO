package de.fiereu.openmmo.net.login

import de.fiereu.openmmo.common.test.assertBytesRoundtrip
import de.fiereu.openmmo.common.test.decodeBytes
import de.fiereu.openmmo.common.test.fixture
import de.fiereu.openmmo.net.login.packets.ExistingSessionPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ExistingSessionPacketTest :
    FunSpec({
      val capture = "login/s2c/26/existing_session_32710.bin"

      test("round-trips the captured payload byte for byte") {
        ExistingSessionPacketCodec.assertBytesRoundtrip(fixture(capture))
      }

      test("decodes the session and the server hosting it") {
        val packet = ExistingSessionPacketCodec.decodeBytes(fixture(capture))

        packet.sessionKey.size shouldBe 16
        packet.gameServerId shouldBe 1u.toUByte()
        packet.serverName shouldBe "Live"
        packet.localAddress.toString() shouldBe "127.0.0.1"
        packet.localHostname shouldBe "localhost"
        packet.port shouldBe 7777u.toUShort()
      }

      test("decodes every node, whose ids are assigned rather than sequential") {
        val packet = ExistingSessionPacketCodec.decodeBytes(fixture(capture))

        packet.nodes.size shouldBe 5
        packet.nodes.map { it.id.toInt() } shouldBe listOf(1, 2, 5, 9, 15)
        packet.nodes.forEach { it.port shouldBe 7777u.toUShort() }
      }
    })
