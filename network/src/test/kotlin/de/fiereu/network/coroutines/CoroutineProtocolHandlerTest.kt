package de.fiereu.network.coroutines

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.U16LE
import de.fiereu.network.HandlerRegistrationException
import de.fiereu.network.PipelineNames
import de.fiereu.network.Protocol
import de.fiereu.network.ProtocolHandler
import de.fiereu.network.Side
import de.fiereu.network.c2s
import de.fiereu.network.internal.MutableSessionContext
import de.fiereu.network.internal.SESSION_KEY
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

private data class Slow(val id: Int)

private data class Fast(val id: Int)

private object SlowCodec : PacketCodec<Slow>() {
  override fun CodecScope<Slow>.body() = Slow(field(U16LE, Slow::id))
}

private object FastCodec : PacketCodec<Fast>() {
  override fun CodecScope<Fast>.body() = Fast(field(U16LE, Fast::id))
}

private object MixedProtocol : Protocol() {
  init {
    c2s<Slow>(0xC0u, SlowCodec)
    c2s<Fast>(0xC1u, FastCodec)
  }
}

private fun channelWithHandler(handler: ProtocolHandler): EmbeddedChannel {
  val channel = EmbeddedChannel()
  val session = MutableSessionContext(handler.side, channel, handler.protocol)
  channel.attr(SESSION_KEY).set(session)
  channel.pipeline().addLast(PipelineNames.PROTOCOL_HANDLER, handler)
  return channel
}

class CoroutineProtocolHandlerTest :
    FunSpec({
      test("onSuspend handler runs on the supplied scope") {
        val scope = CoroutineScope(Dispatchers.Default + Job())
        val received = CompletableDeferred<Slow>()
        val handler =
            object : CoroutineProtocolHandler<MixedProtocol>(MixedProtocol, Side.SERVER, scope) {
              init {
                onSuspend<Slow> { event -> received.complete(event.packet) }
              }
            }
        val channel = channelWithHandler(handler)
        channel.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(0xC0.toByte(), 0x07, 0x00)))
        runBlocking { withTimeout(2000) { received.await() } shouldBe Slow(7) }
        scope.cancel()
      }

      test("sync on<T> and onSuspend coexist") {
        val scope = CoroutineScope(Dispatchers.Default + Job())
        val syncSeen = mutableListOf<Fast>()
        val asyncSeen = CompletableDeferred<Slow>()
        val handler =
            object : CoroutineProtocolHandler<MixedProtocol>(MixedProtocol, Side.SERVER, scope) {
              init {
                on<Fast> { event -> syncSeen += event.packet }
                onSuspend<Slow> { event -> asyncSeen.complete(event.packet) }
              }
            }
        val channel = channelWithHandler(handler)
        channel.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(0xC1.toByte(), 0x03, 0x00)))
        channel.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(0xC0.toByte(), 0x09, 0x00)))
        syncSeen shouldBe listOf(Fast(3))
        runBlocking { withTimeout(2000) { asyncSeen.await() } shouldBe Slow(9) }
        scope.cancel()
      }

      test("registering the same type twice across sync/suspend throws") {
        val scope = CoroutineScope(Dispatchers.Default + Job())
        shouldThrow<HandlerRegistrationException> {
          object : CoroutineProtocolHandler<MixedProtocol>(MixedProtocol, Side.SERVER, scope) {
            init {
              on<Slow> {}
              onSuspend<Slow> {}
            }
          }
        }
        scope.cancel()
      }

      test("mailbox preserves ordering") {
        val scope = CoroutineScope(Dispatchers.Default + Job())
        val seen = mutableListOf<Int>()
        val all = CompletableDeferred<Unit>()
        val handler =
            object : CoroutineProtocolHandler<MixedProtocol>(MixedProtocol, Side.SERVER, scope) {
              init {
                onSuspend<Slow> { event ->
                  seen += event.packet.id
                  if (seen.size == 5) all.complete(Unit)
                }
              }
            }
        val channel = channelWithHandler(handler)
        for (i in 1..5) {
          channel.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(0xC0.toByte(), i.toByte(), 0x00)))
        }
        runBlocking { withTimeout(2000) { all.await() } }
        seen shouldBe listOf(1, 2, 3, 4, 5)
        scope.cancel()
      }
    })
