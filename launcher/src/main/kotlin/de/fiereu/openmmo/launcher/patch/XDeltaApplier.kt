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
import java.time.Duration
import java.util.zip.GZIPInputStream
import java.util.zip.ZipInputStream
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object XDeltaApplier {

  const val XDELTA_RELEASES_API = "https://api.github.com/repos/jmacd/xdelta/releases/latest"

  private val json = Json { ignoreUnknownKeys = true }

  @Serializable
  data class GitHubRelease(
      @SerialName("tag_name") val tagName: String? = null,
      val assets: List<GitHubAsset> = emptyList(),
  )

  @Serializable
  data class GitHubAsset(
      val name: String,
      @SerialName("browser_download_url") val browserDownloadUrl: String,
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

    // 2. Download latest release from GitHub
    if (toolsDir != null) {
      val downloaded = downloadXDeltaBinary(toolsDir, platform, http)
      if (downloaded != null) return downloaded
    }

    error("No usable xdelta3 binary found on system and could not download from GitHub releases")
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

  private fun testBinary(binPath: String): Boolean =
      runCatching {
            val process = ProcessBuilder(binPath, "-V").start()
            process.waitFor() == 0
          }
          .getOrDefault(false)

  fun selectAsset(assets: List<GitHubAsset>, platform: Platform): GitHubAsset? =
      when (platform.os) {
        Os.WINDOWS -> {
          assets.firstOrNull {
            it.name.contains("windows", ignoreCase = true) &&
                it.name.endsWith(".zip", ignoreCase = true)
          } ?: assets.firstOrNull { it.name.endsWith(".exe.zip", ignoreCase = true) }
        }
        Os.MACOS -> {
          if (platform.arch == Arch.ARM64) {
            assets.firstOrNull {
              it.name.contains("macos", ignoreCase = true) &&
                  (it.name.contains("arm64", ignoreCase = true) ||
                      it.name.contains("aarch64", ignoreCase = true))
            }
                ?: assets.firstOrNull {
                  it.name.contains("macos", ignoreCase = true) ||
                      it.name.contains("darwin", ignoreCase = true)
                }
          } else {
            assets.firstOrNull {
              it.name.contains("macos", ignoreCase = true) &&
                  (it.name.contains("x86_64", ignoreCase = true) ||
                      it.name.contains("x64", ignoreCase = true))
            }
                ?: assets.firstOrNull {
                  it.name.contains("macos", ignoreCase = true) ||
                      it.name.contains("darwin", ignoreCase = true)
                }
          }
        }
        Os.LINUX -> {
          if (platform.arch == Arch.ARM64) {
            assets.firstOrNull {
              it.name.contains("linux", ignoreCase = true) &&
                  (it.name.contains("arm64", ignoreCase = true) ||
                      it.name.contains("aarch64", ignoreCase = true))
            } ?: assets.firstOrNull { it.name.contains("linux", ignoreCase = true) }
          } else {
            assets.firstOrNull {
              it.name.contains("linux", ignoreCase = true) &&
                  (it.name.contains("x86_64", ignoreCase = true) ||
                      it.name.contains("x64", ignoreCase = true))
            } ?: assets.firstOrNull { it.name.contains("linux", ignoreCase = true) }
          }
        }
      }

  fun downloadXDeltaBinary(
      toolsDir: Path,
      platform: Platform = Platform.current(),
      http: HttpClient = ArchiveClient.defaultHttpClient(),
      releasesApiUrl: String = XDELTA_RELEASES_API,
  ): String? {
    Files.createDirectories(toolsDir)
    val exeName = if (platform.os == Os.WINDOWS) "xdelta3.exe" else "xdelta3"
    val targetExe = toolsDir.resolve(exeName)

    if (Files.isRegularFile(targetExe) && testBinary(targetExe.toString())) {
      return targetExe.toString()
    }

    val releaseRequest =
        HttpRequest.newBuilder()
            .uri(URI.create(releasesApiUrl))
            .header("User-Agent", "OpenMMO-Launcher")
            .header("Accept", "application/vnd.github+json")
            .timeout(Duration.ofSeconds(15))
            .GET()
            .build()

    val releaseResponse = http.send(releaseRequest, HttpResponse.BodyHandlers.ofString())
    if (releaseResponse.statusCode() !in 200..299) {
      System.err.println(
          "[XDeltaApplier] Failed to fetch latest xdelta release from $releasesApiUrl: HTTP ${releaseResponse.statusCode()}")
      return null
    }

    val release = json.decodeFromString<GitHubRelease>(releaseResponse.body())
    val selectedAsset =
        selectAsset(release.assets, platform)
            ?: run {
              System.err.println(
                  "[XDeltaApplier] No matching asset found in latest release (${release.tagName}) for $platform")
              return null
            }

    val downloadUrl = selectedAsset.browserDownloadUrl
    val assetName = selectedAsset.name
    println(
        "[XDeltaApplier] Downloading xdelta3 (${release.tagName ?: "latest"}) from $downloadUrl to $toolsDir")

    val tempArchive = Files.createTempFile(toolsDir, "xdelta3-dl", ".tmp")
    try {
      val downloadRequest =
          HttpRequest.newBuilder()
              .uri(URI.create(downloadUrl))
              .header("User-Agent", "OpenMMO-Launcher")
              .timeout(Duration.ofMinutes(2))
              .GET()
              .build()
      val downloadResponse = http.send(downloadRequest, HttpResponse.BodyHandlers.ofInputStream())
      if (downloadResponse.statusCode() !in 200..299) {
        throw IOException(
            "Failed to download xdelta3 from $downloadUrl: HTTP ${downloadResponse.statusCode()}")
      }

      downloadResponse.body().use { inStream ->
        BufferedOutputStream(Files.newOutputStream(tempArchive)).use { outStream ->
          inStream.copyTo(outStream)
        }
      }

      val tempExe = toolsDir.resolve("$exeName.part")
      try {
        if (assetName.endsWith(".zip", ignoreCase = true)) {
          extractFromZip(tempArchive, exeName, tempExe)
        } else {
          extractFromTarGz(tempArchive, exeName, tempExe)
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

        if (testBinary(targetExe.toString())) {
          println("[XDeltaApplier] Successfully installed xdelta3 at $targetExe")
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
