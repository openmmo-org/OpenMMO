package de.fiereu.openmmo.launcher.launch

import de.fiereu.openmmo.launcher.client.TestHttpServer
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.net.http.HttpClient
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.Signature
import kotlin.io.encoding.Base64

class ServerKeysTest :
    FunSpec({
      val feedPair =
          KeyPairGenerator.getInstance("RSA").apply { initialize(3072) }.generateKeyPair()
      val serverPair = KeyPairGenerator.getInstance("EC").generateKeyPair()

      val pem =
          ("-----BEGIN PUBLIC KEY-----\n" +
                  Base64.Default.encode(serverPair.public.encoded).chunked(64).joinToString("\n") +
                  "\n-----END PUBLIC KEY-----\n")
              .toByteArray()

      fun signedWith(key: PrivateKey, body: ByteArray) =
          Signature.getInstance("SHA256withRSA").run {
            initSign(key)
            update(body)
            sign()
          }

      fun serve(routes: Map<String, ByteArray>, block: (String) -> Unit) {
        val server = TestHttpServer(routes).also { it.start() }
        try {
          block(server.origin)
        } finally {
          server.stop()
        }
      }

      fun keys(origin: String) =
          ServerKeys(HttpClient.newHttpClient(), origin, feedPair.public).gamePublicKeyBase64()

      test("returns the key when the feed key signed it") {
        serve(
            mapOf(
                "/game.public.pem" to pem,
                "/game.public.pem.sig256" to signedWith(feedPair.private, pem))) { origin ->
              keys(origin) shouldBe Base64.Default.encode(serverPair.public.encoded)
            }
      }

      test("refuses a key signed by anything other than the feed key") {
        // Whoever serves the origin must not be able to choose the server the client trusts.
        val impostor =
            KeyPairGenerator.getInstance("RSA").apply { initialize(3072) }.generateKeyPair()

        serve(
            mapOf(
                "/game.public.pem" to pem,
                "/game.public.pem.sig256" to signedWith(impostor.private, pem))) { origin ->
              shouldThrow<ServerKeyException> { keys(origin) }.message shouldContain
                  "not signed by the feed key"
            }
      }

      test("refuses a key whose bytes changed after signing") {
        val tampered = pem.copyOf().also { it[it.size - 20] = 'A'.code.toByte() }

        serve(
            mapOf(
                "/game.public.pem" to tampered,
                "/game.public.pem.sig256" to signedWith(feedPair.private, pem))) { origin ->
              shouldThrow<ServerKeyException> { keys(origin) }
            }
      }

      test("refuses when the origin serves no signature at all") {
        serve(mapOf("/game.public.pem" to pem)) { origin ->
          shouldThrow<ServerKeyException> { keys(origin) }.message shouldContain "404"
        }
      }
    })
