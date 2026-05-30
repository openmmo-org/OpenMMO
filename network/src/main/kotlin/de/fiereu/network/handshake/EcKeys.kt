package de.fiereu.network.handshake

import java.math.BigInteger
import java.security.AlgorithmParameters
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.Signature
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey
import java.security.spec.ECGenParameterSpec
import java.security.spec.ECParameterSpec
import java.security.spec.ECPoint
import java.security.spec.ECPublicKeySpec

internal object EcKeys {
  private const val UNCOMPRESSED_INDICATOR: Byte = 0x04
  private const val CURVE = "secp256r1"
  private val keyFactory = KeyFactory.getInstance("EC")
  private val parameters: ECParameterSpec by lazy {
    val params = AlgorithmParameters.getInstance("EC")
    params.init(ECGenParameterSpec(CURVE))
    params.getParameterSpec(ECParameterSpec::class.java)
  }
  private val keyByteLength: Int by lazy {
    (parameters.order.bitLength() + Byte.SIZE_BITS - 1) / Byte.SIZE_BITS
  }

  fun generateEphemeralKeyPair(): KeyPair {
    val gen = KeyPairGenerator.getInstance("EC")
    gen.initialize(ECGenParameterSpec(CURVE))
    return gen.generateKeyPair()
  }

  fun toUncompressedPoint(key: ECPublicKey): ByteArray {
    val point = key.w
    val keyLength = keyByteLength
    val data = ByteArray(1 + 2 * keyLength)
    data[0] = UNCOMPRESSED_INDICATOR
    writeFieldInto(data, 1, point.affineX.toByteArray(), keyLength)
    writeFieldInto(data, 1 + keyLength, point.affineY.toByteArray(), keyLength)
    return data
  }

  fun fromUncompressedPoint(bytes: ByteArray): ECPublicKey {
    require(bytes.isNotEmpty() && bytes[0] == UNCOMPRESSED_INDICATOR) {
      "Invalid uncompressed point indicator"
    }
    val keyLength = keyByteLength
    require(bytes.size == 1 + 2 * keyLength) { "Invalid EC point length: ${bytes.size}" }
    val x = BigInteger(1, bytes.copyOfRange(1, 1 + keyLength))
    val y = BigInteger(1, bytes.copyOfRange(1 + keyLength, 1 + 2 * keyLength))
    return keyFactory.generatePublic(ECPublicKeySpec(ECPoint(x, y), parameters)) as ECPublicKey
  }

  private fun writeFieldInto(dst: ByteArray, offset: Int, src: ByteArray, length: Int) {
    when {
      src.size <= length -> System.arraycopy(src, 0, dst, offset + length - src.size, src.size)
      src.size == length + 1 && src[0] == 0.toByte() ->
          System.arraycopy(src, 1, dst, offset, length)
      else -> error("EC coordinate too large: ${src.size}")
    }
  }

  fun sign(privateKey: ECPrivateKey, data: ByteArray): ByteArray {
    val sig = Signature.getInstance("SHA256withECDSA")
    sig.initSign(privateKey)
    sig.update(data)
    return sig.sign()
  }

  fun verify(publicKey: ECPublicKey, data: ByteArray, signature: ByteArray): Boolean {
    val sig = Signature.getInstance("SHA256withECDSA")
    sig.initVerify(publicKey)
    sig.update(data)
    return sig.verify(signature)
  }
}
