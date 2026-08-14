package de.fiereu.openmmo.launcher.ui

import de.fiereu.openmmo.launcher.client.ArchiveClient
import de.fiereu.openmmo.launcher.client.ClientSync
import de.fiereu.openmmo.launcher.client.Downloader
import de.fiereu.openmmo.launcher.client.FeedClient
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.Platform
import de.fiereu.openmmo.launcher.launch.LaunchStage
import de.fiereu.openmmo.launcher.launch.LauncherPipeline
import de.fiereu.openmmo.launcher.patch.ArchivePatcher
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
    private val highestRevision: () -> Int? = { null },
    private val assets: PatchAssets = PatchAssets.none(),
    private val http: HttpClient = ArchiveClient.defaultHttpClient(),
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
      LauncherPipeline(
              install = install,
              manifests = manifests,
              assets = assets,
              http = http,
              platform = platform,
              highestRevision = highestRevision,
          )
          .run(::report)
    }
    update { it.copy(status = "Started", detail = "Handing off to the client") }
    onStarted()
  }

  fun verify() = run {
    update { it.copy(status = "Verifying", detail = "Hashing the installed files") }
    withContext(dispatcher) {
      val feeds = FeedClient(http).load()
      val currentRevision = feeds.main.revision
      val directManifest = manifests(currentRevision)

      val targetRevision =
          if (directManifest != null) {
            currentRevision
          } else {
            highestRevision() ?: currentRevision
          }

      val sync = ClientSync(install, Downloader(http), platform)
      val archiveClient = ArchiveClient(http)
      val targetFeed =
          if (directManifest != null) {
            feeds.update
          } else {
            archiveClient.fetchUpdateFeed(targetRevision)
          }

      val plan = sync.plan(targetFeed)
      if (plan.isUpToDate) {
        update { it.copy(status = "Ready", detail = "Everything matches the feed") }
        return@withContext
      }

      if (directManifest != null) {
        update {
          it.copy(detail = "Repairing ${plan.stale.size} files, ${bytes(plan.bytesToFetch)}")
        }
        sync.execute(plan, feeds.mirror) { report(LaunchStage.Syncing(it)) }
        update { it.copy(status = "Ready", detail = "Repaired ${plan.stale.size} files") }
      } else {
        update {
          it.copy(
              detail =
                  "Repairing client to revision $targetRevision. Please wait, this could take a while.")
        }
        sync.syncAll(feeds) { report(LaunchStage.Syncing(it)) }
        report(LaunchStage.DeltaPatching)
        ArchivePatcher(install, archiveClient, platform)
            .apply(currentRevision, targetRevision, feeds.update)
        update { it.copy(status = "Ready", detail = "Restored revision $targetRevision") }
      }
    }
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
        is LaunchStage.DeltaPatching ->
            update {
              it.copy(
                  status = "Delta Patching",
                  detail = "Applying delta patches. Please wait, this could take a while.",
                  progress = null,
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
        System.err.println("[Launcher] Encountered error during execution:")
        e.printStackTrace()
        update { it.copy(status = "Stopped", detail = "", error = e.message ?: e.toString()) }
      } finally {
        update { it.copy(busy = false, progress = null) }
      }
    }
  }
}
