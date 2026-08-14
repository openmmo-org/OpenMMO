package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.client.FeedClient
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.Platform
import de.fiereu.openmmo.launcher.launch.GeneratedKeys
import de.fiereu.openmmo.launcher.launch.executableName
import de.fiereu.openmmo.launcher.launch.feedTrustValues
import de.fiereu.openmmo.launcher.launch.redirectPatches
import java.net.http.HttpClient
import java.nio.file.Files
import java.nio.file.Path
import kotlin.system.exitProcess

private const val ROOT_PROPERTY = "openmmo.root"
private const val MANIFEST_PROPERTY = "openmmo.manifest"

object PatchCli {

  @JvmStatic
  fun main(args: Array<String>) {
    val root = System.getProperty(ROOT_PROPERTY)?.let(Path::of) ?: ManagedInstall.defaultRoot()
    val manifestPath =
        System.getProperty(MANIFEST_PROPERTY)?.let(Path::of)
            ?: error("Missing -D$MANIFEST_PROPERTY=<path to manifest xml>")

    val install = ManagedInstall(root).create()
    val manifest = PatchManifestParser.parse(Files.readAllBytes(manifestPath))
    val feeds = FeedClient(HttpClient.newHttpClient()).load()

    println("manifest   $manifestPath for revision ${manifest.revision}")
    println("feed       revision ${feeds.main.revision}")
    if (manifest.revision != feeds.main.revision) {
      System.err.println(
          "No patch manifest for client revision ${feeds.main.revision}, refusing to patch")
      exitProcess(1)
    }

    val assets = PatchAssets.ofDirectory(manifestPath.parent ?: Path.of("."))
    val platform = Platform.current()
    val tree =
        try {
          val extras = redirectPatches(manifest)
          PatchEngine(
                  install,
                  assets,
                  GeneratedKeys.values() + feedTrustValues(install),
                  executableName(platform),
                  platform = platform.feedName,
              )
              .apply(manifest, feeds.update, extras)
        } catch (e: PatchFailedException) {
          System.err.println("Patch failed: ${e.message}")
          exitProcess(1)
        }

    val byOrigin = tree.files.values.groupingBy { it }.eachCount()
    println("runtime    ${tree.root}")
    println("files      ${tree.files.size} total, $byOrigin")
    tree.files
        .filterValues { it != Origin.FEED }
        .forEach { (name, origin) -> println("  $origin $name") }
  }
}
