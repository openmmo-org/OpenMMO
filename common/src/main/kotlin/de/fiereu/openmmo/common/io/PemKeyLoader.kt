package de.fiereu.openmmo.common.io

import java.io.InputStream
import java.security.KeyFactory
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

object PemKeyLoader {
  fun loadEcPublic(stream: InputStream): ECPublicKey {
    val der = readPem(stream)
    val spec = X509EncodedKeySpec(der)
    return KeyFactory.getInstance("EC").generatePublic(spec) as ECPublicKey
  }

  fun loadEcPrivate(stream: InputStream): ECPrivateKey {
    val der = readPem(stream)
    val spec = PKCS8EncodedKeySpec(der)
    return KeyFactory.getInstance("EC").generatePrivate(spec) as ECPrivateKey
  }

  private fun readPem(stream: InputStream): ByteArray {
    val text = stream.bufferedReader(Charsets.US_ASCII).use { it.readText() }
    val body =
        text
            .lineSequence()
            .map(String::trim)
            .filter { it.isNotEmpty() && !it.startsWith("-----") }
            .joinToString("")
    return Base64.getDecoder().decode(body)
  }
}

fun resource(name: String): InputStream =
    PemKeyLoader::class.java.classLoader.getResourceAsStream(name)
        ?: error("classpath resource not found: $name")
