package de.fiereu.openmmo.launcher

import de.fiereu.openmmo.launcher.client.POKEMMO_MIRRORS
import java.io.BufferedInputStream
import java.io.IOException
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.URI
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executors

const val DEV_FEED_PROXY_PORT = 20444

private const val MAX_REQUEST_BYTES = 8 * 1024

/**
 * Tunnels the client's encrypted feed URLs to the development feed server.
 *
 * Revision 32824 no longer carries patchable URL strings. The client still honors the standard
 * HTTPS proxy properties, so feed mirror tunnels terminate locally while unrelated HTTPS traffic
 * passes through unchanged.
 */
class FeedProxy(
    private val feedPort: Int,
    port: Int = DEV_FEED_PROXY_PORT,
    private val onEvent: (String) -> Unit = {},
) {

  private val server = ServerSocket().apply { bind(InetSocketAddress(LOOPBACK, port)) }
  private val workers =
      Executors.newCachedThreadPool { runnable ->
        Thread(runnable, "openmmo-feed-proxy").apply { isDaemon = true }
      }
  private val feedHosts = POKEMMO_MIRRORS.mapTo(mutableSetOf()) { URI(it).host }

  val port: Int = server.localPort

  fun start() {
    workers.execute {
      while (!server.isClosed) {
        try {
          val client = server.accept()
          workers.execute { handle(client) }
        } catch (e: IOException) {
          if (!server.isClosed) throw e
        }
      }
    }
  }

  fun stop() {
    server.close()
    workers.shutdownNow()
  }

  private fun handle(client: Socket) {
    client.use {
      val request = readHeaders(BufferedInputStream(it.getInputStream())) ?: return
      val firstLine = request.substringBefore("\r\n")
      val authority = firstLine.removePrefix("CONNECT ").substringBefore(' ')
      val host = authority.substringBeforeLast(':', "")
      val requestedPort = authority.substringAfterLast(':', "").toIntOrNull()
      if (!firstLine.startsWith("CONNECT ") || host.isEmpty() || requestedPort == null) {
        reject(it, 400, "Bad Request")
        return
      }

      val targetHost = if (host in feedHosts && requestedPort == 443) LOOPBACK else host
      val targetPort = if (host in feedHosts && requestedPort == 443) feedPort else requestedPort
      onEvent("CONNECT $host:$requestedPort -> $targetHost:$targetPort")
      try {
        Socket().use { upstream ->
          upstream.connect(InetSocketAddress(targetHost, targetPort), 20_000)
          it.getOutputStream().write("HTTP/1.1 200 Connection Established\r\n\r\n".toByteArray())
          val toUpstream = workers.submit { it.getInputStream().copyTo(upstream.getOutputStream()) }
          upstream.getInputStream().copyTo(it.getOutputStream())
          toUpstream.cancel(true)
        }
      } catch (e: IOException) {
        onEvent("CONNECT $host:$requestedPort failed: ${e.message}")
        runCatching { reject(it, 502, "Bad Gateway") }
      }
    }
  }

  private fun readHeaders(input: BufferedInputStream): String? {
    val bytes = ArrayList<Byte>()
    while (bytes.size < MAX_REQUEST_BYTES) {
      val next = input.read()
      if (next < 0) return null
      bytes += next.toByte()
      val size = bytes.size
      if (size >= 4 &&
          bytes[size - 4] == '\r'.code.toByte() &&
          bytes[size - 3] == '\n'.code.toByte() &&
          bytes[size - 2] == '\r'.code.toByte() &&
          bytes[size - 1] == '\n'.code.toByte()) {
        return bytes.toByteArray().toString(StandardCharsets.US_ASCII)
      }
    }
    return null
  }

  private fun reject(socket: Socket, status: Int, reason: String) {
    socket
        .getOutputStream()
        .write("HTTP/1.1 $status $reason\r\nConnection: close\r\n\r\n".toByteArray())
  }
}
