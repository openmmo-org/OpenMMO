package de.fiereu.openmmo.codegen.story

import java.io.File

/**
 * Pulls the flag and var names out of a GBA decomp. Only the names matter here, the numeric values
 * are the client's concern and are not needed to key the game agnostic story store. The FLAG_/VAR_
 * prefix match skips the range markers like TEMP_FLAGS_START and FLAGS_COUNT on its own.
 */
object FlagVarParser {

  fun flags(decompDir: File): List<String> =
      names(File(decompDir, "include/constants/flags.h"), "FLAG_")

  fun vars(decompDir: File): List<String> =
      names(File(decompDir, "include/constants/vars.h"), "VAR_")

  private fun names(file: File, prefix: String): List<String> {
    require(file.exists()) { "Missing ${file.path}" }
    val re = Regex("""^#define\s+($prefix\w+)\b""")
    return file.readLines().mapNotNull { re.find(it.trim())?.groupValues?.get(1) }.distinct()
  }
}
