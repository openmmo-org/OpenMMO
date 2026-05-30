package de.fiereu.network.handlers

import de.fiereu.network.checksum.Checksum
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class ChecksumFrameEncoder(@Volatile var checksum: Checksum) : MessageToByteEncoder<ByteBuf>() {

  override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf) {
    val checksum = this.checksum
    val len = msg.readableBytes()
    val tag = if (checksum.size > 0) checksum.calculate(msg) else null
    out.writeBytes(msg, msg.readerIndex(), len)
    if (tag != null) {
      out.writeBytes(tag)
    }
  }
}
