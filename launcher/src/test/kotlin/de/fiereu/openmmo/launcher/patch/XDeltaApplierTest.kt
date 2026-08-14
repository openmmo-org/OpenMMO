package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.client.Arch
import de.fiereu.openmmo.launcher.client.Os
import de.fiereu.openmmo.launcher.client.Platform
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.io.path.createTempDirectory

class XDeltaApplierTest :
    FunSpec({
      test("selectAsset picks the matching release asset for each platform") {
        val assets =
            listOf(
                XDeltaApplier.GitHubAsset(
                    name = "xdelta3-3.2.0-linux-x86_64.tar.gz",
                    browserDownloadUrl = "https://example.com/linux.tar.gz",
                ),
                XDeltaApplier.GitHubAsset(
                    name = "xdelta3-3.2.0-macos-arm64.tar.gz",
                    browserDownloadUrl = "https://example.com/macos-arm64.tar.gz",
                ),
                XDeltaApplier.GitHubAsset(
                    name = "xdelta3-3.2.0-windows-x86_64.zip",
                    browserDownloadUrl = "https://example.com/windows.zip",
                ),
            )

        val linuxAsset = XDeltaApplier.selectAsset(assets, Platform(Os.LINUX, Arch.X64))
        linuxAsset?.name shouldBe "xdelta3-3.2.0-linux-x86_64.tar.gz"

        val macAsset = XDeltaApplier.selectAsset(assets, Platform(Os.MACOS, Arch.ARM64))
        macAsset?.name shouldBe "xdelta3-3.2.0-macos-arm64.tar.gz"

        val winAsset = XDeltaApplier.selectAsset(assets, Platform(Os.WINDOWS, Arch.X64))
        winAsset?.name shouldBe "xdelta3-3.2.0-windows-x86_64.zip"
      }

      test("finds xdelta binary on system or in tools directory") {
        val tempDir = createTempDirectory("xdelta_find_test")
        try {
          val fakeTools = tempDir.resolve("tools")
          Files.createDirectories(fakeTools)

          val found = XDeltaApplier.findXDeltaBinary(fakeTools)
          if (found != null) {
            found shouldNotBe ""
          }
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }

      test("downloads and extracts xdelta3 from dynamic latest release zip") {
        val tempDir = createTempDirectory("xdelta_zip_test")
        try {
          val toolsDir = tempDir.resolve("tools")
          val fakeExeContent = "#!/bin/sh\necho xdelta3-fake\nexit 0\n"

          val zipBytes =
              ByteArrayOutputStream().use { bOut ->
                ZipOutputStream(bOut).use { zOut ->
                  zOut.putNextEntry(ZipEntry("xdelta3-windows/xdelta3.exe"))
                  zOut.write(fakeExeContent.toByteArray())
                  zOut.closeEntry()
                }
                bOut.toByteArray()
              }

          val archiveFile = tempDir.resolve("archive.zip")
          Files.write(archiveFile, zipBytes)

          val extractedExe = toolsDir.resolve("xdelta3.exe")
          Files.createDirectories(toolsDir)

          java.util.zip.ZipInputStream(Files.newInputStream(archiveFile)).use { zip ->
            var entry = zip.nextEntry
            while (entry != null) {
              if (entry.name.endsWith("xdelta3.exe")) {
                Files.copy(zip, extractedExe)
                break
              }
              entry = zip.nextEntry
            }
          }

          Files.exists(extractedExe) shouldBe true
          Files.readString(extractedExe) shouldBe fakeExeContent
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }
    })
