package de.fiereu.openmmo.launcher.launch

import java.io.StringReader
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.PublicKey
import java.security.Signature
import java.time.Duration
import kotlin.io.encoding.Base64
import org.bouncycastle.util.io.pem.PemReader

const val GAME_KEY_FILE = "game.public.pem"

const val SIGNATURE_SUFFIX = ".sig256"

class ServerKeyException(message: String) : Exception(message)

/**
 * Reads the server key the client is patched to trust from the feed origin.
 *
 * That key decides which server the client will talk to, so serving it over TLS alone would let
 * whoever controls the origin choose. The feed key is the one thing the build pins, so the key has
 * to carry a signature made with it, the same way the feed itself does.
 */
class ServerKeys(
    private val http: HttpClient,
    private val origin: String,
    private val feedKey: PublicKey,
    private val timeout: Duration = Duration.ofSeconds(30),
) {

  fun gamePublicKeyBase64(): String {
    val pem = get(GAME_KEY_FILE)
    if (!verify(pem, get(GAME_KEY_FILE + SIGNATURE_SUFFIX))) {
      throw ServerKeyException("$GAME_KEY_FILE from $origin is not signed by the feed key")
    }
    val content =
        PemReader(StringReader(pem.decodeToString())).readPemObject()?.content
            ?: throw ServerKeyException("$GAME_KEY_FILE from $origin is not a PEM key")
    return Base64.Default.encode(content)
  }

  private fun verify(body: ByteArray, signature: ByteArray): Boolean =
      runCatching {
            Signature.getInstance("SHA256withRSA").run {
              initVerify(feedKey)
              update(body)
              verify(signature)
            }
          }
          .getOrDefault(false)

  private fun get(name: String): ByteArray {
    val url = "${origin.trimEnd('/')}/$name"
    val request = HttpRequest.newBuilder(URI.create(url)).timeout(timeout).GET().build()
    val response = http.send(request, HttpResponse.BodyHandlers.ofByteArray())
    if (response.statusCode() != 200) {
      throw ServerKeyException("GET $url returned ${response.statusCode()}")
    }
    return response.body()
  }
}
