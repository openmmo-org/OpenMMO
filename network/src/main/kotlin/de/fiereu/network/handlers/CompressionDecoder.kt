package de.fiereu.network.handlers

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import java.util.zip.Inflater

class CompressionDecoder : ByteToMessageDecoder() {

  private val inflater = Inflater(true)

  override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>) {
    if (buffer.readableBytes() < 2) return
    val opcode = buffer.readByte()
    val flag = buffer.readByte().toInt()
    val payloadLen = buffer.readableBytes()
    val output = ctx.alloc().buffer(payloadLen * 2 + 1)
    try {
      output.writeByte(opcode.toInt())
      if (flag == 0) {
        output.writeBytes(buffer, buffer.readerIndex(), payloadLen)
        buffer.skipBytes(payloadLen)
      } else {
        val input = ByteArray(payloadLen)
        buffer.readBytes(input)
        inflater.reset()
        inflater.setInput(input)
        val chunk = ByteArray(0x4000)
        while (!inflater.finished() && !inflater.needsInput()) {
          val written = inflater.inflate(chunk)
          if (written == 0) break
          output.writeBytes(chunk, 0, written)
        }
        if (output.readableBytes() >= 2) {
          output.writerIndex(output.writerIndex() - 2)
        }
      }
      out += output.retain()
    } finally {
      output.release()
    }
  }

  override fun handlerRemoved0(ctx: ChannelHandlerContext) {
    inflater.end()
  }
}
