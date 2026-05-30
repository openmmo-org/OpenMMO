package de.fiereu.network.checksum

import io.netty.buffer.ByteBuf
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class HmacSha256Checksum(
    override val size: Int,
    key: ByteArray,
) : Checksum {

  init {
    require(size in 4..32) { "HMAC-SHA256 size must be between 4 and 32 bytes" }
  }

  private val mac: Mac =
      Mac.getInstance("HmacSHA256").also { it.init(SecretKeySpec(key, "HmacSHA256")) }

  private var calculateRound = 0
  private var verifyRound = 0

  override fun calculate(data: ByteBuf): ByteArray {
    val bytes = ByteArray(data.readableBytes())
    data.getBytes(data.readerIndex(), bytes)
    mac.update(bytes)
    mac.update(roundBytes(calculateRound++))
    return mac.doFinal().copyOfRange(0, size)
  }

  override fun verify(data: ByteBuf, expected: ByteArray): Boolean {
    if (expected.size != size) return false
    val bytes = ByteArray(data.readableBytes())
    data.getBytes(data.readerIndex(), bytes)
    mac.update(bytes)
    mac.update(roundBytes(verifyRound++))
    val actual = mac.doFinal().copyOfRange(0, size)
    return MessageDigest.isEqual(actual, expected)
  }

  private fun roundBytes(value: Int): ByteArray =
      byteArrayOf(
          ((value ushr 24) and 0xFF).toByte(),
          ((value ushr 16) and 0xFF).toByte(),
          ((value ushr 8) and 0xFF).toByte(),
          (value and 0xFF).toByte(),
      )
}
