package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.client.Arch
import de.fiereu.openmmo.launcher.client.ArchiveClient
import de.fiereu.openmmo.launcher.client.Os
import de.fiereu.openmmo.launcher.client.Platform
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.EOFException
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.security.MessageDigest
import java.time.Duration
import java.util.zip.GZIPInputStream
import java.util.zip.ZipInputStream

object XDeltaApplier {

  data class PinnedXDelta(
      val fileName: String,
      val archiveSha256: String,
      val executableName: String,
      val executableSha256: String,
      val downloadUrl: String,
  )

  val PINNED_DISTRIBUTIONS: Map<Platform, PinnedXDelta> =
      mapOf(
          Platform(Os.LINUX, Arch.X64) to
              PinnedXDelta(
                  fileName = "xdelta3-3.2.0-linux-x86_64.tar.gz",
                  archiveSha256 =
                      "480295c7a41fea6503659f19ddc61676c0df4834e2292846ba97de30c68c2397",
                  executableName = "xdelta3",
                  executableSha256 =
                      "0d38d86de5ab6bbc1adae531331d64585b5a09ce3604a5f090c27f71b6a64b23",
                  downloadUrl =
                      "https://github.com/jmacd/xdelta/releases/download/v3.2.0/xdelta3-3.2.0-linux-x86_64.tar.gz",
              ),
          Platform(Os.MACOS, Arch.ARM64) to
              PinnedXDelta(
                  fileName = "xdelta3-3.2.0-macos-arm64.tar.gz",
                  archiveSha256 =
                      "16221e74708157a7051614323445577219557a04109622abb583d32a8f86785f",
                  executableName = "xdelta3",
                  executableSha256 =
                      "244a2643774155ab7dc563258a203674feafbbddc7f1f4a4f4486b00e78c1941",
                  downloadUrl =
                      "https://github.com/jmacd/xdelta/releases/download/v3.2.0/xdelta3-3.2.0-macos-arm64.tar.gz",
              ),
          Platform(Os.WINDOWS, Arch.X64) to
              PinnedXDelta(
                  fileName = "xdelta3-3.2.0-windows-x86_64.zip",
                  archiveSha256 =
                      "af8ef036cb077a48df080c9a8ac1be4a6e7511c32d11f8bec89b6803a9e52576",
                  executableName = "xdelta3.exe",
                  executableSha256 =
                      "53d90226615f217d3380c39892833311b4e24acd863e1ca01f14b5e772e2e6d0",
                  downloadUrl =
                      "https://github.com/jmacd/xdelta/releases/download/v3.2.0/xdelta3-3.2.0-windows-x86_64.zip",
              ),
      )

  fun apply(
      source: Path,
      patch: Path,
      target: Path,
      toolsDir: Path? = null,
      platform: Platform = Platform.current(),
      http: HttpClient = ArchiveClient.defaultHttpClient(),
  ) {
    target.parent?.let(Files::createDirectories)

    val xdeltaBin = ensureXDeltaBinary(toolsDir, platform, http)
    applyWithProcess(xdeltaBin, source, patch, target)
  }

  fun ensureXDeltaBinary(
      toolsDir: Path?,
      platform: Platform = Platform.current(),
      http: HttpClient = ArchiveClient.defaultHttpClient(),
  ): String {
    // 1. Check existing on system or in tools directory
    findXDeltaBinary(toolsDir, platform)?.let {
      return it
    }

    // 2. Download pinned release
    if (toolsDir != null) {
      val downloaded = downloadXDeltaBinary(toolsDir, platform, http)
      if (downloaded != null) return downloaded
    }

    if (platform !in PINNED_DISTRIBUTIONS) {
      error(
          "Automatic xdelta3 download is not supported for $platform. Please install xdelta3 natively on your system.")
    } else {
      error(
          "No usable xdelta3 binary found on system and could not download pinned binary for $platform.")
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

  fun findXDeltaBinary(toolsDir: Path?, platform: Platform = Platform.current()): String? {
    val exeName = if (platform.os == Os.WINDOWS) "xdelta3.exe" else "xdelta3"
    val searchPaths = mutableListOf<String>()

    if (toolsDir != null) {
      searchPaths.add(toolsDir.resolve(exeName).toAbsolutePath().toString())
    }

    searchPaths.addAll(
        listOf(
            "xdelta3",
            "/usr/bin/xdelta3",
            "/usr/local/bin/xdelta3",
            File(System.getProperty("user.home"), ".local/bin/xdelta3").absolutePath,
        ))

    for (bin in searchPaths) {
      if (testBinary(bin)) {
        return bin
      }
    }
    return null
  }

  fun testBinary(binPath: String): Boolean =
      runCatching {
            val process = ProcessBuilder(binPath, "-V").start()
            process.waitFor() == 0
          }
          .getOrDefault(false)

  fun sha256(path: Path): String {
    val digest = MessageDigest.getInstance("SHA-256")
    Files.newInputStream(path).use { input ->
      val buffer = ByteArray(8192)
      var read: Int
      while (input.read(buffer).also { read = it } != -1) {
        digest.update(buffer, 0, read)
      }
    }
    return digest.digest().joinToString("") { "%02x".format(it) }
  }

  fun downloadXDeltaBinary(
      toolsDir: Path,
      platform: Platform = Platform.current(),
      http: HttpClient = ArchiveClient.defaultHttpClient(),
      pinnedOverride: PinnedXDelta? = null,
  ): String? {
    val pinned =
        pinnedOverride
            ?: PINNED_DISTRIBUTIONS[platform]
            ?: run {
              System.err.println(
                  "[XDeltaApplier] Unsupported platform for automatic xdelta3 download: $platform. Please install xdelta3 natively.")
              return null
            }

    Files.createDirectories(toolsDir)
    val targetExe = toolsDir.resolve(pinned.executableName)

    val canExecute = (Platform.current().os == platform.os)
    if (Files.isRegularFile(targetExe) && (!canExecute || testBinary(targetExe.toString()))) {
      return targetExe.toString()
    }

    val redirectHttp =
        if (http.followRedirects() != HttpClient.Redirect.NEVER) {
          http
        } else {
          ArchiveClient.defaultHttpClient()
        }

    println(
        "[XDeltaApplier] Downloading pinned xdelta3 (${pinned.fileName}) from ${pinned.downloadUrl} to $toolsDir")

    val tempArchive = Files.createTempFile(toolsDir, "xdelta3-dl", ".tmp")
    try {
      val downloadRequest =
          HttpRequest.newBuilder()
              .uri(URI.create(pinned.downloadUrl))
              .header("User-Agent", "OpenMMO-Launcher")
              .timeout(Duration.ofMinutes(2))
              .GET()
              .build()
      val downloadResponse =
          redirectHttp.send(downloadRequest, HttpResponse.BodyHandlers.ofInputStream())
      if (downloadResponse.statusCode() !in 200..299) {
        throw IOException(
            "Failed to download xdelta3 from ${pinned.downloadUrl}: HTTP ${downloadResponse.statusCode()}")
      }

      downloadResponse.body().use { inStream ->
        BufferedOutputStream(Files.newOutputStream(tempArchive)).use { outStream ->
          inStream.copyTo(outStream)
        }
      }

      // 1. Verify archive SHA-256
      val actualArchiveSha256 = sha256(tempArchive)
      if (!actualArchiveSha256.equals(pinned.archiveSha256, ignoreCase = true)) {
        throw SecurityException(
            "SHA-256 mismatch for downloaded xdelta archive ${pinned.fileName}: expected ${pinned.archiveSha256}, got $actualArchiveSha256")
      }

      val tempExe = toolsDir.resolve("${pinned.executableName}.part")
      try {
        if (pinned.fileName.endsWith(".zip", ignoreCase = true)) {
          extractFromZip(tempArchive, pinned.executableName, tempExe)
        } else {
          extractFromTarGz(tempArchive, pinned.executableName, tempExe)
        }

        // 2. Verify extracted binary SHA-256
        val actualExeSha256 = sha256(tempExe)
        if (!actualExeSha256.equals(pinned.executableSha256, ignoreCase = true)) {
          throw SecurityException(
              "SHA-256 mismatch for extracted xdelta binary ${pinned.executableName}: expected ${pinned.executableSha256}, got $actualExeSha256")
        }

        if (platform.os != Os.WINDOWS && !tempExe.toFile().setExecutable(true, false)) {
          System.err.println(
              "[XDeltaApplier] Warning: Failed to set executable permission on $tempExe")
        }

        Files.move(tempExe, targetExe, StandardCopyOption.REPLACE_EXISTING)
        if (platform.os != Os.WINDOWS && !targetExe.toFile().setExecutable(true, false)) {
          System.err.println(
              "[XDeltaApplier] Warning: Failed to set executable permission on $targetExe")
        }

        val canExecuteOnHost = (Platform.current().os == platform.os)
        if (!canExecuteOnHost || testBinary(targetExe.toString())) {
          println("[XDeltaApplier] Successfully installed verified xdelta3 at $targetExe")
          return targetExe.toString()
        }
      } finally {
        Files.deleteIfExists(tempExe)
      }
    } catch (e: Exception) {
      System.err.println("[XDeltaApplier] Failed to download/install xdelta3: ${e.message}")
      e.printStackTrace()
    } finally {
      Files.deleteIfExists(tempArchive)
    }
    return null
  }

  private fun extractFromZip(zipPath: Path, targetName: String, destination: Path) {
    BufferedInputStream(Files.newInputStream(zipPath)).use { bIn ->
      ZipInputStream(bIn).use { zip ->
        var entry = zip.nextEntry
        while (entry != null) {
          val fileName = Path.of(entry.name).fileName?.toString()
          if (!entry.isDirectory && fileName == targetName) {
            BufferedOutputStream(Files.newOutputStream(destination)).use { out -> zip.copyTo(out) }
            return
          }
          entry = zip.nextEntry
        }
      }
    }
    error("Could not find $targetName in zip archive $zipPath")
  }

  private fun extractFromTarGz(tarGzPath: Path, targetName: String, destination: Path) {
    BufferedInputStream(Files.newInputStream(tarGzPath)).use { bIn ->
      GZIPInputStream(bIn).use { gzIn -> extractFromTarStream(gzIn, targetName, destination) }
    }
  }

  private fun extractFromTarStream(input: InputStream, targetName: String, destination: Path) {
    val header = ByteArray(512)
    val buffer = ByteArray(8192)

    while (true) {
      val read = readFully(input, header)
      if (read < 512 || header.all { it == 0.toByte() }) {
        break
      }

      val rawName = String(header, 0, 100, StandardCharsets.UTF_8).trimEnd('\u0000').trim()
      val size = parseOctal(header, 124, 12)
      val fileName = Path.of(rawName).fileName?.toString()

      if (fileName == targetName) {
        BufferedOutputStream(Files.newOutputStream(destination)).use { out ->
          var remaining = size
          while (remaining > 0) {
            val toRead = minOf(buffer.size.toLong(), remaining).toInt()
            val chunkRead = input.read(buffer, 0, toRead)
            if (chunkRead == -1) throw EOFException("Unexpected EOF in tar entry")
            out.write(buffer, 0, chunkRead)
            remaining -= chunkRead
          }
        }
        return
      }

      // Skip entry data + padding
      val pad = ((512 - (size % 512)) % 512).toInt()
      val totalToSkip = size + pad
      var skipped = 0L
      while (skipped < totalToSkip) {
        val count = input.skip(totalToSkip - skipped)
        if (count <= 0) {
          if (input.read() == -1) break
          skipped++
        } else {
          skipped += count
        }
      }
    }
    error("Could not find $targetName in tar.gz archive")
  }

  private fun readFully(input: InputStream, buffer: ByteArray): Int {
    var total = 0
    while (total < buffer.size) {
      val read = input.read(buffer, total, buffer.size - total)
      if (read == -1) break
      total += read
    }
    return total
  }

  private fun parseOctal(header: ByteArray, offset: Int, length: Int): Long {
    var result = 0L
    for (i in offset until offset + length) {
      val b = header[i]
      if (b == 0.toByte() || b == ' '.code.toByte()) {
        if (result > 0) break else continue
      }
      if (b in '0'.code.toByte()..'7'.code.toByte()) {
        result = (result shl 3) or (b - '0'.code.toByte()).toLong()
      }
    }
    return result
  }
}
