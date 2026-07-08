package de.fiereu.network.handlers

import de.fiereu.network.internal.DiagnosticsCaptureWriter
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import java.util.zip.Deflater

/**
 * `protocolLabel` (e.g. "GAME", "LOGIN", "CHAT") identifies which protocol this encoder instance
 * serves, purely for the S2C capture tap below -- has no effect on wire behavior.
 *
 * `captureEnabled`/`captureDir` gate and configure the dev-diagnostics S2C capture tap -- see
 * [de.fiereu.network.PipelineOptions.diagnosticsCaptureEnabled].
 */
class CompressionEncoder(
    private val threshold: Int = 256,
    private val protocolLabel: String = "UNKNOWN",
    private val captureEnabled: Boolean = false,
    private val captureDir: String = "captures",
) : MessageToByteEncoder<ByteBuf>() {

  private val deflater = Deflater(Deflater.DEFAULT_COMPRESSION, true)

  override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf) {
    if (msg.readableBytes() < 1) return
    val opcode = msg.readByte()
    out.writeByte(opcode.toInt())
    val payloadLen = msg.readableBytes()
    if (captureEnabled) {
      val payloadHex = DiagnosticsCaptureWriter.hexOf(msg, msg.readerIndex(), payloadLen)
      DiagnosticsCaptureWriter.appendLine(
          captureDir,
          "server-s2c-capture.log",
          "${System.currentTimeMillis()} $protocolLabel S2C id=${opcode.toInt() and 0xFF} " +
              "len=$payloadLen $payloadHex",
      )
    }
    if (payloadLen < threshold) {
      out.writeByte(0)
      out.writeBytes(msg, msg.readerIndex(), payloadLen)
      msg.skipBytes(payloadLen)
      return
    }
    val input = ByteArray(payloadLen + 2)
    msg.getBytes(msg.readerIndex(), input, 0, payloadLen)
    input[payloadLen] = 0xFF.toByte()
    input[payloadLen + 1] = 0xFF.toByte()
    msg.skipBytes(payloadLen)
    out.writeByte(1)
    deflater.reset()
    deflater.setInput(input)
    val chunk = ByteArray(0x4000)
    while (true) {
      val written = deflater.deflate(chunk, 0, chunk.size, Deflater.SYNC_FLUSH)
      out.writeBytes(chunk, 0, written)
      // Per Deflater.deflate's contract, a full buffer means more output may still be
      // pending -- only a short (or empty) write means this flush is fully drained.
      if (written < chunk.size) break
    }
  }

  override fun handlerRemoved(ctx: ChannelHandlerContext) {
    deflater.end()
  }
}
