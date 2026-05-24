package de.fiereu.bytecodec

interface Codec<T> {
  fun read(buf: ReadBuffer): T

  fun write(buf: WriteBuffer, value: T)
}
