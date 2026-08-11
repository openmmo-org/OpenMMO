package de.fiereu.openmmo.launcher

private const val WILDCARD = "??"

private val HEX_BYTE = Regex("[0-9A-Fa-f]{2}")

private val SEPARATOR = Regex("\\s+")

private fun tokenize(text: String): List<String> =
    text.trim().split(SEPARATOR).filter { it.isNotEmpty() }

private fun hexByte(token: String): Byte {
  require(HEX_BYTE.matches(token)) { "\"$token\" is not a hex byte" }
  return token.toInt(16).toByte()
}

/** Reads hex bytes, such as "B8 01 00 00 00". */
fun parseHexBytes(text: String): ByteArray {
  val tokens = tokenize(text)
  require(tokens.isNotEmpty()) { "\"$text\" holds no bytes" }
  return ByteArray(tokens.size) { hexByte(tokens[it]) }
}

/**
 * Bytes to search for, where a wildcard position matches any byte.
 *
 * Wildcards let a code signature survive a rebuild: the operands of a call or a jump move, the
 * opcodes around them do not.
 */
class BytePattern(private val bytes: ByteArray, private val wildcards: BooleanArray) {

  init {
    require(bytes.isNotEmpty()) { "A pattern with no bytes matches everywhere" }
    require(bytes.size == wildcards.size) {
      "${bytes.size} bytes carry ${wildcards.size} wildcard flags"
    }
  }

  val size: Int
    get() = bytes.size

  fun matchesAt(data: ByteArray, offset: Int): Boolean {
    for (i in bytes.indices) {
      if (!wildcards[i] && data[offset + i] != bytes[i]) return false
    }
    return true
  }

  companion object {

    /** Strings are stored as Latin-1, so a literal is matched byte for byte. */
    fun ofText(text: String): BytePattern {
      val bytes = text.toByteArray(Charsets.ISO_8859_1)
      return BytePattern(bytes, BooleanArray(bytes.size))
    }

    /** Reads hex bytes, where [WILDCARD] matches any byte. */
    fun parse(signature: String): BytePattern {
      val tokens = tokenize(signature)
      require(tokens.isNotEmpty()) { "\"$signature\" holds no bytes" }
      val bytes = ByteArray(tokens.size)
      val wildcards = BooleanArray(tokens.size)
      tokens.forEachIndexed { index, token ->
        if (token == WILDCARD) wildcards[index] = true else bytes[index] = hexByte(token)
      }
      return BytePattern(bytes, wildcards)
    }
  }
}
