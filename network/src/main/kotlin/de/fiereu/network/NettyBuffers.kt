package de.fiereu.network

import de.fiereu.bytecodec.ReadBuffer
import de.fiereu.bytecodec.WriteBuffer
import io.netty.buffer.ByteBuf

class NettyReadBuffer(private val buf: ByteBuf) : ReadBuffer {
  override fun readByte(): Byte = buf.readByte()

  override fun readBytes(dst: ByteArray, offset: Int, length: Int) {
    buf.readBytes(dst, offset, length)
  }

  override fun skip(n: Int) {
    buf.skipBytes(n)
  }

  override fun remaining(): Int = buf.readableBytes()
}

class NettyWriteBuffer(private val buf: ByteBuf) : WriteBuffer {
  override fun writeByte(value: Byte) {
    buf.writeByte(value.toInt())
  }

  override fun writeBytes(src: ByteArray, offset: Int, length: Int) {
    buf.writeBytes(src, offset, length)
  }
}
