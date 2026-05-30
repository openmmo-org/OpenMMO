package de.fiereu.network.cipher

import de.fiereu.network.handshake.EcKeys
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.netty.buffer.Unpooled

class AesCtrSessionCipherTest :
    FunSpec({
      test("derived ciphers interoperate across roles") {
        val serverEphemeral = EcKeys.generateEphemeralKeyPair()
        val clientEphemeral = EcKeys.generateEphemeralKeyPair()

        val serverCipher =
            AesCtrSessionCipher.derive(
                localPrivate = serverEphemeral.private,
                remotePublic = clientEphemeral.public,
                role = AesCtrSessionCipher.Role.SERVER,
            )
        val clientCipher =
            AesCtrSessionCipher.derive(
                localPrivate = clientEphemeral.private,
                remotePublic = serverEphemeral.public,
                role = AesCtrSessionCipher.Role.CLIENT,
            )

        val plaintext = "the quick brown fox jumps over the lazy dog".toByteArray()
        val srcServerOut = Unpooled.wrappedBuffer(plaintext)
        val encrypted = Unpooled.buffer()
        serverCipher.encrypt(srcServerOut, encrypted)

        val decrypted = Unpooled.buffer()
        clientCipher.decrypt(encrypted, decrypted)
        val read = ByteArray(decrypted.readableBytes())
        decrypted.readBytes(read)
        read shouldBe plaintext
      }

      test("NoOpSessionCipher passes through") {
        val src = Unpooled.wrappedBuffer(byteArrayOf(1, 2, 3))
        val dst = Unpooled.buffer()
        NoOpSessionCipher.encrypt(src, dst)
        val arr = ByteArray(3)
        dst.readBytes(arr)
        arr shouldBe byteArrayOf(1, 2, 3)
      }
    })
