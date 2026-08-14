package de.fiereu.openmmo.launcher.patch

import com.davidehrmann.vcdiff.VCDiffEncoderBuilder
import de.fiereu.openmmo.launcher.client.ArchiveClient
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.RemoteFile
import de.fiereu.openmmo.launcher.client.TestHttpServer
import de.fiereu.openmmo.launcher.client.UpdateFeed
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.io.ByteArrayOutputStream
import java.net.http.HttpClient
import java.nio.file.Files
import kotlin.io.path.createTempDirectory

private const val TEST_TARGET_SHA256 =
    "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef"

class ArchivePatcherTest :
    FunSpec({
      test("applies archive delta patch to pristine install and extracts target files") {
        val tempDir = createTempDirectory("archive_patcher_test")
        try {
          val install = ManagedInstall(tempDir.resolve("install"))
          install.create()

          // 1. Write source files
          val sourceRevisionFile = install.resolve("revision.txt")
          Files.writeString(sourceRevisionFile, "32898")

          // 2. Prepare target archive to generate delta patch
          val sourceTar = tempDir.resolve("source.tar")
          val targetTar = tempDir.resolve("target.tar")
          val patchFile = tempDir.resolve("delta.xdelta")

          val targetDir = tempDir.resolve("target_dir")
          Files.createDirectories(targetDir)
          val targetRevisionFile = targetDir.resolve("revision.txt")
          Files.writeString(targetRevisionFile, "32763")

          val sourceFiles =
              listOf(RemoteFile(name = "revision.txt", sha256 = TEST_TARGET_SHA256, size = 5))
          val targetFiles =
              listOf(RemoteFile(name = "revision.txt", sha256 = TEST_TARGET_SHA256, size = 5))

          CanonicalTar.create(install.client, sourceFiles, sourceTar)
          CanonicalTar.create(targetDir, targetFiles, targetTar)

          // 3. Generate patch using xdelta3 if available, or fall back to pure Java VCDiff encoder
          val deltaBytes =
              runCatching {
                    val pb =
                        ProcessBuilder(
                            "xdelta3",
                            "-e",
                            "-f",
                            "-s",
                            sourceTar.toAbsolutePath().toString(),
                            targetTar.toAbsolutePath().toString(),
                            patchFile.toAbsolutePath().toString(),
                        )
                    val exitCode = pb.start().waitFor()
                    if (exitCode == 0 && Files.exists(patchFile)) {
                      Files.readAllBytes(patchFile)
                    } else {
                      null
                    }
                  }
                  .getOrNull()
                  ?: ByteArrayOutputStream().use { outStream ->
                    val encoder =
                        VCDiffEncoderBuilder.builder()
                            .withDictionary(Files.readAllBytes(sourceTar))
                            .buildSimple()
                    encoder.encode(Files.readAllBytes(targetTar), outStream)
                    outStream.toByteArray()
                  }

          val xmlFeed =
              """
              <?xml version="1.0" encoding="UTF-8" standalone="no"?>
              <update_feed min_osx_installer_version="60">
                <file name="revision.txt" sha256="$TEST_TARGET_SHA256" size="5"/>
              </update_feed>
              """
                  .trimIndent()

          val server =
              TestHttpServer(
                  mapOf(
                      "/releases/download/r32898/32898-to-32763.xdelta" to deltaBytes,
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

            val patcher = ArchivePatcher(install, client)
            val currentFeed = UpdateFeed(sourceFiles)
            val targetFeed = patcher.apply(32898, 32763, currentFeed)

            Files.readString(sourceRevisionFile).trim() shouldBe "32763"
            targetFeed.files.size shouldBe 1
            targetFeed.files[0].sha256 shouldBe TEST_TARGET_SHA256
          } finally {
            server.stop()
          }
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }
    })
