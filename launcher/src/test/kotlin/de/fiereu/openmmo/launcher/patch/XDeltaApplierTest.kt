package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.client.Arch
import de.fiereu.openmmo.launcher.client.ArchiveClient
import de.fiereu.openmmo.launcher.client.Os
import de.fiereu.openmmo.launcher.client.Platform
import de.fiereu.openmmo.launcher.client.TestHttpServer
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.util.zip.GZIPOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.io.path.createTempDirectory

private fun calculateSha256(bytes: ByteArray): String {
  val digest = MessageDigest.getInstance("SHA-256").digest(bytes)
  return digest.joinToString("") { "%02x".format(it) }
}

private fun createMockZipArchive(entryName: String, content: ByteArray): ByteArray =
    ByteArrayOutputStream().use { bOut ->
      ZipOutputStream(bOut).use { zOut ->
        zOut.putNextEntry(ZipEntry(entryName))
        zOut.write(content)
        zOut.closeEntry()
      }
      bOut.toByteArray()
    }

private fun createMockTarGzArchive(entryName: String, content: ByteArray): ByteArray {
  val bOut = ByteArrayOutputStream()
  GZIPOutputStream(bOut).use { gzOut ->
    val header = ByteArray(512)
    val nameBytes = entryName.toByteArray(StandardCharsets.UTF_8)
    System.arraycopy(nameBytes, 0, header, 0, minOf(nameBytes.size, 100))

    val modeStr = "0000755\u0000"
    System.arraycopy(modeStr.toByteArray(StandardCharsets.US_ASCII), 0, header, 100, modeStr.length)

    val sizeStr = String.format("%011o\u0000", content.size)
    System.arraycopy(sizeStr.toByteArray(StandardCharsets.US_ASCII), 0, header, 124, sizeStr.length)

    for (i in 148 until 156) header[i] = ' '.code.toByte()
    var sum = 0L
    for (b in header) sum += (b.toInt() and 0xFF)
    val chkStr = String.format("%06o\u0000 ", sum)
    System.arraycopy(chkStr.toByteArray(StandardCharsets.US_ASCII), 0, header, 148, chkStr.length)

    gzOut.write(header)
    gzOut.write(content)
    val pad = ((512 - (content.size % 512)) % 512)
    if (pad > 0) gzOut.write(ByteArray(pad))
    gzOut.write(ByteArray(1024))
  }
  return bOut.toByteArray()
}

class XDeltaApplierTest :
    FunSpec({
      test("finds xdelta binary in tools directory when present") {
        val tempDir = createTempDirectory("xdelta_find_test")
        try {
          val fakeTools = tempDir.resolve("tools")
          Files.createDirectories(fakeTools)
          val fakeExe = fakeTools.resolve("xdelta3")
          Files.writeString(fakeExe, "#!/bin/sh\nexit 0\n")
          fakeExe.toFile().setExecutable(true, false)

          val found =
              XDeltaApplier.findXDeltaBinary(
                  fakeTools,
                  Platform(Os.LINUX, Arch.X64),
              )
          found shouldBe fakeExe.toAbsolutePath().toString()
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }

      test("downloads and extracts xdelta3 from zip archive with verified SHA-256") {
        val tempDir = createTempDirectory("xdelta_zip_test")
        try {
          val toolsDir = tempDir.resolve("tools")
          val fakeBinary = "#!/bin/sh\nexit 0\n".toByteArray(StandardCharsets.UTF_8)
          val exeSha256 = calculateSha256(fakeBinary)

          val zipBytes = createMockZipArchive("xdelta3-windows/xdelta3.exe", fakeBinary)
          val archiveSha256 = calculateSha256(zipBytes)

          val server =
              TestHttpServer(
                  mapOf(
                      "/releases/download/v3.2.0/xdelta3-3.2.0-windows-x86_64.zip" to zipBytes,
                  ))
          server.start()

          try {
            val client = ArchiveClient.defaultHttpClient()
            val override =
                XDeltaApplier.PinnedXDelta(
                    fileName = "xdelta3-3.2.0-windows-x86_64.zip",
                    archiveSha256 = archiveSha256,
                    executableName = "xdelta3.exe",
                    executableSha256 = exeSha256,
                    downloadUrl =
                        "${server.origin}/releases/download/v3.2.0/xdelta3-3.2.0-windows-x86_64.zip",
                )

            val downloadedPath =
                XDeltaApplier.downloadXDeltaBinary(
                    toolsDir = toolsDir,
                    platform = Platform(Os.WINDOWS, Arch.X64),
                    http = client,
                    pinnedOverride = override,
                )

            downloadedPath shouldNotBe null
            val exePath = Path.of(downloadedPath!!)
            Files.exists(exePath) shouldBe true
            XDeltaApplier.sha256(exePath) shouldBe exeSha256
          } finally {
            server.stop()
          }
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }

      test("downloads and extracts xdelta3 from tar.gz archive with verified SHA-256") {
        val tempDir = createTempDirectory("xdelta_targz_test")
        try {
          val toolsDir = tempDir.resolve("tools")
          val fakeBinary = "#!/bin/sh\nexit 0\n".toByteArray(StandardCharsets.UTF_8)
          val exeSha256 = calculateSha256(fakeBinary)

          val tarGzBytes = createMockTarGzArchive("xdelta3-linux/xdelta3", fakeBinary)
          val archiveSha256 = calculateSha256(tarGzBytes)

          val server =
              TestHttpServer(
                  mapOf(
                      "/releases/download/v3.2.0/xdelta3-3.2.0-linux-x86_64.tar.gz" to tarGzBytes,
                  ))
          server.start()

          try {
            val client = ArchiveClient.defaultHttpClient()
            val override =
                XDeltaApplier.PinnedXDelta(
                    fileName = "xdelta3-3.2.0-linux-x86_64.tar.gz",
                    archiveSha256 = archiveSha256,
                    executableName = "xdelta3",
                    executableSha256 = exeSha256,
                    downloadUrl =
                        "${server.origin}/releases/download/v3.2.0/xdelta3-3.2.0-linux-x86_64.tar.gz",
                )

            val downloadedPath =
                XDeltaApplier.downloadXDeltaBinary(
                    toolsDir = toolsDir,
                    platform = Platform(Os.LINUX, Arch.X64),
                    http = client,
                    pinnedOverride = override,
                )

            downloadedPath shouldNotBe null
            val exePath = Path.of(downloadedPath!!)
            Files.exists(exePath) shouldBe true
            XDeltaApplier.sha256(exePath) shouldBe exeSha256
            Files.isExecutable(exePath) shouldBe true
          } finally {
            server.stop()
          }
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }

      test("rejects downloaded archive when archive SHA-256 does not match pinned hash") {
        val tempDir = createTempDirectory("xdelta_corrupt_archive_test")
        try {
          val toolsDir = tempDir.resolve("tools")
          val fakeBinary = "#!/bin/sh\nexit 0\n".toByteArray(StandardCharsets.UTF_8)
          val zipBytes = createMockZipArchive("xdelta3-windows/xdelta3.exe", fakeBinary)

          val server = TestHttpServer(mapOf("/releases/download/v3.2.0/archive.zip" to zipBytes))
          server.start()

          try {
            val client = ArchiveClient.defaultHttpClient()
            val override =
                XDeltaApplier.PinnedXDelta(
                    fileName = "archive.zip",
                    archiveSha256 =
                        "0000000000000000000000000000000000000000000000000000000000000000",
                    executableName = "xdelta3.exe",
                    executableSha256 = calculateSha256(fakeBinary),
                    downloadUrl = "${server.origin}/releases/download/v3.2.0/archive.zip",
                )

            val downloaded =
                XDeltaApplier.downloadXDeltaBinary(
                    toolsDir = toolsDir,
                    platform = Platform(Os.WINDOWS, Arch.X64),
                    http = client,
                    pinnedOverride = override,
                )

            downloaded shouldBe null
            Files.exists(toolsDir.resolve("xdelta3.exe")) shouldBe false
          } finally {
            server.stop()
          }
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }

      test("rejects downloaded binary when extracted executable SHA-256 does not match") {
        val tempDir = createTempDirectory("xdelta_corrupt_binary_test")
        try {
          val toolsDir = tempDir.resolve("tools")
          val fakeBinary = "#!/bin/sh\nexit 0\n".toByteArray(StandardCharsets.UTF_8)
          val zipBytes = createMockZipArchive("xdelta3-windows/xdelta3.exe", fakeBinary)
          val actualArchiveSha = calculateSha256(zipBytes)

          val server = TestHttpServer(mapOf("/releases/download/v3.2.0/archive.zip" to zipBytes))
          server.start()

          try {
            val client = ArchiveClient.defaultHttpClient()
            val override =
                XDeltaApplier.PinnedXDelta(
                    fileName = "archive.zip",
                    archiveSha256 = actualArchiveSha,
                    executableName = "xdelta3.exe",
                    executableSha256 =
                        "0000000000000000000000000000000000000000000000000000000000000000",
                    downloadUrl = "${server.origin}/releases/download/v3.2.0/archive.zip",
                )

            val downloaded =
                XDeltaApplier.downloadXDeltaBinary(
                    toolsDir = toolsDir,
                    platform = Platform(Os.WINDOWS, Arch.X64),
                    http = client,
                    pinnedOverride = override,
                )

            downloaded shouldBe null
            Files.exists(toolsDir.resolve("xdelta3.exe")) shouldBe false
          } finally {
            server.stop()
          }
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }

      test("rejects unsupported platform without attempting download") {
        val tempDir = createTempDirectory("xdelta_unsupported_test")
        try {
          val toolsDir = tempDir.resolve("tools")
          val client = ArchiveClient.defaultHttpClient()

          // Linux ARM64 is not currently provided upstream
          val result =
              XDeltaApplier.downloadXDeltaBinary(
                  toolsDir = toolsDir,
                  platform = Platform(Os.LINUX, Arch.ARM64),
                  http = client,
              )
          result shouldBe null

          // macOS x86_64 is not provided in v3.2.0 release
          val macX64Result =
              XDeltaApplier.downloadXDeltaBinary(
                  toolsDir = toolsDir,
                  platform = Platform(Os.MACOS, Arch.X64),
                  http = client,
              )
          macX64Result shouldBe null
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }
    })
