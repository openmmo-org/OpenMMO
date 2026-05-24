package de.fiereu.bytecodec

interface ReadBuffer {
  fun readByte(): Byte

  fun readBytes(dst: ByteArray, offset: Int = 0, length: Int = dst.size - offset)

  fun skip(n: Int)

  fun remaining(): Int
}

interface WriteBuffer {
  fun writeByte(value: Byte)

  fun writeBytes(src: ByteArray, offset: Int = 0, length: Int = src.size - offset)
}

interface Buffer : ReadBuffer, WriteBuffer
