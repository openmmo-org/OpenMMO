package de.fiereu.openmmo.launcher.client

import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest

private const val BUFFER = 1 shl 16

fun sha256(bytes: ByteArray): String =
    MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }

fun sha256(path: Path): String {
  val digest = MessageDigest.getInstance("SHA-256")
  Files.newInputStream(path).use { stream ->
    val buffer = ByteArray(BUFFER)
    while (true) {
      val read = stream.read(buffer)
      if (read < 0) break
      digest.update(buffer, 0, read)
    }
  }
  return digest.digest().joinToString("") { "%02x".format(it) }
}
