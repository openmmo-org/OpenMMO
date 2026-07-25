package de.fiereu.openmmo.codegen.dialog

import java.io.File

/**
 * A retail GBA ROM located by its header game code, used to resolve a text's file offset. The ROM
 * is byte-identical to the pret decomp, so the offset of an encoded string is the value PokeMMO
 * packs into a dialog textId.
 */
class RomIndex private constructor(private val latin1: String) {

  /** The first offset of [bytes] in the ROM, or -1 if absent. */
  fun offsetOf(bytes: ByteArray): Int = latin1.indexOf(String(bytes, Charsets.ISO_8859_1))

  companion object {
    private const val GAME_CODE_OFFSET = 0xAC

    /** Finds the ROM in [romsDir] whose header game code equals [gameCode] (e.g. "BPEE"). */
    fun find(romsDir: File, gameCode: String): RomIndex? {
      val rom =
          romsDir
              .listFiles { f -> f.isFile }
              ?.firstOrNull { f ->
                f.length() > GAME_CODE_OFFSET + 4 && readGameCode(f) == gameCode
              } ?: return null
      return RomIndex(String(rom.readBytes(), Charsets.ISO_8859_1))
    }

    private fun readGameCode(file: File): String =
        file.inputStream().use { s ->
          s.skip(GAME_CODE_OFFSET.toLong())
          val b = ByteArray(4)
          if (s.read(b) != 4) "" else String(b, Charsets.US_ASCII)
        }
  }
}
