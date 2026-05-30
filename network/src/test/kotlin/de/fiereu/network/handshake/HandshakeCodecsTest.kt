package de.fiereu.network.handshake

import de.fiereu.bytecodec.test.assertValueRoundtrip
import de.fiereu.bytecodec.test.decodeBytes
import de.fiereu.bytecodec.test.encodeToBytes
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.security.interfaces.ECPublicKey

class HandshakeCodecsTest :
    FunSpec({
      test("ClientHello roundtrip recovers timestamp despite XOR obfuscation") {
        val ts = 1764115200L
        val bytes = ClientHelloCodec.encodeToBytes(ClientHelloPacket(ts))
        bytes.size shouldBe 16
        ClientHelloCodec.decodeBytes(bytes).timestamp shouldBe ts
      }

      test("ServerHello roundtrips pubkey, signature, checksum size") {
        val keyPair = EcKeys.generateEphemeralKeyPair()
        val pkt =
            ServerHelloPacket(
                ephemeralPublic = keyPair.public as ECPublicKey,
                signature = ByteArray(64) { it.toByte() },
                checksumSize = 16,
            )
        val bytes = ServerHelloCodec.encodeToBytes(pkt)
        val decoded = ServerHelloCodec.decodeBytes(bytes)
        decoded.ephemeralPublic shouldBe pkt.ephemeralPublic
        decoded.signature shouldBe pkt.signature
        decoded.checksumSize shouldBe 16
      }

      test("ClientReady roundtrips public key") {
        val keyPair = EcKeys.generateEphemeralKeyPair()
        val pkt = ClientReadyPacket(ephemeralPublic = keyPair.public as ECPublicKey)
        ClientReadyCodec.assertValueRoundtrip(pkt)
      }
    })
