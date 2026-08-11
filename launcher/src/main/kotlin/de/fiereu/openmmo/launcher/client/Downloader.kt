package de.fiereu.openmmo.launcher.client

import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import java.time.Duration
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

class ChecksumMismatchException(name: String, expected: String, actual: String) :
    Exception("$name hashed to $actual but the feed says $expected")

/**
 * Downloads one feed entry, resuming where it can and refusing bytes that do not match the hash.
 *
 * Never send `Accept-Encoding`. Cloudflare compresses the content root when asked, and
 * `Content-Range` then counts compressed bytes, so a resumed offset lands in the wrong place.
 */
class Downloader(
    private val http: HttpClient,
    private val timeout: Duration = Duration.ofMinutes(10),
    private val stallTimeout: Duration = Duration.ofSeconds(30),
) {

  // A request timeout ends when the headers arrive, so a mirror that then stops sending would block
  // the read forever. Closing the stream from here is what breaks it out.
  private val watchdog =
      Executors.newSingleThreadScheduledExecutor { runnable ->
        Thread(runnable, "openmmo-download-watchdog").apply { isDaemon = true }
      }

  fun fetch(url: String, target: Path, expected: RemoteFile, onProgress: (Long) -> Unit = {}) {
    target.parent?.let(Files::createDirectories)
    // The hash is in the name, so a part file left by an older revision is never resumed into a
    // newer one. Its bytes would pass the length check and only fail on the hash.
    val part = target.resolveSibling("${target.fileName}.${expected.cacheBuster}.part")

    var have = if (Files.exists(part)) Files.size(part) else 0
    if (have > expected.size) {
      Files.deleteIfExists(part)
      have = 0
    }

    if (have < expected.size) {
      have = transfer(url, part, have, expected.size, onProgress)
    }

    if (have != expected.size) {
      Files.deleteIfExists(part)
      error("${expected.name} is $have bytes but the feed says ${expected.size}")
    }

    val actual = sha256(part)
    if (!actual.equals(expected.sha256, ignoreCase = true)) {
      Files.deleteIfExists(part)
      throw ChecksumMismatchException(expected.name, expected.sha256, actual)
    }

    move(part, target)
    if (expected.executable) markExecutable(target)
  }

  private fun markExecutable(target: Path) {
    // Windows has no executable bit and reports failure, which is not a problem there.
    if (!target.toFile().setExecutable(true, false) && Os.current() != Os.WINDOWS) {
      error("Could not mark $target executable")
    }
  }

  private fun transfer(
      url: String,
      part: Path,
      from: Long,
      limit: Long,
      onProgress: (Long) -> Unit,
  ): Long {
    val builder = HttpRequest.newBuilder(URI.create(url)).timeout(timeout).GET()
    if (from > 0) builder.header("Range", "bytes=$from-")
    val response = builder.build().let { http.send(it, HttpResponse.BodyHandlers.ofInputStream()) }

    val status = response.statusCode()
    if (status != 200 && status != 206) {
      response.body().close()
      error("GET $url returned $status")
    }

    // A 206 that starts somewhere other than where we asked would append the wrong bytes, and only
    // the hash would notice.
    if (status == 206 && from > 0) {
      val range = response.headers().firstValue("content-range").orElse("")
      val start = range.substringAfter("bytes ", "").substringBefore('-').toLongOrNull()
      if (start != from) error("GET $url answered range '$range' but $from was asked for")
    }

    // A server ignoring the range answers 200 with the whole file, so the partial is discarded.
    val resuming = status == 206 && from > 0
    val options =
        if (resuming) arrayOf(StandardOpenOption.WRITE, StandardOpenOption.APPEND)
        else
            arrayOf(
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)

    var written = if (resuming) from else 0
    val lastRead = AtomicLong(System.nanoTime())
    val stalled = AtomicBoolean(false)
    val input = response.body()
    val watch =
        watchdog.scheduleWithFixedDelay(
            {
              if (System.nanoTime() - lastRead.get() > stallTimeout.toNanos()) {
                stalled.set(true)
                runCatching { input.close() }
              }
            },
            stallTimeout.toMillis(),
            stallTimeout.toMillis() / 2,
            TimeUnit.MILLISECONDS,
        )

    try {
      input.use {
        Files.newOutputStream(part, *options).use { output ->
          val buffer = ByteArray(1 shl 16)
          while (true) {
            val read = it.read(buffer)
            if (read < 0) break
            lastRead.set(System.nanoTime())
            output.write(buffer, 0, read)
            written += read
            onProgress(written)
            // Stop a mirror that never stops sending from filling the disk.
            if (written > limit) error("$url sent more than the $limit bytes the feed published")
          }
        }
      }
    } catch (e: IOException) {
      if (stalled.get()) throw IOException("$url stalled for more than $stallTimeout", e)
      throw e
    } finally {
      watch.cancel(false)
    }
    return written
  }

  private fun move(source: Path, target: Path) {
    try {
      Files.move(
          source,
          target,
          StandardCopyOption.REPLACE_EXISTING,
          StandardCopyOption.ATOMIC_MOVE,
      )
    } catch (_: AtomicMoveNotSupportedException) {
      Files.move(source, target, StandardCopyOption.REPLACE_EXISTING)
    }
  }
}
