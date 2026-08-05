package de.fiereu.openmmo.codegen

import java.io.File

/** Reads a `#define <prefix><name> <number>` block, keyed by the full constant name. */
fun defineTable(file: File, prefix: String): Map<String, Int> {
  if (!file.exists()) return emptyMap()
  val regex = Regex("""^#define\s+($prefix\w+)\s+(\d+)\s*$""")
  return file
      .readLines()
      .mapNotNull { regex.find(it.trim()) }
      .associate { it.groupValues[1] to it.groupValues[2].toInt() }
}
