package de.fiereu.openmmo.launcher.client

import de.fiereu.openmmo.launcher.launch.ArchiveOrigin
import java.io.BufferedOutputStream
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path
import java.security.PublicKey
import java.security.Signature
import java.time.Duration
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.delay

class ArchiveClient(
    http: HttpClient = defaultHttpClient(),
    private val archiveOrigin: String = ArchiveOrigin.configured,
    private val archiveRawOrigin: String = ArchiveOrigin.configuredRaw,
    private val trustedKeys: List<PublicKey> = pokemmoFeedPublicKeys(),
) {

  internal val http: HttpClient =
      if (http.followRedirects() != HttpClient.Redirect.NEVER) {
        http
      } else {
        defaultHttpClient()
      }

  companion object {
    fun defaultHttpClient(): HttpClient =
        HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .connectTimeout(Duration.ofSeconds(15))
            .build()
  }

  suspend fun hasDelta(fromRevision: Int, toRevision: Int): Boolean {
    val url = "$archiveOrigin/releases/download/r$fromRevision/$fromRevision-to-$toRevision.xdelta"
    println("[ArchiveClient] Checking delta availability at $url")
    return try {
      val request =
          HttpRequest.newBuilder()
              .uri(URI.create(url))
              .timeout(Duration.ofSeconds(10))
              .method("HEAD", HttpRequest.BodyPublishers.noBody())
              .build()
      val response = http.send(request, HttpResponse.BodyHandlers.discarding())
      val exists = response.statusCode() in 200..399
      println("[ArchiveClient] Delta exists: $exists (HTTP ${response.statusCode()})")
      exists
    } catch (e: Exception) {
      println("[ArchiveClient] Delta check failed: ${e.message}")
      false
    }
  }

  suspend fun downloadDelta(
      fromRevision: Int,
      toRevision: Int,
      targetPath: Path,
      attempts: Int = 5
  ) {
    targetPath.parent?.let(Files::createDirectories)
    val url = "$archiveOrigin/releases/download/r$fromRevision/$fromRevision-to-$toRevision.xdelta"
    println("[ArchiveClient] Downloading delta from $url to $targetPath")

    for (attempt in 1..attempts) {
      try {
        val request =
            HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(5))
                .GET()
                .build()

        val response = http.send(request, HttpResponse.BodyHandlers.ofInputStream())
        if (response.statusCode() == 404) {
          throw IOException(
              "Archive release delta not found for revision $fromRevision -> $toRevision (HTTP 404)")
        }
        if (response.statusCode() !in 200..299) {
          throw IOException("Failed to download delta from $url: HTTP ${response.statusCode()}")
        }

        val tempFile = targetPath.resolveSibling(targetPath.fileName.toString() + ".part")
        try {
          response.body().use { inStream ->
            BufferedOutputStream(Files.newOutputStream(tempFile)).use { outStream ->
              inStream.copyTo(outStream)
            }
          }
          Files.move(tempFile, targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING)
          println("[ArchiveClient] Successfully downloaded delta (${Files.size(targetPath)} bytes)")
          return
        } finally {
          Files.deleteIfExists(tempFile)
        }
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        println("[ArchiveClient] Download attempt $attempt failed: ${e.message}")
        if (attempt == attempts) {
          throw IOException("Gave up downloading delta from $url after $attempts attempts", e)
        }
        delay(minOf(attempt * 1000L, 5000L))
      }
    }
  }

  suspend fun fetchUpdateFeed(
      revision: Int,
      keys: List<PublicKey> = trustedKeys,
      attempts: Int = 3
  ): UpdateFeed {
    val feedUrl = "$archiveRawOrigin/revisions/$revision/update_feed.txt"
    val sigUrl = "$archiveRawOrigin/revisions/$revision/update_feed.sig256"
    println("[ArchiveClient] Fetching update feed for revision $revision from $feedUrl")

    for (attempt in 1..attempts) {
      try {
        val feedRequest =
            HttpRequest.newBuilder()
                .uri(URI.create(feedUrl))
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build()
        val feedResponse = http.send(feedRequest, HttpResponse.BodyHandlers.ofByteArray())

        if (feedResponse.statusCode() !in 200..299) {
          throw IOException(
              "Failed to fetch update feed from $feedUrl: HTTP ${feedResponse.statusCode()}")
        }
        val feedBytes = feedResponse.body()

        if (keys.isNotEmpty()) {
          val sigRequest =
              HttpRequest.newBuilder()
                  .uri(URI.create(sigUrl))
                  .timeout(Duration.ofSeconds(30))
                  .GET()
                  .build()
          val sigResponse = http.send(sigRequest, HttpResponse.BodyHandlers.ofByteArray())
          if (sigResponse.statusCode() in 200..299) {
            val signature = sigResponse.body()
            val verified =
                keys.any { key ->
                  runCatching {
                        Signature.getInstance("SHA256withRSA").run {
                          initVerify(key)
                          update(feedBytes)
                          verify(signature)
                        }
                      }
                      .getOrDefault(false)
                }
            if (!verified) {
              throw IOException("Update feed signature verification failed for revision $revision")
            }
          }
        }

        val parsedFeed = FeedParser.parseUpdate(feedBytes)
        println("[ArchiveClient] Successfully parsed update feed (${parsedFeed.files.size} files)")
        return parsedFeed
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        println("[ArchiveClient] Fetch feed attempt $attempt failed: ${e.message}")
        if (attempt == attempts) {
          throw IOException(
              "Gave up fetching update feed for revision $revision from $feedUrl after $attempts attempts: ${e.message}",
              e)
        }
        delay(minOf(attempt * 500L, 2000L))
      }
    }
    error("Unreachable")
  }
}
