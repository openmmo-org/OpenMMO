package de.fiereu.openmmo.launcher.launch

import de.fiereu.openmmo.launcher.FeedServer
import de.fiereu.openmmo.launcher.FeedTls
import de.fiereu.openmmo.launcher.client.FeedClient
import de.fiereu.openmmo.launcher.client.ManagedInstall
import java.net.http.HttpClient
import java.nio.file.Path

private const val ROOT_PROPERTY = "openmmo.root"
private const val PORT_PROPERTY = "openmmo.devFeedPort"
private const val REVISION_PROPERTY = "openmmo.revision"
private const val MANIFESTS_PROPERTY = "openmmo.manifests"
private const val LAUNCH_UI_PROPERTY = "openmmo.launchUi"

const val DEV_TRUSTSTORE = "dev-truststore.p12"

// Its own process, because the client reads the feed for as long as it runs and the launcher exits
// as soon as the game starts.
object DevFeedServerCli {

  @JvmStatic
  fun main(args: Array<String>) {
    val root = System.getProperty(ROOT_PROPERTY)?.let(Path::of) ?: ManagedInstall.defaultRoot()
    val port = System.getProperty(PORT_PROPERTY)?.toInt() ?: DEV_FEED_PORT
    val install = ManagedInstall(root).create()

    val revision =
        System.getProperty(REVISION_PROPERTY)?.toInt()
            ?: FeedClient(HttpClient.newHttpClient()).load().main.revision

    val keyStore = FeedTls.keyStore()
    val server = FeedServer(GeneratedKeys.privateKey("/feed.private.pem"), keyStore, port)
    server.publish(revision.toLong())

    val trustStore =
        FeedTls.writeTrustStore(
            server.certificate(), install.root, install.root.resolve(DEV_TRUSTSTORE))

    server.start()
    println("dev feed on https://127.0.0.1:$port for revision $revision")
    println("  login server 127.0.0.1:2106")
    println("  trust store  $trustStore")
    Runtime.getRuntime().addShutdownHook(Thread { server.stop() })

    if (System.getProperty(LAUNCH_UI_PROPERTY).toBoolean()) {
      println("starting the launcher, this feed stays up after it hands off")
      startLauncher(trustStore).waitFor()
    }

    println("Leave this running while the client is open. Ctrl+C to stop.")
    Thread.currentThread().join()
  }

  // The launcher reads the server key from the feed too, and this one signed its own certificate.
  private fun startLauncher(trustStore: Path): Process {
    val java = Path.of(System.getProperty("java.home"), "bin", "java").toString()
    val forwarded =
        listOf(ROOT_PROPERTY, MANIFESTS_PROPERTY).mapNotNull { name ->
          System.getProperty(name)?.let { "-D$name=$it" }
        }
    return ProcessBuilder(
            listOf(java, "-cp", System.getProperty("java.class.path")) +
                forwarded +
                listOf(
                    "-Djavax.net.ssl.trustStore=$trustStore",
                    "-Djavax.net.ssl.trustStorePassword=${FeedTls.password.concatToString()}",
                    "-Djavax.net.ssl.trustStoreType=PKCS12",
                    "--enable-native-access=ALL-UNNAMED",
                    "de.fiereu.openmmo.launcher.ui.MainKt"))
        .inheritIO()
        .start()
  }
}
