package de.fiereu.openmmo.launcher.client

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

/** A loopback http server for tests, with enough Range support to exercise resuming. */
class TestHttpServer(private val routes: Map<String, ByteArray>) {

  var requests: MutableList<String> = mutableListOf()
    private set

  /** Paths that answer 200 with the whole body even when a range was asked for. */
  var ignoreRange: Set<String> = emptySet()

  /** Paths that fail before serving anything. */
  var failing: Set<String> = emptySet()

  /** Fails this many requests before answering, to exercise retries. */
  var failFirst: Int = 0

  private val server: HttpServer =
      HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0).apply {
        createContext("/", ::handle)
        executor = null
      }

  val origin: String
    get() = "http://127.0.0.1:${server.address.port}"

  fun start() = server.start()

  fun stop() = server.stop(0)

  private fun handle(exchange: HttpExchange) {
    val path = exchange.requestURI.path
    val seen =
        synchronized(requests) {
          requests += path
          requests.size
        }
    val body = routes[path]
    exchange.use {
      if (seen <= failFirst) {
        it.sendResponseHeaders(503, -1)
        return@use
      }
      if (body == null || path in failing) {
        it.sendResponseHeaders(404, -1)
        return@use
      }

      val range = it.requestHeaders.getFirst("Range")
      val from =
          if (range != null && path !in ignoreRange) {
            range.removePrefix("bytes=").substringBefore('-').toLongOrNull() ?: 0
          } else {
            0
          }

      val slice = body.copyOfRange(from.toInt(), body.size)
      val status = if (from > 0 && path !in ignoreRange) 206 else 200
      if (status == 206) {
        it.responseHeaders.add("Content-Range", "bytes $from-${body.size - 1}/${body.size}")
      }
      it.sendResponseHeaders(status, slice.size.toLong())
      it.responseBody.write(slice)
    }
  }
}
