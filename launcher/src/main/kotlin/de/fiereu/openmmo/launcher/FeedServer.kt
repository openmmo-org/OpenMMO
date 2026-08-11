package de.fiereu.openmmo.launcher

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpsConfigurator
import com.sun.net.httpserver.HttpsServer
import de.fiereu.openmmo.launcher.launch.GAME_KEY_FILE
import de.fiereu.openmmo.launcher.launch.SIGNATURE_SUFFIX
import java.io.IOException
import java.net.InetSocketAddress
import java.security.KeyStore
import java.security.PrivateKey
import java.security.SecureRandom
import java.security.Signature
import java.security.cert.X509Certificate
import java.util.concurrent.Executors
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext

private const val TEMPLATE = "/main_feed_template.xml"

// The names we publish, which is what the client is patched to ask for.
const val MAIN_NAME = "main.xml"
const val SIGNATURE_NAME = "main.sig256"
const val NEWS_NAME = "news.xml"

private val DEV_NEWS =
    ("""<?xml version="1.0"?><rss version="2.0"><channel><title>OpenMMO</title>""" +
            """<link>https://github.com/openmmo-org/OpenMMO</link>""" +
            """<description>Development feed</description><language>en</language>""" +
            """<item><title>Development build</title><description>Connected to a local server.""" +
            """</description><guid isPermaLink="false">openmmo-dev</guid></item></channel></rss>""")
        .toByteArray(Charsets.UTF_8)
private const val LOGIN_PORT = 2106

// A patched url has room for a four digit port after 127.0.0.1 and no more.
private const val MIN_PORT = 2048
private const val MAX_PORT = 8999
private val BUSY_PORTS = setOf(2106, 3000, 3306, 5000, 5432, 5900, 6379, 8000, 8080, 8443)

/**
 * Serves a signed stand in for the PokeMMO main feed over loopback HTTPS.
 *
 * The client takes the login server address from this feed and refuses to start without one, so
 * pointing it here is the only way to reach a local server. The native image only enables the https
 * url protocol, so plain http is not an option.
 */
class FeedServer(private val signingKey: PrivateKey, keyStore: KeyStore, port: Int? = null) {

  private val server: HttpsServer = if (port == null) bind() else bind(port)
  private val certificate = FeedTls.certificate(keyStore)

  private var body = ByteArray(0)
  private var signature = ByteArray(0)
  private val gameKey = resource("/$GAME_KEY_FILE").toByteArray(Charsets.UTF_8)
  private val gameKeySignature = sign(gameKey)

  val port: Int = server.address.port

  init {
    server.httpsConfigurator = HttpsConfigurator(sslContext(keyStore))
    server.createContext("/", ::handle)
    server.executor =
        Executors.newCachedThreadPool { runnable ->
          Thread(runnable, "openmmo-feed").apply { isDaemon = true }
        }
  }

  fun certificate(): X509Certificate = certificate

  /** Builds and signs a feed that sends the client to the local login server at [revision]. */
  fun publish(revision: Long) {
    val xml =
        resource(TEMPLATE)
            .replace("{{IP}}", LOOPBACK)
            .replace("{{PORT}}", LOGIN_PORT.toString())
            .replace("{{FEED_PORT}}", port.toString())
            .replace("{{REVISION}}", revision.toString())
    body = xml.toByteArray(Charsets.UTF_8)
    signature = sign(body)
  }

  private fun sign(data: ByteArray): ByteArray =
      Signature.getInstance("SHA256withRSA").run {
        initSign(signingKey)
        update(data)
        sign()
      }

  fun start() = server.start()

  fun stop() = server.stop(0)

  private fun handle(exchange: HttpExchange) {
    val data =
        when {
          exchange.requestURI.path.endsWith(SIGNATURE_NAME) -> signature
          exchange.requestURI.path.endsWith(MAIN_NAME) -> body
          exchange.requestURI.path.endsWith(NEWS_NAME) -> DEV_NEWS
          exchange.requestURI.path.endsWith(GAME_KEY_FILE + SIGNATURE_SUFFIX) -> gameKeySignature
          exchange.requestURI.path.endsWith(GAME_KEY_FILE) -> gameKey
          else -> null
        }
    exchange.use {
      if (data == null) {
        it.sendResponseHeaders(404, -1)
      } else {
        it.sendResponseHeaders(200, data.size.toLong())
        it.responseBody.write(data)
      }
    }
  }

  private fun sslContext(keyStore: KeyStore): SSLContext {
    val factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
    factory.init(keyStore, FeedTls.password)
    return SSLContext.getInstance("TLSv1.3").apply {
      init(factory.keyManagers, null, SecureRandom())
    }
  }

  private fun bind(port: Int): HttpsServer =
      HttpsServer.create(InetSocketAddress(LOOPBACK, port), 0)

  private fun bind(): HttpsServer {
    val random = SecureRandom()
    repeat(128) {
      val candidate = MIN_PORT + random.nextInt(MAX_PORT - MIN_PORT + 1)
      if (candidate !in BUSY_PORTS) {
        runCatching {
              return HttpsServer.create(InetSocketAddress(LOOPBACK, candidate), 0)
            }
            .exceptionOrNull()
            ?.let { if (it !is IOException) throw it }
      }
    }
    error("Could not find a free port for the feed server")
  }

  private fun resource(path: String): String =
      FeedServer::class.java.getResourceAsStream(path)?.use { it.readBytes().decodeToString() }
          ?: error("Resource not found: $path")
}
