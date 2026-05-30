package de.fiereu.network.cipher

import io.netty.buffer.ByteBuf
import java.security.MessageDigest
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher
import javax.crypto.KeyAgreement
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AesCtrSessionCipher
private constructor(
    private val encryptCipher: Cipher,
    private val decryptCipher: Cipher,
) : SessionCipher {

  enum class Role {
    SERVER,
    CLIENT
  }

  override fun encrypt(input: ByteBuf, output: ByteBuf) {
    apply(encryptCipher, input, output)
  }

  override fun decrypt(input: ByteBuf, output: ByteBuf) {
    apply(decryptCipher, input, output)
  }

  private fun apply(cipher: Cipher, input: ByteBuf, output: ByteBuf) {
    val len = input.readableBytes()
    if (len == 0) return
    val src = ByteArray(len)
    input.readBytes(src)
    val dst = cipher.update(src) ?: return
    if (dst.isNotEmpty()) {
      output.writeBytes(dst)
    }
  }

  companion object {
    private val IV_SALT = "IVDERIV".toByteArray(Charsets.US_ASCII)
    private val CLIENT_KEY_SALT = "KeySalt".toByteArray(Charsets.US_ASCII) + byteArrayOf(1)
    private val SERVER_KEY_SALT = "KeySalt".toByteArray(Charsets.US_ASCII) + byteArrayOf(2)

    fun ecdh(localPrivate: PrivateKey, remotePublic: PublicKey): ByteArray {
      val agreement = KeyAgreement.getInstance("ECDH")
      agreement.init(localPrivate)
      agreement.doPhase(remotePublic, true)
      return agreement.generateSecret()
    }

    fun derive(secret: ByteArray, role: Role): AesCtrSessionCipher {
      val (outSeed, inSeed) = directionalSeeds(secret, role)
      return AesCtrSessionCipher(
          encryptCipher = createCipher(Cipher.ENCRYPT_MODE, outSeed),
          decryptCipher = createCipher(Cipher.DECRYPT_MODE, inSeed),
      )
    }

    internal fun directionalSeeds(secret: ByteArray, role: Role): Pair<ByteArray, ByteArray> {
      val clientSeed = tripleHash(secret, CLIENT_KEY_SALT)
      val serverSeed = tripleHash(secret, SERVER_KEY_SALT)
      return when (role) {
        Role.SERVER -> serverSeed to clientSeed
        Role.CLIENT -> clientSeed to serverSeed
      }
    }

    fun derive(
        localPrivate: PrivateKey,
        remotePublic: PublicKey,
        role: Role,
    ): AesCtrSessionCipher = derive(ecdh(localPrivate, remotePublic), role)

    internal fun tripleHash(secret: ByteArray, salt: ByteArray): ByteArray {
      val sha = MessageDigest.getInstance("SHA-256")
      sha.update(salt)
      sha.update(secret)
      sha.update(salt)
      return sha.digest().copyOfRange(0, 16)
    }

    private fun createCipher(mode: Int, seed: ByteArray): Cipher {
      val cipher = Cipher.getInstance("AES/CTR/NoPadding")
      val key = SecretKeySpec(seed, "AES")
      val iv = IvParameterSpec(tripleHash(seed, IV_SALT))
      cipher.init(mode, key, iv)
      return cipher
    }
  }
}
