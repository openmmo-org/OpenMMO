package de.fiereu.network

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.U16LE
import de.fiereu.network.internal.MutableSessionContext
import de.fiereu.network.internal.OutgoingPacket
import de.fiereu.network.internal.SESSION_KEY
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel

private data class Ping(val n: Int)

private data class Pong(val n: Int)

private object PingCodec : PacketCodec<Ping>() {
  override fun CodecScope<Ping>.body() = Ping(field(U16LE, Ping::n))
}

private object PongCodec : PacketCodec<Pong>() {
  override fun CodecScope<Pong>.body() = Pong(field(U16LE, Pong::n))
}

private object PingPongProtocol : Protocol() {
  init {
    c2s<Ping>(0xA0u, PingCodec)
    s2c<Pong>(0xA1u, PongCodec)
  }
}

private class SimulatedFatalError : Error("simulated OOM/StackOverflow-class failure")

private object ThrowingErrorCodec : PacketCodec<Ping>() {
  override fun CodecScope<Ping>.body(): Ping = throw SimulatedFatalError()
}

private object ErrorProneProtocol : Protocol() {
  init {
    c2s<Ping>(0xC0u, ThrowingErrorCodec)
  }
}

private class ServerHandler :
    TypedProtocolHandler<PingPongProtocol>(PingPongProtocol, Side.SERVER) {
  val seen = mutableListOf<Ping>()

  init {
    on<Ping> { event ->
      seen += event.packet
      event.session.send(Pong(event.packet.n + 1))
    }
  }
}

private fun channelWithHandler(
    handler: ProtocolHandler,
    diagnosticsCaptureEnabled: Boolean = false,
    diagnosticsCaptureDir: String = "captures",
): EmbeddedChannel {
  val channel = EmbeddedChannel()
  val session =
      MutableSessionContext(
          handler.side,
          channel,
          handler.protocol,
          diagnosticsCaptureEnabled = diagnosticsCaptureEnabled,
          diagnosticsCaptureDir = diagnosticsCaptureDir,
      )
  channel.attr(SESSION_KEY).set(session)
  channel.pipeline().addLast(PipelineNames.PROTOCOL_HANDLER, handler)
  return channel
}

class TypedProtocolHandlerTest :
    FunSpec({
      test("incoming bytes dispatch to on<T>") {
        val handler = ServerHandler()
        val channel = channelWithHandler(handler)
        channel.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(0xA0.toByte(), 0x05, 0x00)))
        handler.seen shouldBe listOf(Ping(5))
        val out = channel.readOutbound<ByteBuf>()
        out.readUnsignedByte().toInt() shouldBe 0xA1
        out.readUnsignedShortLE() shouldBe 6
      }

      test("session.send writes through the handler") {
        val handler = ServerHandler()
        val channel = channelWithHandler(handler)
        val session = channel.attr(SESSION_KEY).get()
        session.send(Pong(42))
        val out = channel.readOutbound<ByteBuf>()
        out.readUnsignedByte().toInt() shouldBe 0xA1
        out.readUnsignedShortLE() shouldBe 42
      }

      test("unknown opcode is logged but channel stays open") {
        val handler = ServerHandler()
        val channel = channelWithHandler(handler)
        channel.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(0xFE.toByte())))
        channel.isOpen shouldBe true
      }

      test("unhandled packet logs but keeps the channel open") {
        val protocol =
            object : Protocol() {
              init {
                c2s<Ping>(0xB0u, PingCodec)
                c2s<Pong>(0xB1u, PongCodec)
              }
            }
        class OnlyPingHandler : TypedProtocolHandler<Protocol>(protocol, Side.SERVER) {
          init {
            on<Ping> { /* no-op */ }
          }
        }
        val handler = OnlyPingHandler()
        val channel = channelWithHandler(handler)
        channel.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(0xB1.toByte(), 0x01, 0x00)))
        channel.isOpen shouldBe true
      }

      test("a malformed packet is skipped without closing the session or affecting later packets") {
        val handler = ServerHandler()
        val channel = channelWithHandler(handler)

        // Truncated Ping: U16LE needs 2 payload bytes, only 1 is present. The frame boundary
        // itself (this ByteBuf) is still correct -- the codec just can't make sense of its
        // contents. That must not be treated as connection-fatal.
        channel.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(0xA0.toByte(), 0x05)))
        channel.isOpen shouldBe true
        handler.seen shouldBe emptyList()

        // Trailing bytes: a well-formed Ping frame with one extra byte the codec doesn't
        // consume. Also just a decode-time mismatch, not a framing/cipher-level failure.
        channel.writeInbound(
            Unpooled.wrappedBuffer(byteArrayOf(0xA0.toByte(), 0x07, 0x00, 0xFF.toByte())))
        channel.isOpen shouldBe true
        handler.seen shouldBe emptyList()

        // A subsequent, well-formed packet must still decode and dispatch normally --
        // proving the earlier bad packets left no residue in the buffer/session state.
        channel.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(0xA0.toByte(), 0x09, 0x00)))
        handler.seen shouldBe listOf(Ping(9))
        channel.isOpen shouldBe true
      }

      test("an Error thrown while decoding escapes instead of being swallowed as a handler error") {
        class ErrorProneHandler :
            TypedProtocolHandler<ErrorProneProtocol>(ErrorProneProtocol, Side.SERVER) {
          init {
            on<Ping> {}
          }
        }
        val handler = ErrorProneHandler()
        val channel = channelWithHandler(handler)
        shouldThrow<SimulatedFatalError> {
          channel.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(0xC0.toByte(), 0x00)))
        }
      }

      test("C2S capture tap is off by default and does not write outside its configured dir") {
        val handler = ServerHandler()
        val channel = channelWithHandler(handler)
        val session = channel.attr(SESSION_KEY).get()
        session.diagnosticsCaptureEnabled shouldBe false
        // Sending traffic with the tap in its default (disabled) state must not throw or
        // otherwise misbehave -- this is the "off in production" guarantee.
        channel.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(0xA0.toByte(), 0x0A, 0x00)))
        handler.seen shouldBe listOf(Ping(10))
      }

      test("C2S capture tap writes real bytes to the configured dir when enabled") {
        val tempDir = kotlin.io.path.createTempDirectory("c2s-capture-test").toString()
        val handler = ServerHandler()
        val channel =
            channelWithHandler(
                handler, diagnosticsCaptureEnabled = true, diagnosticsCaptureDir = tempDir)
        channel.writeInbound(Unpooled.wrappedBuffer(byteArrayOf(0xA0.toByte(), 0x0B, 0x00)))
        handler.seen shouldBe listOf(Ping(11))
        val captureFile = java.io.File(tempDir, "server-c2s-capture.log")
        captureFile.exists() shouldBe true
        val line = captureFile.readText()
        line shouldContain "C2S id=160"
        line shouldContain "0b00"
      }

      test("duplicate handler registration throws") {
        shouldThrow<HandlerRegistrationException> {
          object : TypedProtocolHandler<PingPongProtocol>(PingPongProtocol, Side.SERVER) {
            init {
              on<Ping> {}
              on<Ping> {}
            }
          }
        }
      }

      test("attributes survive on a session") {
        val handler = ServerHandler()
        val channel = channelWithHandler(handler)
        val session = channel.attr(SESSION_KEY).get()
        val key = SessionAttribute.of<Long>("userId")
        session.attributes[key] = 42L
        session.attributes[key] shouldBe 42L
        session.attributes.contains(key) shouldBe true
      }

      test("OutgoingPacket goes through ProtocolHandler.write") {
        val handler = ServerHandler()
        val channel = channelWithHandler(handler)
        val reg = PingPongProtocol.outgoingRegistration(Side.SERVER, Pong::class)!!
        channel.writeOutbound(OutgoingPacket(reg, Pong(7)))
        val out = channel.readOutbound<ByteBuf>()
        out.readUnsignedByte().toInt() shouldBe 0xA1
        out.readUnsignedShortLE() shouldBe 7
      }
    })
