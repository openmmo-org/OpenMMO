package de.fiereu.openmmo.launcher.launch

import de.fiereu.openmmo.launcher.client.ClientSync
import de.fiereu.openmmo.launcher.client.Downloader
import de.fiereu.openmmo.launcher.client.FeedClient
import de.fiereu.openmmo.launcher.client.Feeds
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.Platform
import de.fiereu.openmmo.launcher.client.SyncProgress
import de.fiereu.openmmo.launcher.patch.PatchAssets
import de.fiereu.openmmo.launcher.patch.PatchEngine
import de.fiereu.openmmo.launcher.patch.PatchManifest
import de.fiereu.openmmo.launcher.patch.PatchManifestParser
import de.fiereu.openmmo.launcher.patch.RuntimeTree
import java.net.http.HttpClient
import java.nio.file.Files
import java.nio.file.Path

sealed interface LaunchStage {
  data object Resolving : LaunchStage

  data class Syncing(val progress: SyncProgress) : LaunchStage

  data object Patching : LaunchStage

  data object Starting : LaunchStage
}

class UnsupportedRevisionException(revision: Int) :
    Exception(
        "OpenMMO has no patch set for client revision $revision yet. " +
            "Patching a client whose layout changed would corrupt it, so this is a hard stop.")

class LauncherPipeline(
    private val install: ManagedInstall,
    private val manifests: (Int) -> PatchManifest?,
    private val assets: PatchAssets = PatchAssets.none(),
    private val http: HttpClient = HttpClient.newHttpClient(),
    private val platform: Platform = Platform.current(),
) {

  suspend fun run(onStage: (LaunchStage) -> Unit = {}): Process {
    onStage(LaunchStage.Resolving)
    install.create()
    val feeds: Feeds = FeedClient(http).load()
    val manifest =
        manifests(feeds.main.revision) ?: throw UnsupportedRevisionException(feeds.main.revision)

    val sync = ClientSync(install, Downloader(http), platform)
    sync.sync(feeds) { onStage(LaunchStage.Syncing(it)) }

    onStage(LaunchStage.Patching)
    val runtime: RuntimeTree =
        PatchEngine(install, assets, keys(), executableName(platform))
            .apply(manifest, feeds.update, feedPatches() + loginHostPatch())

    onStage(LaunchStage.Starting)
    return GameLaunch(install, platform).start(runtime)
  }

  /**
   * The feed origin publishes the server key, so rotating it does not need a new launcher. Only the
   * feed key itself stays baked in, because it is what proves the rest genuine.
   */
  private fun keys(): Map<String, String> =
      GeneratedKeys.values() +
          (GeneratedKeys.GAME_PUBLIC to
              ServerKeys(http, FeedOrigin.configured, GeneratedKeys.feedPublicKey())
                  .gamePublicKeyBase64())

  companion object {
    fun manifestsIn(directory: Path): (Int) -> PatchManifest? = { revision ->
      val file = directory.resolve("manifest-$revision.toml")
      if (Files.isRegularFile(file)) PatchManifestParser.parse(Files.readAllBytes(file)) else null
    }
  }
}
