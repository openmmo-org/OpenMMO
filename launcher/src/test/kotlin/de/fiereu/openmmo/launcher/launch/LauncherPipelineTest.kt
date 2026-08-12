package de.fiereu.openmmo.launcher.launch

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Files
import kotlin.io.path.createTempDirectory

class LauncherPipelineTest :
    FunSpec({
      test("highestSupportedRevisionIn finds the highest manifest in directory") {
        val tempDir = createTempDirectory("manifest_test")
        try {
          Files.createFile(tempDir.resolve("manifest-32763.toml"))
          Files.createFile(tempDir.resolve("manifest-32700.toml"))
          Files.createFile(tempDir.resolve("other.txt"))

          val resolver = LauncherPipeline.highestSupportedRevisionIn(tempDir)
          resolver() shouldBe 32763
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }

      test("highestSupportedRevisionIn returns null when no manifests exist") {
        val tempDir = createTempDirectory("manifest_test_empty")
        try {
          val resolver = LauncherPipeline.highestSupportedRevisionIn(tempDir)
          resolver() shouldBe null
        } finally {
          tempDir.toFile().deleteRecursively()
        }
      }
    })
