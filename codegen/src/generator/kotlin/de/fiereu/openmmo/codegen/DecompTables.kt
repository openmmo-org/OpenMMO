package de.fiereu.openmmo.codegen

import java.io.File

/**
 * Reads a `#define <prefix><name> <number>` block, keyed by the full constant name. Emerald writes
 * its trainer classes in hex, FireRed in decimal.
 */
fun defineTable(file: File, prefix: String): Map<String, Int> {
  if (!file.exists()) return emptyMap()
  val regex = Regex("""^#define\s+($prefix\w+)\s+(0[xX][0-9a-fA-F]+|\d+)\s*(?://.*)?$""")
  return file
      .readLines()
      .mapNotNull { regex.find(it.trim()) }
      .associate { it.groupValues[1] to parseNumber(it.groupValues[2]) }
}

private fun parseNumber(text: String): Int =
    if (text.startsWith("0x", ignoreCase = true)) text.substring(2).toInt(16) else text.toInt()
