package de.fiereu.openmmo.launcher.client

import java.io.IOException
import java.nio.file.Files
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext

data class SyncPlan(val stale: List<RemoteFile>, val current: List<RemoteFile>) {
  val bytesToFetch: Long
    get() = stale.sumOf { it.size }

  val isUpToDate: Boolean
    get() = stale.isEmpty()
}

data class SyncProgress(
    val completedFiles: Int,
    val totalFiles: Int,
    val fetchedBytes: Long,
    val totalBytes: Long,
    val current: String,
)

class ClientSync(
    private val install: ManagedInstall,
    private val downloader: Downloader,
    private val platform: Platform = Platform.current(),
    private val channel: String = LIVE_CHANNEL,
    private val concurrency: Int = 6,
    private val attempts: Int = 10,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

  fun plan(feed: UpdateFeed, forPlatformOnly: Boolean = true): SyncPlan {
    val stale = mutableListOf<RemoteFile>()
    val current = mutableListOf<RemoteFile>()
    val fileList = if (forPlatformOnly) feed.forPlatform(platform) else feed.files
    for (file in fileList) {
      if (isCurrent(file)) current += file else stale += file
    }
    return SyncPlan(stale, current)
  }

  private fun isCurrent(file: RemoteFile): Boolean {
    val target = install.resolve(file.name)
    if (!Files.isRegularFile(target)) return false
    if (file.onlyIfNotExists) return true
    if (Files.size(target) != file.size) return false
    return sha256(target).equals(file.sha256, ignoreCase = true)
  }

  suspend fun execute(
      plan: SyncPlan,
      mirror: String,
      onProgress: (SyncProgress) -> Unit = {},
  ) {
    val total = plan.stale.size
    val totalBytes = plan.bytesToFetch
    var completed = 0
    var fetched = 0L
    val gate = Semaphore(concurrency)

    coroutineScope {
      for (file in plan.stale) {
        launch(dispatcher) {
          gate.withPermit {
            fetchWithRetry(mirror, file)
            synchronized(this@ClientSync) {
              completed++
              fetched += file.size
              onProgress(SyncProgress(completed, total, fetched, totalBytes, file.name))
            }
          }
        }
      }
    }
  }

  suspend fun sync(feeds: Feeds, onProgress: (SyncProgress) -> Unit = {}): SyncPlan {
    install.create()
    val plan = withContext(dispatcher) { plan(feeds.update, forPlatformOnly = true) }
    execute(plan, feeds.mirror, onProgress)
    return plan
  }

  suspend fun syncAll(feeds: Feeds, onProgress: (SyncProgress) -> Unit = {}): SyncPlan {
    install.create()
    val plan = withContext(dispatcher) { plan(feeds.update, forPlatformOnly = false) }
    execute(plan, feeds.mirror, onProgress)
    return plan
  }

  /**
   * A failed attempt leaves its part file behind, so the next one resumes rather than starting
   * over. Giving up throws, which cancels the whole sync.
   */
  private suspend fun fetchWithRetry(mirror: String, file: RemoteFile) {
    val url = contentUrl(mirror, file)
    val target = install.resolve(file.name)
    for (attempt in 1..attempts) {
      try {
        return downloader.fetch(url, target, file)
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        if (attempt == attempts) {
          throw IOException("Gave up on ${file.name} after $attempts attempts", e)
        }
        delay(minOf(attempt * 500L, 5_000L))
      }
    }
  }

  internal fun contentUrl(mirror: String, file: RemoteFile): String =
      "$mirror/$channel/current/client/${file.name}?v=${file.cacheBuster}"
}
