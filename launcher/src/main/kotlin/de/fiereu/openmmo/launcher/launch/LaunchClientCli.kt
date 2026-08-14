package de.fiereu.openmmo.launcher.launch

import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.patch.PatchAssets
import java.nio.file.Path
import kotlinx.coroutines.runBlocking

private const val ROOT_PROPERTY = "openmmo.root"
private const val MANIFESTS_PROPERTY = "openmmo.manifests"

/** Runs the same launch pipeline as the desktop Play button without opening the launcher window. */
object LaunchClientCli {

  @JvmStatic
  fun main(args: Array<String>) {
    runBlocking {
      val root = System.getProperty(ROOT_PROPERTY)?.let(Path::of) ?: ManagedInstall.defaultRoot()
      val manifests =
          System.getProperty(MANIFESTS_PROPERTY)?.let(Path::of) ?: root.resolve("manifests")

      LauncherPipeline(
              install = ManagedInstall(root),
              manifests = LauncherPipeline.manifestsIn(manifests),
              assets = PatchAssets.ofDirectory(manifests),
          )
          .run { println(it) }
    }
  }
}
