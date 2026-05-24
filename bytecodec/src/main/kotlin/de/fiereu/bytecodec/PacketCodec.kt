package de.fiereu.bytecodec

interface CodecScope<P> {
  fun <T> field(codec: Codec<T>, get: (P) -> T): T

  fun structural(codec: Codec<Unit>)
}

internal class ReadingScope<P>(private val buf: ReadBuffer) : CodecScope<P> {
  override fun <T> field(codec: Codec<T>, get: (P) -> T): T = codec.read(buf)

  override fun structural(codec: Codec<Unit>) {
    codec.read(buf)
  }
}

internal class WritingScope<P>(
    private val buf: WriteBuffer,
    private val value: P,
) : CodecScope<P> {
  override fun <T> field(codec: Codec<T>, get: (P) -> T): T {
    val v = get(value)
    codec.write(buf, v)
    return v
  }

  override fun structural(codec: Codec<Unit>) {
    codec.write(buf, Unit)
  }
}

abstract class PacketCodec<P> : Codec<P> {
  protected abstract fun CodecScope<P>.body(): P

  final override fun read(buf: ReadBuffer): P {
    val scope = ReadingScope<P>(buf)
    return with(scope) { body() }
  }

  final override fun write(buf: WriteBuffer, value: P) {
    val scope = WritingScope(buf, value)
    with(scope) { body() }
  }
}

fun CodecScope<*>.reserved(byte: Int = 0, strict: Boolean = false) {
  structural(de.fiereu.bytecodec.reserved(byte = byte, strict = strict))
}

fun CodecScope<*>.padding(n: Int, fill: Byte = 0) {
  structural(de.fiereu.bytecodec.padding(n = n, fill = fill))
}

fun CodecScope<*>.skip(n: Int) {
  structural(de.fiereu.bytecodec.skip(n = n))
}
