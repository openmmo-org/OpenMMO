package de.fiereu.openmmo.launcher.ui

import de.fiereu.openmmo.launcher.client.ClientSync
import de.fiereu.openmmo.launcher.client.Downloader
import de.fiereu.openmmo.launcher.client.FeedClient
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.Platform
import de.fiereu.openmmo.launcher.launch.LaunchStage
import de.fiereu.openmmo.launcher.launch.LauncherPipeline
import de.fiereu.openmmo.launcher.patch.PatchAssets
import de.fiereu.openmmo.launcher.patch.PatchManifest
import java.net.http.HttpClient
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class LauncherUiState(
    val status: String = "Ready",
    val detail: String = "",
    val progress: Float? = null,
    val busy: Boolean = false,
    val error: String? = null,
)

private fun bytes(count: Long): String =
    when {
      count >= 1L shl 30 -> "%.1f GiB".format(count.toDouble() / (1L shl 30))
      count >= 1L shl 20 -> "%.0f MiB".format(count.toDouble() / (1L shl 20))
      else -> "%d KiB".format(count / 1024)
    }

class LauncherController(
    private val install: ManagedInstall,
    private val manifests: (Int) -> PatchManifest?,
    private val assets: PatchAssets = PatchAssets.none(),
    private val http: HttpClient = HttpClient.newHttpClient(),
    private val platform: Platform = Platform.current(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val scope: CoroutineScope,
    private val onState: (LauncherUiState) -> Unit,
) {

  private var state = LauncherUiState()

  // Progress arrives on download threads while the buttons write from the UI thread.
  @Synchronized
  private fun update(block: (LauncherUiState) -> LauncherUiState) {
    state = block(state)
    onState(state)
  }

  fun play(onStarted: () -> Unit = {}) = run {
    withContext(dispatcher) {
      LauncherPipeline(install, manifests, assets, http, platform).run(::report)
    }
    update { it.copy(status = "Started", detail = "Handing off to the client") }
    onStarted()
  }

  fun verify() = run {
    update { it.copy(status = "Verifying", detail = "Hashing the installed files") }
    val feeds = withContext(dispatcher) { FeedClient(http).load() }
    val sync = ClientSync(install, Downloader(http), platform)
    val plan = withContext(dispatcher) { sync.plan(feeds.update) }
    if (plan.isUpToDate) {
      update { it.copy(status = "Ready", detail = "Everything matches the feed") }
      return@run
    }
    update { it.copy(detail = "Repairing ${plan.stale.size} files, ${bytes(plan.bytesToFetch)}") }
    sync.execute(plan, feeds.mirror) { report(LaunchStage.Syncing(it)) }
    update { it.copy(status = "Ready", detail = "Repaired ${plan.stale.size} files") }
  }

  private fun report(stage: LaunchStage) =
      when (stage) {
        is LaunchStage.Resolving ->
            update { it.copy(status = "Checking for updates", detail = "", progress = null) }
        is LaunchStage.Syncing ->
            update {
              val p = stage.progress
              it.copy(
                  status = "Downloading",
                  detail = "${p.completedFiles} of ${p.totalFiles}, ${p.current}",
                  progress =
                      if (p.totalBytes > 0) p.fetchedBytes.toFloat() / p.totalBytes else null,
              )
            }
        is LaunchStage.Patching ->
            update {
              it.copy(status = "Patching", detail = "Building the runtime", progress = null)
            }
        is LaunchStage.Starting ->
            update { it.copy(status = "Starting", detail = "Launching the client") }
      }

  private fun run(block: suspend () -> Unit) {
    if (state.busy) return
    update { it.copy(busy = true, error = null) }
    scope.launch {
      try {
        block()
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        update { it.copy(status = "Stopped", detail = "", error = e.message ?: e.toString()) }
      } finally {
        update { it.copy(busy = false, progress = null) }
      }
    }
  }
}
