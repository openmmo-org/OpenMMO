package de.fiereu.openmmo.server.game.testsupport

import de.fiereu.network.SessionAttribute
import de.fiereu.network.SessionAttributes
import de.fiereu.network.SessionContext
import de.fiereu.network.SessionPhase
import de.fiereu.network.Side
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.PlayerState
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.embedded.EmbeddedChannel
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.time.Instant

/** In-memory [SessionAttributes] for tests. */
class MapAttributes : SessionAttributes {
  private val map = HashMap<SessionAttribute<*>, Any>()

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

/** A [SessionContext] that records everything sent to it, for asserting propagation. */
class FakeSession(
    characterId: Long? = null,
    regionId: Int = 1,
    bankId: Int = 51,
    mapId: Int = 3,
    facing: Direction = Direction.DOWN,
) : SessionContext {
  val sent = mutableListOf<Any>()
  private val ch = EmbeddedChannel()
  override val attributes = MapAttributes()

  init {
    attributes[PLAYER_STATE] =
        PlayerState(
            userId = 0,
            characterId = characterId,
            regionId = regionId,
            bankId = bankId,
            mapId = mapId,
            facingDirection = facing,
        )
  }

  fun state(): PlayerState = attributes[PLAYER_STATE]!!

  override val side: Side = Side.SERVER
  override val channel: Channel
    get() = ch

  override val remoteAddress: SocketAddress = InetSocketAddress("127.0.0.1", 0)
  override val phase: SessionPhase = SessionPhase.ESTABLISHED
  override val handshakeCompletedAt: Instant? = null

  override fun send(packet: Any): ChannelFuture {
    sent += packet
    return ch.newSucceededFuture()
  }

  override fun close(reason: () -> String) {}

  override fun onPhase(phase: SessionPhase, listener: () -> Unit) {}
}
