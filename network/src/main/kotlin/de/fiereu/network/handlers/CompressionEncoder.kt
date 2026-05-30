package de.fiereu.network.handlers

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import java.util.zip.Deflater

class CompressionEncoder(private val threshold: Int = 256) : MessageToByteEncoder<ByteBuf>() {

  private val deflater = Deflater(Deflater.DEFAULT_COMPRESSION, true)

  override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf) {
    if (msg.readableBytes() < 1) return
    val opcode = msg.readByte()
    out.writeByte(opcode.toInt())
    val payloadLen = msg.readableBytes()
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
      if (written == 0) break
      out.writeBytes(chunk, 0, written)
    }
  }

  override fun handlerRemoved(ctx: ChannelHandlerContext) {
    deflater.end()
  }
}
