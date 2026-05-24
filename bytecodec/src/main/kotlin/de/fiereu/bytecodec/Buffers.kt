package de.fiereu.bytecodec

import java.nio.ByteBuffer

class ByteArrayReadBuffer(
    private val array: ByteArray,
    private val offset: Int = 0,
    private val length: Int = array.size - offset,
) : ReadBuffer {
  init {
    require(offset >= 0) { "offset < 0" }
    require(length >= 0) { "length < 0" }
    require(offset + length <= array.size) { "offset + length > array.size" }
  }

  private var pos = 0

  override fun readByte(): Byte {
    if (pos >= length) throw IndexOutOfBoundsException("read past end")
    return array[offset + pos++]
  }

  override fun readBytes(dst: ByteArray, offset: Int, length: Int) {
    if (this.pos + length > this.length) throw IndexOutOfBoundsException("read past end")
    System.arraycopy(array, this.offset + pos, dst, offset, length)
    pos += length
  }

  override fun skip(n: Int) {
    if (n < 0) throw IndexOutOfBoundsException("negative skip")
    if (pos + n > length) throw IndexOutOfBoundsException("skip past end")
    pos += n
  }

  override fun remaining(): Int = length - pos
}

class GrowableWriteBuffer(initialCapacity: Int = 256) : WriteBuffer {
  private var buf: ByteArray = ByteArray(initialCapacity.coerceAtLeast(16))
  private var pos: Int = 0

  fun size(): Int = pos

  fun toByteArray(): ByteArray = buf.copyOf(pos)

  private fun ensure(extra: Int) {
    val needed = pos + extra
    if (needed <= buf.size) return
    var newSize = buf.size
    while (newSize < needed) newSize *= 2
    buf = buf.copyOf(newSize)
  }

  override fun writeByte(value: Byte) {
    ensure(1)
    buf[pos++] = value
  }

  override fun writeBytes(src: ByteArray, offset: Int, length: Int) {
    ensure(length)
    System.arraycopy(src, offset, buf, pos, length)
    pos += length
  }
}

class NioReadBuffer(private val nio: ByteBuffer) : ReadBuffer {
  override fun readByte(): Byte = nio.get()

  override fun readBytes(dst: ByteArray, offset: Int, length: Int) {
    nio.get(dst, offset, length)
  }

  override fun skip(n: Int) {
    nio.position(nio.position() + n)
  }

  override fun remaining(): Int = nio.remaining()
}

class NioWriteBuffer(private val nio: ByteBuffer) : WriteBuffer {
  override fun writeByte(value: Byte) {
    nio.put(value)
  }

  override fun writeBytes(src: ByteArray, offset: Int, length: Int) {
    nio.put(src, offset, length)
  }
}
