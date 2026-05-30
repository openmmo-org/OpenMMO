package de.fiereu.network

import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import java.net.SocketAddress
import java.time.Instant

interface SessionContext {
  val side: Side

  val channel: Channel

  val remoteAddress: SocketAddress

  val phase: SessionPhase

  val handshakeCompletedAt: Instant?

  val attributes: SessionAttributes

  fun send(packet: Any): ChannelFuture

  fun close(reason: () -> String = { "no reason" })

  fun onPhase(phase: SessionPhase, listener: () -> Unit)
}
