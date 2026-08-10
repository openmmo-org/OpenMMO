package de.fiereu.openmmo.launcher

/**
 * Replaces string constants inside the PokeMMO executable.
 *
 * The image heap stores strings as Latin-1 bytes with a separate length field, so a replacement has
 * to be exactly as long as the original.
 */
class ClientPatcher(private val patches: List<Patch>) {

  data class Patch(val name: String, val original: String, val replacement: String) {
    init {
      require(original.length == replacement.length) {
        "$name: replacement is ${replacement.length} chars but the original is ${original.length}"
      }
    }
  }

  /** Applies every patch to [bytes] in place and returns how often each one matched. */
  fun apply(bytes: ByteArray): Map<Patch, Int> = patches.associateWith { replaceAll(bytes, it) }

  private fun replaceAll(bytes: ByteArray, patch: Patch): Int {
    val pattern = patch.original.toByteArray(Charsets.ISO_8859_1)
    val replacement = patch.replacement.toByteArray(Charsets.ISO_8859_1)
    var count = 0
    var offset = 0
    while (offset <= bytes.size - pattern.size) {
      if (matchesAt(bytes, offset, pattern)) {
        replacement.copyInto(bytes, offset)
        offset += pattern.size
        count++
      } else {
        offset++
      }
    }
    return count
  }

  private fun matchesAt(bytes: ByteArray, offset: Int, pattern: ByteArray): Boolean {
    for (i in pattern.indices) if (bytes[offset + i] != pattern[i]) return false
    return true
  }
}
