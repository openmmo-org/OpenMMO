package de.fiereu.openmmo.launcher.client

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.net.http.HttpClient
import java.nio.file.Files
import kotlin.io.path.createTempDirectory

private const val TEST_SHA256 = "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef"

class ArchiveClientTest :
    FunSpec({
      test("hasDelta returns true when release delta exists and false when missing") {
        val server =
            TestHttpServer(
                mapOf(
                    "/releases/download/r32898/32898-to-32763.xdelta" to "EXISTS".toByteArray(),
                ))
        server.start()

        try {
          val client =
              ArchiveClient(
                  http = HttpClient.newHttpClient(),
                  archiveOrigin = server.origin,
                  archiveRawOrigin = server.origin,
              )

          client.hasDelta(32898, 32763) shouldBe true
          client.hasDelta(99999, 32763) shouldBe false
        } finally {
          server.stop()
        }
      }

      test("downloads release delta from archive origin") {
        val deltaBytes = "MOCK_DELTA_BYTES".toByteArray()
        val server =
            TestHttpServer(
                mapOf(
                    "/releases/download/r32898/32898-to-32763.xdelta" to deltaBytes,
                ))
        server.start()

        val tempDir = createTempDirectory("archive_client_test")
        val targetPath = tempDir.resolve("patch.xdelta")

        try {
          val client =
              ArchiveClient(
                  http = HttpClient.newHttpClient(),
                  archiveOrigin = server.origin,
                  archiveRawOrigin = server.origin,
              )

          client.downloadDelta(32898, 32763, targetPath)

          Files.exists(targetPath) shouldBe true
          Files.readAllBytes(targetPath) shouldBe deltaBytes
        } finally {
          server.stop()
          tempDir.toFile().deleteRecursively()
        }
      }

      test("fetches and parses update feed from raw archive") {
        val xmlFeed =
            """
            <?xml version="1.0" encoding="UTF-8" standalone="no"?>
            <update_feed min_osx_installer_version="60">
              <file name="revision.txt" sha256="$TEST_SHA256" size="5"/>
            </update_feed>
            """
                .trimIndent()

        val server =
            TestHttpServer(
                mapOf(
                    "/revisions/32763/update_feed.txt" to xmlFeed.toByteArray(),
                ))
        server.start()

        try {
          val client =
              ArchiveClient(
                  http = HttpClient.newHttpClient(),
                  archiveOrigin = server.origin,
                  archiveRawOrigin = server.origin,
              )

          val feed = client.fetchUpdateFeed(32763)
          feed.files.size shouldBe 1
          feed.files[0].name shouldBe "revision.txt"
          feed.files[0].sha256 shouldBe TEST_SHA256
        } finally {
          server.stop()
        }
      }
    })
