package de.fiereu.openmmo.launcher.client

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.PublicKey
import java.security.Signature
import java.time.Duration
import kotlin.coroutines.cancellation.CancellationException

val POKEMMO_MIRRORS =
    listOf(
        "https://dl.pokemmo.com",
        "https://files.pokemmo.com",
        "https://dl.pokemmo.eu",
        "https://files.pokemmo.eu",
        "https://dl.pokemmo.download",
    )

// The other three carry no news feed, so patching one there would find nothing to replace.
val POKEMMO_NEWS_MIRRORS = setOf("https://dl.pokemmo.com", "https://files.pokemmo.com")

const val LIVE_CHANNEL = "live"

private const val SIGNATURE_ALGORITHM = "SHA256withRSA"

class FeedUnavailableException(val failures: List<String>) :
    Exception("No mirror served a usable feed: ${failures.joinToString("; ")}")

// Nothing is parsed until its signature verifies.
class FeedClient(
    private val http: HttpClient,
    private val mirrors: List<String> = POKEMMO_MIRRORS,
    private val keys: List<PublicKey> = pokemmoFeedPublicKeys(),
    private val channel: String = LIVE_CHANNEL,
    private val timeout: Duration = Duration.ofSeconds(45),
) {

  fun load(): Feeds {
    val failures = mutableListOf<String>()
    for (mirror in mirrors) {
      try {
        return loadFrom(mirror)
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        failures += "$mirror: ${e.message}"
      }
    }
    throw FeedUnavailableException(failures)
  }

  private fun loadFrom(mirror: String): Feeds {
    val main = verified(mirror, "main_feed")
    val update = verified(mirror, "update_feed")
    val parsed = FeedParser.parseUpdate(update)
    if (parsed.files.isEmpty()) error("update feed lists no usable files")
    return Feeds(FeedParser.parseMain(main), parsed, mirror)
  }

  private fun verified(mirror: String, feed: String): ByteArray {
    val body = get("$mirror/$channel/current/feeds/$feed.txt")
    val signature = get("$mirror/$channel/current/feeds/$feed.sig256")
    if (!verify(body, signature)) error("$feed failed signature verification")
    return body
  }

  private fun verify(body: ByteArray, signature: ByteArray): Boolean =
      keys.any { key ->
        runCatching {
              Signature.getInstance(SIGNATURE_ALGORITHM).run {
                initVerify(key)
                update(body)
                verify(signature)
              }
            }
            .getOrDefault(false)
      }

  private fun get(url: String): ByteArray {
    val request = HttpRequest.newBuilder(URI.create(url)).timeout(timeout).GET().build()
    val response = http.send(request, HttpResponse.BodyHandlers.ofByteArray())
    if (response.statusCode() != 200) error("GET $url returned ${response.statusCode()}")
    return response.body()
  }
}
