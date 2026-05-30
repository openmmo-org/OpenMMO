package de.fiereu.bytecodec

object Bool : Codec<Boolean> {
  override fun read(buf: ReadBuffer): Boolean = buf.readByte() != 0.toByte()

  override fun write(buf: WriteBuffer, value: Boolean) {
    buf.writeByte(if (value) 1 else 0)
  }
}

object S8 : Codec<Byte> {
  override fun read(buf: ReadBuffer): Byte = buf.readByte()

  override fun write(buf: WriteBuffer, value: Byte) {
    buf.writeByte(value)
  }
}

object U8 : Codec<Int> {
  override fun read(buf: ReadBuffer): Int = buf.readByte().toInt() and 0xFF

  override fun write(buf: WriteBuffer, value: Int) {
    require(value in 0..0xFF) { "U8 out of range: $value" }
    buf.writeByte(value.toByte())
  }
}

object S16LE : Codec<Short> {
  override fun read(buf: ReadBuffer): Short {
    val b0 = buf.readByte().toInt() and 0xFF
    val b1 = buf.readByte().toInt() and 0xFF
    return ((b1 shl 8) or b0).toShort()
  }

  override fun write(buf: WriteBuffer, value: Short) {
    val v = value.toInt()
    buf.writeByte((v and 0xFF).toByte())
    buf.writeByte(((v ushr 8) and 0xFF).toByte())
  }
}

object S16BE : Codec<Short> {
  override fun read(buf: ReadBuffer): Short {
    val b0 = buf.readByte().toInt() and 0xFF
    val b1 = buf.readByte().toInt() and 0xFF
    return ((b0 shl 8) or b1).toShort()
  }

  override fun write(buf: WriteBuffer, value: Short) {
    val v = value.toInt()
    buf.writeByte(((v ushr 8) and 0xFF).toByte())
    buf.writeByte((v and 0xFF).toByte())
  }
}

object U16LE : Codec<Int> {
  override fun read(buf: ReadBuffer): Int {
    val b0 = buf.readByte().toInt() and 0xFF
    val b1 = buf.readByte().toInt() and 0xFF
    return (b1 shl 8) or b0
  }

  override fun write(buf: WriteBuffer, value: Int) {
    require(value in 0..0xFFFF) { "U16 out of range: $value" }
    buf.writeByte((value and 0xFF).toByte())
    buf.writeByte(((value ushr 8) and 0xFF).toByte())
  }
}

object U16BE : Codec<Int> {
  override fun read(buf: ReadBuffer): Int {
    val b0 = buf.readByte().toInt() and 0xFF
    val b1 = buf.readByte().toInt() and 0xFF
    return (b0 shl 8) or b1
  }

  override fun write(buf: WriteBuffer, value: Int) {
    require(value in 0..0xFFFF) { "U16 out of range: $value" }
    buf.writeByte(((value ushr 8) and 0xFF).toByte())
    buf.writeByte((value and 0xFF).toByte())
  }
}

object S32LE : Codec<Int> {
  override fun read(buf: ReadBuffer): Int {
    val b0 = buf.readByte().toInt() and 0xFF
    val b1 = buf.readByte().toInt() and 0xFF
    val b2 = buf.readByte().toInt() and 0xFF
    val b3 = buf.readByte().toInt() and 0xFF
    return (b3 shl 24) or (b2 shl 16) or (b1 shl 8) or b0
  }

  override fun write(buf: WriteBuffer, value: Int) {
    buf.writeByte((value and 0xFF).toByte())
    buf.writeByte(((value ushr 8) and 0xFF).toByte())
    buf.writeByte(((value ushr 16) and 0xFF).toByte())
    buf.writeByte(((value ushr 24) and 0xFF).toByte())
  }
}

object S32BE : Codec<Int> {
  override fun read(buf: ReadBuffer): Int {
    val b0 = buf.readByte().toInt() and 0xFF
    val b1 = buf.readByte().toInt() and 0xFF
    val b2 = buf.readByte().toInt() and 0xFF
    val b3 = buf.readByte().toInt() and 0xFF
    return (b0 shl 24) or (b1 shl 16) or (b2 shl 8) or b3
  }

  override fun write(buf: WriteBuffer, value: Int) {
    buf.writeByte(((value ushr 24) and 0xFF).toByte())
    buf.writeByte(((value ushr 16) and 0xFF).toByte())
    buf.writeByte(((value ushr 8) and 0xFF).toByte())
    buf.writeByte((value and 0xFF).toByte())
  }
}

object U32LE : Codec<Long> {
  override fun read(buf: ReadBuffer): Long {
    val b0 = buf.readByte().toLong() and 0xFF
    val b1 = buf.readByte().toLong() and 0xFF
    val b2 = buf.readByte().toLong() and 0xFF
    val b3 = buf.readByte().toLong() and 0xFF
    return (b3 shl 24) or (b2 shl 16) or (b1 shl 8) or b0
  }

  override fun write(buf: WriteBuffer, value: Long) {
    require(value in 0L..0xFFFFFFFFL) { "U32 out of range: $value" }
    buf.writeByte((value and 0xFF).toByte())
    buf.writeByte(((value ushr 8) and 0xFF).toByte())
    buf.writeByte(((value ushr 16) and 0xFF).toByte())
    buf.writeByte(((value ushr 24) and 0xFF).toByte())
  }
}

object U32BE : Codec<Long> {
  override fun read(buf: ReadBuffer): Long {
    val b0 = buf.readByte().toLong() and 0xFF
    val b1 = buf.readByte().toLong() and 0xFF
    val b2 = buf.readByte().toLong() and 0xFF
    val b3 = buf.readByte().toLong() and 0xFF
    return (b0 shl 24) or (b1 shl 16) or (b2 shl 8) or b3
  }

  override fun write(buf: WriteBuffer, value: Long) {
    require(value in 0L..0xFFFFFFFFL) { "U32 out of range: $value" }
    buf.writeByte(((value ushr 24) and 0xFF).toByte())
    buf.writeByte(((value ushr 16) and 0xFF).toByte())
    buf.writeByte(((value ushr 8) and 0xFF).toByte())
    buf.writeByte((value and 0xFF).toByte())
  }
}

object S64LE : Codec<Long> {
  override fun read(buf: ReadBuffer): Long {
    var result = 0L
    var shift = 0
    repeat(8) {
      result = result or ((buf.readByte().toLong() and 0xFF) shl shift)
      shift += 8
    }
    return result
  }

  override fun write(buf: WriteBuffer, value: Long) {
    var v = value
    repeat(8) {
      buf.writeByte((v and 0xFF).toByte())
      v = v ushr 8
    }
  }
}

object S64BE : Codec<Long> {
  override fun read(buf: ReadBuffer): Long {
    var result = 0L
    repeat(8) { result = (result shl 8) or (buf.readByte().toLong() and 0xFF) }
    return result
  }

  override fun write(buf: WriteBuffer, value: Long) {
    var shift = 56
    repeat(8) {
      buf.writeByte(((value ushr shift) and 0xFF).toByte())
      shift -= 8
    }
  }
}

object F32LE : Codec<Float> {
  override fun read(buf: ReadBuffer): Float = Float.fromBits(S32LE.read(buf))

  override fun write(buf: WriteBuffer, value: Float) {
    S32LE.write(buf, value.toRawBits())
  }
}

object F32BE : Codec<Float> {
  override fun read(buf: ReadBuffer): Float = Float.fromBits(S32BE.read(buf))

  override fun write(buf: WriteBuffer, value: Float) {
    S32BE.write(buf, value.toRawBits())
  }
}

object F64LE : Codec<Double> {
  override fun read(buf: ReadBuffer): Double = Double.fromBits(S64LE.read(buf))

  override fun write(buf: WriteBuffer, value: Double) {
    S64LE.write(buf, value.toRawBits())
  }
}

object F64BE : Codec<Double> {
  override fun read(buf: ReadBuffer): Double = Double.fromBits(S64BE.read(buf))

  override fun write(buf: WriteBuffer, value: Double) {
    S64BE.write(buf, value.toRawBits())
  }
}

fun fixedBytes(n: Int): Codec<ByteArray> {
  require(n >= 0) { "fixedBytes negative size: $n" }
  return object : Codec<ByteArray> {
    override fun read(buf: ReadBuffer): ByteArray {
      val arr = ByteArray(n)
      if (n > 0) buf.readBytes(arr)
      return arr
    }

    override fun write(buf: WriteBuffer, value: ByteArray) {
      if (value.size != n) {
        throw MalformedPacketException("fixedBytes($n) got ${value.size}")
      }
      if (n > 0) buf.writeBytes(value)
    }
  }
}

fun unknownBytes(n: Int): Codec<ByteArray> = fixedBytes(n)

fun reserved(byte: Int, strict: Boolean = false): Codec<Unit> {
  val literal = (byte and 0xFF).toByte()
  return object : Codec<Unit> {
    override fun read(buf: ReadBuffer) {
      val read = buf.readByte()
      if (strict && read != literal) {
        throw ReservedByteMismatchException(byte and 0xFF, read.toInt() and 0xFF)
      }
    }

    override fun write(buf: WriteBuffer, value: Unit) {
      buf.writeByte(literal)
    }
  }
}

fun padding(n: Int, fill: Byte = 0): Codec<Unit> {
  require(n >= 0) { "padding negative size: $n" }
  return object : Codec<Unit> {
    override fun read(buf: ReadBuffer) {
      if (n > 0) buf.skip(n)
    }

    override fun write(buf: WriteBuffer, value: Unit) {
      repeat(n) { buf.writeByte(fill) }
    }
  }
}

fun skip(n: Int): Codec<Unit> = padding(n)

object Utf16LeNullTerminated : Codec<String> {
  override fun read(buf: ReadBuffer): String {
    val out = java.io.ByteArrayOutputStream()
    while (true) {
      val lo = buf.readByte()
      val hi = buf.readByte()
      if (lo == 0.toByte() && hi == 0.toByte()) break
      out.write(lo.toInt() and 0xFF)
      out.write(hi.toInt() and 0xFF)
    }
    return String(out.toByteArray(), Charsets.UTF_16LE)
  }

  override fun write(buf: WriteBuffer, value: String) {
    buf.writeBytes(value.toByteArray(Charsets.UTF_16LE))
    buf.writeByte(0)
    buf.writeByte(0)
  }
}

object Utf16BeNullTerminated : Codec<String> {
  override fun read(buf: ReadBuffer): String {
    val out = java.io.ByteArrayOutputStream()
    while (true) {
      val hi = buf.readByte()
      val lo = buf.readByte()
      if (hi == 0.toByte() && lo == 0.toByte()) break
      out.write(hi.toInt() and 0xFF)
      out.write(lo.toInt() and 0xFF)
    }
    return String(out.toByteArray(), Charsets.UTF_16BE)
  }

  override fun write(buf: WriteBuffer, value: String) {
    buf.writeBytes(value.toByteArray(Charsets.UTF_16BE))
    buf.writeByte(0)
    buf.writeByte(0)
  }
}

object AsciiNullTerminated : Codec<String> {
  override fun read(buf: ReadBuffer): String {
    val out = java.io.ByteArrayOutputStream()
    while (true) {
      val b = buf.readByte()
      if (b == 0.toByte()) break
      out.write(b.toInt() and 0xFF)
    }
    return String(out.toByteArray(), Charsets.US_ASCII)
  }

  override fun write(buf: WriteBuffer, value: String) {
    buf.writeBytes(value.toByteArray(Charsets.US_ASCII))
    buf.writeByte(0)
  }
}
