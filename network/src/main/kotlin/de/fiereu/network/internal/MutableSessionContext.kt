package de.fiereu.network.internal

import de.fiereu.network.Protocol
import de.fiereu.network.SessionAttribute
import de.fiereu.network.SessionAttributes
import de.fiereu.network.SessionContext
import de.fiereu.network.SessionPhase
import de.fiereu.network.Side
import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.util.AttributeKey
import java.net.SocketAddress
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

private val log = KotlinLogging.logger {}

internal val SESSION_KEY: AttributeKey<MutableSessionContext> =
    AttributeKey.valueOf("de.fiereu.network.session")

internal class MutableSessionContext(
    override val side: Side,
    override val channel: Channel,
    internal val applicationProtocol: Protocol,
    override val diagnosticsCaptureEnabled: Boolean = false,
    override val diagnosticsCaptureDir: String = "captures",
) : SessionContext {

  override val remoteAddress: SocketAddress
    get() = channel.remoteAddress()

  @Volatile
  override var phase: SessionPhase = SessionPhase.HANDSHAKE
    private set

  @Volatile
  override var handshakeCompletedAt: Instant? = null
    private set

  override val attributes: SessionAttributes = ConcurrentAttributes()

  private val listenersLock = Any()
  private val listeners = mutableMapOf<SessionPhase, MutableList<() -> Unit>>()

  internal fun transitionTo(next: SessionPhase) {
    val toFire: List<() -> Unit>
    synchronized(listenersLock) {
      if (phase == next) return
      phase = next
      if (next == SessionPhase.ESTABLISHED) {
        handshakeCompletedAt = Instant.now()
      }
      toFire = listeners[next]?.toList() ?: emptyList()
    }
    for (listener in toFire) {
      try {
        listener()
      } catch (t: Throwable) {
        log.warn(t) { "Phase listener for $next threw" }
      }
    }
  }

  override fun send(packet: Any): ChannelFuture = channel.writeAndFlush(packet)

  override fun close(reason: () -> String) {
    val message = reason()
    log.debug { "Closing session $remoteAddress: $message" }
    channel.close()
  }

  override fun onPhase(phase: SessionPhase, listener: () -> Unit) {
    val fireNow: Boolean
    synchronized(listenersLock) {
      if (this.phase == phase) {
        fireNow = true
      } else {
        fireNow = false
        listeners.getOrPut(phase) { mutableListOf() } += listener
      }
    }
    if (fireNow) {
      listener()
    }
  }
}

internal data class OutgoingPacket(
    val registration: Protocol.Registration<*>,
    val value: Any,
)

private class ConcurrentAttributes : SessionAttributes {
  private val map = ConcurrentHashMap<SessionAttribute<*>, Any>()

  @Suppress("UNCHECKED_CAST")
  override fun <T : Any> get(key: SessionAttribute<T>): T? = map[key] as T?

  override fun <T : Any> set(key: SessionAttribute<T>, value: T) {
    map[key] = value
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : Any> remove(key: SessionAttribute<T>): T? = map.remove(key) as T?

  @Suppress("UNCHECKED_CAST")
  override fun <T : Any> getOrPut(key: SessionAttribute<T>, default: () -> T): T =
      map.getOrPut(key) { default() } as T

  override fun contains(key: SessionAttribute<*>): Boolean = map.containsKey(key)
}
