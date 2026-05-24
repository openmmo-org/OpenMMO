package de.fiereu.bytecodec.test

import de.fiereu.bytecodec.ByteArrayReadBuffer
import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.GrowableWriteBuffer

private fun contentEqual(a: Any?, b: Any?): Boolean =
    when {
      a is ByteArray && b is ByteArray -> a.contentEquals(b)
      a is Array<*> && b is Array<*> -> a.contentDeepEquals(b)
      else -> a == b
    }

fun <T> Codec<T>.encodeToBytes(value: T): ByteArray {
  val buf = GrowableWriteBuffer()
  write(buf, value)
  return buf.toByteArray()
}

fun <T> Codec<T>.decodeBytes(bytes: ByteArray): T {
  val buf = ByteArrayReadBuffer(bytes)
  val value = read(buf)
  check(buf.remaining() == 0) { "decode left ${buf.remaining()} bytes unread" }
  return value
}

fun <T> Codec<T>.assertValueRoundtrip(value: T) {
  val bytes = encodeToBytes(value)
  val decoded = decodeBytes(bytes)
  if (!contentEqual(decoded, value)) {
    throw AssertionError(
        "value roundtrip mismatch: original=$value decoded=$decoded bytes=${bytes.toHex()}",
    )
  }
}

fun <T> Codec<T>.assertBytesRoundtrip(bytes: ByteArray) {
  val value = decodeBytes(bytes)
  val reencoded = encodeToBytes(value)
  if (!reencoded.contentEquals(bytes)) {
    throw AssertionError(
        "bytes roundtrip mismatch: original=${bytes.toHex()} reencoded=${reencoded.toHex()} value=$value",
    )
  }
}

private fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it.toInt() and 0xFF) }
