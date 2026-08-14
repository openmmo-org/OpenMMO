package de.fiereu.openmmo.launcher.launch

import de.fiereu.openmmo.launcher.FeedTls
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.POKEMMO_MIRRORS
import de.fiereu.openmmo.launcher.client.POKEMMO_NEWS_MIRRORS
import de.fiereu.openmmo.launcher.patch.BinaryStringPatch
import de.fiereu.openmmo.launcher.patch.CLIENT_TARGET
import de.fiereu.openmmo.launcher.patch.FeedRedirect
import de.fiereu.openmmo.launcher.patch.LoginRedirect
import de.fiereu.openmmo.launcher.patch.Patch
import de.fiereu.openmmo.launcher.patch.PatchManifest
import java.nio.file.Files
import java.util.Properties

enum class FeedFile(val pokemmoPath: String) {
  MAIN("/live/current/feeds/main_feed.txt"),
  SIGNATURE("/live/current/feeds/main_feed.sig256"),
  NEWS("/news_feed.txt"),
}

// The client stores whole urls, so a replacement is budgeted against the whole url, not the host.
val POKEMMO_FEED_URLS: List<Pair<String, FeedFile>> =
    POKEMMO_MIRRORS.flatMap { mirror ->
      FeedFile.entries
          .filter { it != FeedFile.NEWS || mirror in POKEMMO_NEWS_MIRRORS }
          .map { mirror + it.pokemmoPath to it }
    }

/** Fixed, because a separate process serves the feed and the launcher has to know the port. */
const val DEV_FEED_PORT = 20443

val OPENMMO_FEED_PATHS =
    mapOf(
        FeedFile.MAIN to "/main.xml",
        FeedFile.SIGNATURE to "/main.sig256",
        // The url this replaces is 36 characters, too narrow for "news_feed.xml".
        FeedFile.NEWS to "/news.xml",
    )

/** Values the build bakes in, so a release points somewhere different from a development run. */
internal fun property(key: String): String? =
    System.getProperty(key)
        ?: FeedOrigin::class.java.getResourceAsStream("/launcher.properties")?.use {
          Properties().apply { load(it) }.getProperty(key)
        }

object FeedOrigin {

  val configured: String by lazy { property("feed.origin") ?: "https://127.0.0.1:$DEV_FEED_PORT" }

  val isLoopback: Boolean
    get() = configured.contains("127.0.0.1")
}

private const val LOGIN_HOST_SLOT = "loginserver.pokemmo.com"
const val TLS_FEED_TRUST_PUBLIC = "key.tls.feed.spki"

/** Same width as the slot, and parses to 127.0.0.1. A hostname could not be padded to fit. */
@Suppress("kotlin:S1313") const val DEAD_LOGIN_HOST = "[0:0:0:0:0:ffff:7f00:1]"

/**
 * Sends the compiled in login host nowhere.
 *
 * The feed names the real server, so this only matters when the feed fails, and then the client
 * should stop rather than reach PokeMMO's.
 */
fun loginHostPatch(): Patch =
    BinaryStringPatch(CLIENT_TARGET, "LoginHost", LOGIN_HOST_SLOT, DEAD_LOGIN_HOST)

/** Redirects only the locations the selected client revision still carries in its binary. */
fun redirectPatches(manifest: PatchManifest): List<Patch> = buildList {
  if (manifest.feedRedirect == FeedRedirect.BINARY) addAll(feedPatches())
  if (manifest.loginRedirect == LoginRedirect.BINARY) add(loginHostPatch())
}

fun feedTrustValues(install: ManagedInstall): Map<String, String> {
  val store = install.root.resolve(DEV_TRUSTSTORE)
  if (!Files.isRegularFile(store)) return emptyMap()
  val spki = FeedTls.trustAnchor(store).publicKey.encoded
  return mapOf(TLS_FEED_TRUST_PUBLIC to spki.joinToString(" ") { "%02X".format(it) })
}

// Pads with a query, not slashes, because main.xml/// is a 404.
fun padUrl(url: String, width: Int): String {
  require(url.length <= width) { "$url is ${url.length} characters and will not fit $width" }
  val padding = width - url.length
  return if (padding == 0) url else url + "?" + "x".repeat(padding - 1)
}

fun feedPatches(origin: String = FeedOrigin.configured): List<Patch> =
    POKEMMO_FEED_URLS.map { (url, file) ->
      BinaryStringPatch(
          target = CLIENT_TARGET,
          name = "Feed(${file.name}, $url)",
          find = url,
          replace = padUrl(origin + OPENMMO_FEED_PATHS.getValue(file), url.length),
      )
    }
