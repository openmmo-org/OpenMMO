package de.fiereu.openmmo.net.login

import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import de.fiereu.openmmo.net.login.packets.JoinGameServerPacket
import de.fiereu.openmmo.net.login.packets.JoinGameServerPacketCodec
import de.fiereu.openmmo.net.login.packets.MfaResponsePacket
import de.fiereu.openmmo.net.login.packets.MfaResponsePacketCodec
import de.fiereu.openmmo.net.login.packets.RequestGameServerListPacket
import de.fiereu.openmmo.net.login.packets.RequestGameServerListPacketCodec
import de.fiereu.openmmo.net.login.packets.ToSConfirmationPacket
import de.fiereu.openmmo.net.login.packets.ToSConfirmationPacketCodec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class LoginCodecRoundtripTest :
    FunSpec({
      test("RequestGameServerListPacket has no payload") {
        RequestGameServerListPacketCodec.encodeToBytes(RequestGameServerListPacket()).size shouldBe
            0
      }

      test("JoinGameServerPacket roundtrips") {
        val pkt = JoinGameServerPacket(gameServerId = 0x07u)
        val bytes = JoinGameServerPacketCodec.encodeToBytes(pkt)
        bytes.size shouldBe 1
        JoinGameServerPacketCodec.decodeBytes(bytes) shouldBe pkt
      }

      test("ToSConfirmationPacket roundtrip") {
        val pkt = ToSConfirmationPacket(confirmationKey = 1)
        ToSConfirmationPacketCodec.decodeBytes(
            ToSConfirmationPacketCodec.encodeToBytes(pkt)) shouldBe pkt
      }

      test("MfaResponsePacket roundtrips a string") {
        val pkt = MfaResponsePacket(mfaCode = "123456")
        MfaResponsePacketCodec.decodeBytes(MfaResponsePacketCodec.encodeToBytes(pkt)) shouldBe pkt
      }
    })
