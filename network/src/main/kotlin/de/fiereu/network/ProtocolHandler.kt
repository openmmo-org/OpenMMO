package de.fiereu.network

import de.fiereu.bytecodec.Codec
import de.fiereu.network.internal.DiagnosticsCaptureWriter
import de.fiereu.network.internal.OutgoingPacket
import de.fiereu.network.internal.SESSION_KEY
import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise

private val log = KotlinLogging.logger {}

abstract class ProtocolHandler(
    val protocol: Protocol,
    val side: Side,
) : ChannelDuplexHandler() {

  protected lateinit var session: SessionContext
    private set

  override fun handlerAdded(ctx: ChannelHandlerContext) {
    val attached =
        ctx.channel().attr(SESSION_KEY).get()
            ?: error("No SessionContext attached to channel ${ctx.channel()}")
    session = attached
  }

  override fun channelActive(ctx: ChannelHandlerContext) {
    try {
      onActive()
    } catch (t: Throwable) {
      onErrorInternal(ctx, t)
      return
    }
    ctx.fireChannelActive()
  }

  override fun channelInactive(ctx: ChannelHandlerContext) {
    try {
      onInactive()
    } finally {
      ctx.fireChannelInactive()
    }
  }

  override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
    if (msg !is ByteBuf) {
      ctx.fireChannelRead(msg)
      return
    }
    try {
      if (msg.readableBytes() < 1) throw EmptyFrameException()
      val opcode = (msg.readByte().toInt() and 0xFF).toUByte()
      if (side == Side.SERVER && session.diagnosticsCaptureEnabled) {
        val hex = DiagnosticsCaptureWriter.hexOf(msg, msg.readerIndex(), msg.readableBytes())
        DiagnosticsCaptureWriter.appendLine(
            session.diagnosticsCaptureDir,
            "server-c2s-capture.log",
            "${System.currentTimeMillis()} ${protocol::class.simpleName ?: "UNKNOWN"} C2S " +
                "id=${opcode.toInt()} len=${msg.readableBytes()} $hex",
        )
      }
      val registration = protocol.incomingRegistration(side, opcode)
      if (registration == null) {
        log.error { "No incoming codec for opcode 0x${opcode.toString(16)} on $side" }
        return
      }
      val packet =
          try {
            val decoded = decode(registration.codec, msg)
            val trailing = msg.readableBytes()
            if (trailing > 0) throw TrailingBytesException(opcode, trailing)
            decoded
          } catch (e: Exception) {
            // This frame's boundary was already fixed by the length-prefixed frame decoder
            // upstream, independent of anything below -- a codec choking on a packet whose
            // wire layout we've mismodeled (wrong opcode mapping, unimplemented variant, etc.)
            // cannot desync any OTHER packet's framing. Safe to skip this one packet rather
            // than tear down the whole session over it. Only `Exception`, not `Throwable`: an
            // `Error` (OOM, StackOverflow) still propagates and crashes loudly, as it should.
            log.warn(e) {
              "Failed to decode opcode 0x${opcode.toString(16)} on $side " +
                  "(${msg.readableBytes()} byte(s) remaining); skipping packet"
            }
            return
          }
      try {
        onPacket(PacketEvent(packet, session))
      } catch (t: Throwable) {
        onErrorInternal(ctx, t)
      }
    } catch (t: Throwable) {
      // Don't let a real Error (OOM, StackOverflow) get funneled into onErrorInternal just
      // because it propagated past the inner decode-only catch (which only catches
      // Exception, on purpose) -- it must still escape and crash loudly.
      if (t is Error) throw t
      onErrorInternal(ctx, t)
    } finally {
      msg.release()
    }
  }

  override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {
    val (registration, value) =
        when (msg) {
          is OutgoingPacket -> msg.registration to msg.value
          is ByteBuf -> {
            ctx.write(msg, promise)
            return
          }
          else -> {
            val reg =
                protocol.outgoingRegistration(side, msg::class)
                    ?: throw UnknownPacketTypeException(msg::class, side)
            reg to msg
          }
        }
    val buffer = ctx.alloc().buffer()
    var success = false
    try {
      buffer.writeByte(registration.opcode.toInt())
      encode(registration.codec, value, buffer)
      success = true
    } finally {
      if (!success) buffer.release()
    }
    ctx.write(buffer, promise)
  }

  override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
    // An Error rethrown from channelRead's decode-failure path arrives here via Netty's own
    // invoker (which catches Throwable escaping channelRead and redirects it to
    // exceptionCaught). Forward it past this handler instead of routing it into the same
    // graceful onError/session-close path as a real Exception -- letting it reach the
    // pipeline tail is what actually surfaces it instead of quietly treating it as recoverable.
    if (cause is Error) {
      ctx.fireExceptionCaught(cause)
      return
    }
    onErrorInternal(ctx, cause)
  }

  private fun onErrorInternal(ctx: ChannelHandlerContext, cause: Throwable) {
    try {
      onError(cause)
    } catch (t: Throwable) {
      log.error(t) { "onError handler threw; closing channel" }
      ctx.close()
    }
  }

  open fun onActive() {}

  abstract fun onPacket(event: PacketEvent<*>)

  open fun onInactive() {}

  open fun onError(cause: Throwable) {
    log.warn(cause) { "Handler error on ${session.remoteAddress}" }
    session.close { "Handler error: ${cause.message}" }
  }

  private fun <T : Any> decode(codec: Codec<T>, buf: ByteBuf): T = codec.read(NettyReadBuffer(buf))

  @Suppress("UNCHECKED_CAST")
  private fun encode(codec: Codec<*>, value: Any, buf: ByteBuf) {
    (codec as Codec<Any>).write(NettyWriteBuffer(buf), value)
  }
}
