package de.fiereu.openmmo.common.utils

/**
 * Decodes a hex string into the bytes it spells, for example `"0c2e"` into `[0x0c, 0x2e]`.
 *
 * @throws IllegalArgumentException if the length is odd or a character is not a hex digit.
 */
fun String.hexToBytes(): ByteArray {
  require(length % 2 == 0) { "hex string must have an even length, got $length" }
  return ByteArray(length / 2) {
    val index = it * 2
    val high = Character.digit(this[index], 16)
    val low = Character.digit(this[index + 1], 16)
    require(high >= 0 && low >= 0) { "invalid hex digit at index $index" }
    ((high shl 4) or low).toByte()
  }
}

/** Encodes the bytes as a lowercase hex string without separators, for example `"0c2e"`. */
fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }
