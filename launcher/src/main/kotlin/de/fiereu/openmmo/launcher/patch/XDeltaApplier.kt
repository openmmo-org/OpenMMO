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

    // 1. Prefer native xdelta3 binary if available (sub-second execution)
    val xdeltaBin = findXDeltaBinary()
    if (xdeltaBin != null) {
      try {
        println("[XDeltaApplier] Using native xdelta binary: $xdeltaBin")
        applyWithProcess(xdeltaBin, source, patch, target)
        if (Files.exists(target) && Files.size(target) > 0) {
          println(
              "[XDeltaApplier] Native xdelta completed successfully (${Files.size(target)} bytes)")
          return
        }
      } catch (e: Exception) {
        println(
            "[XDeltaApplier] Native xdelta execution failed, falling back to pure Java decoder: ${e.message}")
      }
    }

    // 2. Pure Java VCDIFF fallback
    println("[XDeltaApplier] Using pure Java VCDiff decoder")
    val dictBytes = Files.readAllBytes(source)
    val patchBytes = Files.readAllBytes(patch)

    FileOutputStream(target.toFile()).use { outStream ->
      BufferedOutputStream(outStream).use { bOut ->
        val decoder = VCDiffDecoderBuilder.builder().buildSimple()
        decoder.decode(dictBytes, patchBytes, bOut)
        bOut.flush()
      }
    }
  }

  private fun applyWithProcess(xdeltaBin: String, source: Path, patch: Path, target: Path) {
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
