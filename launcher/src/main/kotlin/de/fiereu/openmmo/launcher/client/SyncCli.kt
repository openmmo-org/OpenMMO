package de.fiereu.openmmo.launcher.client

import java.net.http.HttpClient
import java.nio.file.Path
import java.time.Duration
import kotlin.system.exitProcess
import kotlinx.coroutines.runBlocking

private const val ROOT_PROPERTY = "openmmo.root"
private const val FILTER_PROPERTY = "openmmo.filter"
private const val DOWNLOAD_PROPERTY = "openmmo.download"

object SyncCli {

  @JvmStatic
  fun main(args: Array<String>) {
    val root = System.getProperty(ROOT_PROPERTY)?.let(Path::of) ?: ManagedInstall.defaultRoot()
    val filter = System.getProperty(FILTER_PROPERTY).orEmpty()
    val download = System.getProperty(DOWNLOAD_PROPERTY).toBoolean()

    val http =
        HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build()

    val feeds =
        try {
          FeedClient(http).load()
        } catch (e: FeedUnavailableException) {
          System.err.println(e.message)
          exitProcess(1)
        }

    val platform = Platform.current()
    println("mirror     ${feeds.mirror}")
    println("revision   ${feeds.main.revision} (minimum ${feeds.main.minRevision})")
    println("login      ${feeds.main.loginHost}:${feeds.main.loginPort}")
    println("platform   ${platform.os.feedName}/${platform.arch.feedName}")
    println(
        "feed files ${feeds.update.files.size}, ${feeds.update.forPlatform(platform).size} here")

    val install = ManagedInstall(root).create()
    val selected =
        if (filter.isEmpty()) feeds.update
        else UpdateFeed(feeds.update.files.filter { it.name.startsWith(filter) })
    if (filter.isNotEmpty()) println("filter     $filter matches ${selected.files.size}")

    val sync = ClientSync(install, Downloader(http), platform)
    val plan = sync.plan(selected)
    println("root       $root")
    println(
        "plan       ${plan.stale.size} to fetch (${plan.bytesToFetch / 1024} KiB), " +
            "${plan.current.size} already current")

    if (!download) {
      println("dry run, pass -D$DOWNLOAD_PROPERTY=true to fetch")
      return
    }

    runBlocking {
      sync.execute(plan, feeds.mirror) {
        println("  [${it.completedFiles}/${it.totalFiles}] ${it.current}")
      }
    }
    val after = sync.plan(selected)
    println(
        if (after.isUpToDate) "done, everything verifies" else "still stale: ${after.stale.size}")
  }
}
