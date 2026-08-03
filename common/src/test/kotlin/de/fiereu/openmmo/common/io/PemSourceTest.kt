package de.fiereu.openmmo.common.io

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.security.KeyPairGenerator
import java.security.spec.ECGenParameterSpec
import java.util.Base64
import kotlin.io.path.createTempFile
import kotlin.io.path.writeText

private fun newKeyPem(): Pair<String, ByteArray> {
  val generator = KeyPairGenerator.getInstance("EC")
  generator.initialize(ECGenParameterSpec("secp256r1"))
  val key = generator.generateKeyPair().private
  val body = Base64.getMimeEncoder(64, "\n".toByteArray()).encodeToString(key.encoded)
  return "-----BEGIN EC PRIVATE KEY-----\n$body\n-----END EC PRIVATE KEY-----\n" to key.encoded
}

private fun pemFile(pem: String) = createTempFile(suffix = ".pem").also { it.writeText(pem) }

class PemSourceTest :
    FunSpec({
      test("reads the key from inline content") {
        val (pem, encoded) = newKeyPem()
        PemKeyLoader.loadEcPrivate(pemStream(pem, null, "missing.pem")).encoded shouldBe encoded
      }

      test("reads the key from a file") {
        val (pem, encoded) = newKeyPem()
        PemKeyLoader.loadEcPrivate(pemStream(null, pemFile(pem).toString(), "missing.pem"))
            .encoded shouldBe encoded
      }

      test("inline content wins over a file") {
        val (pem, encoded) = newKeyPem()
        val other = pemFile(newKeyPem().first)
        PemKeyLoader.loadEcPrivate(pemStream(pem, other.toString(), "missing.pem")).encoded shouldBe
            encoded
      }

      test("treats blank values as unset") {
        val (pem, encoded) = newKeyPem()
        PemKeyLoader.loadEcPrivate(pemStream("  ", pemFile(pem).toString(), "missing.pem"))
            .encoded shouldBe encoded
      }

      test("fails with a helpful message when nothing supplies a key") {
        val error = shouldThrow<IllegalStateException> { pemStream(null, null, "missing.pem") }
        error.message!!.contains("environment variable") shouldBe true
      }
    })
