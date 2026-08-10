package de.fiereu.openmmo.launcher.client

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.net.http.HttpClient
import java.nio.file.Files
import java.security.MessageDigest
import kotlin.io.path.createTempDirectory

class DownloaderTest :
    FunSpec({
      val payload = ByteArray(50_000) { (it % 251).toByte() }
      val hash =
          MessageDigest.getInstance("SHA-256").digest(payload).joinToString("") {
            "%02x".format(it)
          }
      val entry = RemoteFile("data/big.bin", hash, payload.size.toLong())

      lateinit var server: TestHttpServer
      lateinit var downloader: Downloader

      beforeTest {
        server = TestHttpServer(mapOf("/data/big.bin" to payload))
        server.start()
        downloader = Downloader(HttpClient.newHttpClient())
      }
      afterTest { server.stop() }

      test("downloads and verifies a file") {
        val dir = createTempDirectory("dl")
        val target = dir.resolve("big.bin")

        downloader.fetch("${server.origin}/data/big.bin", target, entry)

        Files.readAllBytes(target) shouldBe payload
        Files.exists(dir.resolve("big.bin.${entry.cacheBuster}.part")) shouldBe false
      }

      test("resumes from a partial file instead of starting over") {
        val dir = createTempDirectory("dl")
        val target = dir.resolve("big.bin")
        Files.write(
            dir.resolve("big.bin.${entry.cacheBuster}.part"), payload.copyOfRange(0, 20_000))

        var firstProgress = 0L
        downloader.fetch("${server.origin}/data/big.bin", target, entry) {
          if (firstProgress == 0L) firstProgress = it
        }

        Files.readAllBytes(target) shouldBe payload
        // Progress continues from what was already on disk rather than restarting at zero.
        (firstProgress > 20_000L) shouldBe true
      }

      test("starts over when the server ignores the range") {
        val dir = createTempDirectory("dl")
        val target = dir.resolve("big.bin")
        Files.write(
            dir.resolve("big.bin.${entry.cacheBuster}.part"), payload.copyOfRange(0, 20_000))
        server.ignoreRange = setOf("/data/big.bin")

        downloader.fetch("${server.origin}/data/big.bin", target, entry)

        Files.readAllBytes(target) shouldBe payload
      }

      test("discards a partial file that is longer than the feed says") {
        val dir = createTempDirectory("dl")
        val target = dir.resolve("big.bin")
        Files.write(dir.resolve("big.bin.${entry.cacheBuster}.part"), payload + ByteArray(100))

        downloader.fetch("${server.origin}/data/big.bin", target, entry)

        Files.readAllBytes(target) shouldBe payload
      }

      test("refuses bytes that do not match the published hash") {
        val dir = createTempDirectory("dl")
        val target = dir.resolve("big.bin")
        val wrong = entry.copy(sha256 = "f".repeat(64))

        shouldThrow<ChecksumMismatchException> {
          downloader.fetch("${server.origin}/data/big.bin", target, wrong)
        }

        Files.exists(target) shouldBe false
        Files.exists(dir.resolve("big.bin.${wrong.cacheBuster}.part")) shouldBe false
      }

      test("creates missing parent directories") {
        val dir = createTempDirectory("dl")
        val target = dir.resolve("data/nested/big.bin")

        downloader.fetch("${server.origin}/data/big.bin", target, entry)

        Files.readAllBytes(target) shouldBe payload
      }
    })
