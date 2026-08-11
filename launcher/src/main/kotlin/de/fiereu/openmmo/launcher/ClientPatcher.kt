package de.fiereu.openmmo.launcher

/**
 * Replaces bytes inside the PokeMMO executable.
 *
 * The file is patched in place, so a replacement has to fit inside what the pattern matched.
 */
class ClientPatcher(private val patches: List<Patch>) {

  /** Writes [replacement] [offset] bytes into every match of [pattern]. */
  class Patch(
      val name: String,
      val pattern: BytePattern,
      val replacement: ByteArray,
      val offset: Int = 0,
  ) {
    init {
      require(offset >= 0) { "$name: offset $offset starts before the match" }
      require(replacement.isNotEmpty()) { "$name: replaces nothing" }
      require(offset + replacement.size <= pattern.size) {
        "$name: writes ${replacement.size} bytes at offset $offset, " +
            "past the ${pattern.size} the pattern matched"
      }
    }

    companion object {
      /**
       * Matches a string literal.
       *
       * Strings carry their length in a separate field, so a replacement has to be exactly as long
       * as the original.
       */
      fun ofText(name: String, original: String, replacement: String): Patch {
        require(original.length == replacement.length) {
          "$name: replacement is ${replacement.length} chars but the original is ${original.length}"
        }
        return Patch(
            name, BytePattern.ofText(original), replacement.toByteArray(Charsets.ISO_8859_1))
      }
    }
  }

  /** Applies every patch to [bytes] in place and returns how often each one matched. */
  fun apply(bytes: ByteArray): Map<Patch, Int> = patches.associateWith { replaceAll(bytes, it) }

  private fun replaceAll(bytes: ByteArray, patch: Patch): Int {
    var count = 0
    var offset = 0
    while (offset <= bytes.size - patch.pattern.size) {
      if (patch.pattern.matchesAt(bytes, offset)) {
        patch.replacement.copyInto(bytes, offset + patch.offset)
        offset += patch.pattern.size
        count++
      } else {
        offset++
      }
    }
    return count
  }
}
