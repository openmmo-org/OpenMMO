package de.fiereu.openmmo.launcher.patch

import com.davidehrmann.vcdiff.VCDiffDecoderBuilder
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path

object XDeltaApplier {

  fun apply(source: Path, patch: Path, target: Path) {
    target.parent?.let(Files::createDirectories)

    var javaSuccess = false
    try {
      val dictBytes = Files.readAllBytes(source)
      val patchBytes = Files.readAllBytes(patch)

      FileOutputStream(target.toFile()).use { outStream ->
        BufferedOutputStream(outStream).use { bOut ->
          val decoder = VCDiffDecoderBuilder.builder().buildSimple()
          decoder.decode(dictBytes, patchBytes, bOut)
          bOut.flush()
          javaSuccess = true
        }
      }
    } catch (_: Exception) {
      javaSuccess = false
    }

    if (javaSuccess && Files.exists(target) && Files.size(target) > 0) {
      return
    }

    // Fallback to xdelta3 CLI process
    applyWithProcess(source, patch, target)
  }

  private fun applyWithProcess(source: Path, patch: Path, target: Path) {
    val xdeltaBin = findXDeltaBinary() ?: error("xdelta3 binary not found on system")
    val pb =
        ProcessBuilder(
            xdeltaBin,
            "-d",
            "-f",
            "-s",
            source.toAbsolutePath().toString(),
            patch.toAbsolutePath().toString(),
            target.toAbsolutePath().toString(),
        )
    pb.redirectErrorStream(true)
    val process = pb.start()
    val exitCode = process.waitFor()
    if (exitCode != 0) {
      val errorMsg = process.inputStream.bufferedReader().readText()
      error("xdelta3 process failed with exit code $exitCode: $errorMsg")
    }
  }

  private fun findXDeltaBinary(): String? {
    val systemPaths =
        listOf(
            "xdelta3",
            "/usr/bin/xdelta3",
            "/usr/local/bin/xdelta3",
            File(System.getProperty("user.home"), ".local/bin/xdelta3").absolutePath,
        )
    for (bin in systemPaths) {
      runCatching {
        val process = ProcessBuilder(bin, "-V").start()
        if (process.waitFor() == 0) return bin
      }
    }
    return null
  }
}
