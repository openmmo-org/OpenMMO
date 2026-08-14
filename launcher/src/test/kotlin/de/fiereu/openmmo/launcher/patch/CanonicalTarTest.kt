package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.client.RemoteFile
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Files
import kotlin.io.path.createTempDirectory

class CanonicalTarTest :
    FunSpec({
      test("creates canonical ustar archive and extracts files accurately") {
        val tempDir = createTempDirectory("canonical_tar_test")
        try {
          val sourceDir = tempDir.resolve("source")
          val extractDir = tempDir.resolve("extracted")
          val tarPath = tempDir.resolve("archive.tar")

          Files.createDirectories(sourceDir.resolve("bin"))
          Files.createDirectories(sourceDir.resolve("data"))

          val binFile = sourceDir.resolve("bin/app")
          val dataFile = sourceDir.resolve("data/text.txt")

          Files.writeString(binFile, "binary-content-12345")
          Files.writeString(dataFile, "data-content-67890")

          val files =
              listOf(
                  RemoteFile(
                      name = "data/text.txt",
                      sha256 = "",
                      size = Files.size(dataFile),
                      executable = false),
                  RemoteFile(
                      name = "bin/app", sha256 = "", size = Files.size(binFile), executable = true),
              )

          CanonicalTar.create(sourceDir, files, tarPath)

          // Verify tar file is padded to multiple of 10240 bytes
          (Files.size(tarPath) % 10240) shouldBe 0L

          // Extract tar
          CanonicalTar.extract(tarPath, extractDir)

          Files.exists(extractDir.resolve("bin/app")) shouldBe true
          Files.exists(extractDir.resolve("data/text.txt")) shouldBe true

          Files.readString(extractDir.resolve("bin/app")) shouldBe "binary-content-12345"
          Files.readString(extractDir.resolve("data/text.txt")) shouldBe "data-content-67890"

          extractDir.resolve("bin/app").toFile().canExecute() shouldBe true
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }
    })
