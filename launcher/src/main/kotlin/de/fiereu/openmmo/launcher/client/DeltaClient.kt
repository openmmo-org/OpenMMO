package de.fiereu.openmmo.launcher.client

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.delay
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class DeltaFilePatch(
    val name: String,
    @SerialName("deltaUrl") val deltaUrl: String = "",
    @SerialName("sourceSha256") val sourceSha256: String = "",
    @SerialName("targetSha256") val targetSha256: String = "",
    @SerialName("targetSize") val targetSize: Long = 0L,
    @SerialName("deltaSha256") val deltaSha256: String = "",
    @SerialName("deltaSize") val deltaSize: Long = 0L,
) {
  fun resolveDeltaUrl(baseUrl: String): String {
    if (deltaUrl.startsWith("http://") || deltaUrl.startsWith("https://")) return deltaUrl
    val path = if (deltaUrl.isNotEmpty()) deltaUrl else "$name.xdelta"
    return if (baseUrl.endsWith("/")) "$baseUrl$path" else "$baseUrl/$path"
  }
}

@Serializable
data class DeltaManifest(
    @SerialName("fromRevision") val fromRevision: Int,
    @SerialName("toRevision") val toRevision: Int,
    val patches: List<DeltaFilePatch>,
    val targetFiles: List<RemoteFile> = emptyList(),
)

class DeltaClient(
    private val http: HttpClient,
    private val deltaOrigin: String,
    private val downloader: Downloader = Downloader(http),
    private val timeout: Duration = Duration.ofSeconds(30),
) {
  private val json = Json { ignoreUnknownKeys = true }

  fun fetchManifest(fromRevision: Int): DeltaManifest? {
    val baseUrl = "$deltaOrigin/$fromRevision"
    val url = "$baseUrl/manifest.json"
    val request = HttpRequest.newBuilder(URI.create(url)).timeout(timeout).GET().build()
    val response =
        runCatching { http.send(request, HttpResponse.BodyHandlers.ofString()) }.getOrNull()
            ?: return null
    if (response.statusCode() != 200) return null
    return runCatching { json.decodeFromString<DeltaManifest>(response.body()) }.getOrNull()
  }

  suspend fun downloadPatch(
      fromRevision: Int,
      patch: DeltaFilePatch,
      targetPath: Path,
      attempts: Int = 5,
  ) {
    val baseUrl = "$deltaOrigin/$fromRevision"
    val patchUrl = patch.resolveDeltaUrl(baseUrl)
    if (patch.deltaSha256.isNotEmpty() && patch.deltaSize > 0) {
      val expectedFile =
          RemoteFile(
              name = patch.name,
              sha256 = patch.deltaSha256,
              size = patch.deltaSize,
          )
      downloader.fetch(patchUrl, targetPath, expectedFile)
    } else {
      fetchWithRetry(patchUrl, targetPath, attempts)
    }
  }

  private suspend fun fetchWithRetry(url: String, target: Path, attempts: Int) {
    for (attempt in 1..attempts) {
      try {
        fetchDirect(url, target)
        return
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        if (attempt == attempts) throw e
        delay(minOf(attempt * 500L, 3000L))
      }
    }
  }

  private fun fetchDirect(url: String, target: Path) {
    target.parent?.let(Files::createDirectories)
    val request = HttpRequest.newBuilder(URI.create(url)).timeout(timeout).GET().build()
    val response = http.send(request, HttpResponse.BodyHandlers.ofByteArray())
    if (response.statusCode() != 200) error("GET $url returned ${response.statusCode()}")
    Files.write(target, response.body())
  }
}
