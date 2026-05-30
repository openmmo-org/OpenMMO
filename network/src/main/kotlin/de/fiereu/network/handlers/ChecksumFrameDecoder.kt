package de.fiereu.network.handlers

import de.fiereu.network.ChecksumMismatchException
import de.fiereu.network.checksum.Checksum
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class ChecksumFrameDecoder(@Volatile var checksum: Checksum) : ByteToMessageDecoder() {

  override fun decode(ctx: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>) {
    val checksum = this.checksum
    val tagSize = checksum.size
    val total = buffer.readableBytes()
    if (tagSize == 0) {
      out += buffer.readRetainedSlice(total)
      return
    }
    if (total < tagSize) {
      throw ChecksumMismatchException()
    }
    val payloadSize = total - tagSize
    val expected = ByteArray(tagSize)
    buffer.getBytes(buffer.readerIndex() + payloadSize, expected)
    val payload = buffer.slice(buffer.readerIndex(), payloadSize)
    if (!checksum.verify(payload, expected)) {
      buffer.skipBytes(total)
      throw ChecksumMismatchException()
    }
    out += payload.retain()
    buffer.skipBytes(total)
  }
}
