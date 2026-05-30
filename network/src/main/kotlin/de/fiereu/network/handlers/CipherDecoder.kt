package de.fiereu.network.handlers

import de.fiereu.network.cipher.SessionCipher
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class CipherDecoder(@Volatile var cipher: SessionCipher) : ByteToMessageDecoder() {

  override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>) {
    val len = buffer.readableBytes()
    if (len == 0) return
    val output = ctx.alloc().buffer(len)
    try {
      cipher.decrypt(buffer, output)
      out += output.retain()
    } finally {
      output.release()
    }
  }
}
