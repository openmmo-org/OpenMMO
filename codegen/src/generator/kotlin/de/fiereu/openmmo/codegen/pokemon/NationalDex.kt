package de.fiereu.openmmo.codegen.pokemon

import java.io.File

/**
 * Maps the decomp's internal species id to its national dex number. The two match through gen 1 but
 * diverge from gen 3 on. The client identifies a species by its national dex number, so all
 * generated data must use the national number rather than the internal one.
 */
object NationalDex {

  fun read(rootDir: File): Map<Int, Int> {
    val internalBySuffix = defineTable(File(rootDir, "include/constants/species.h"), "SPECIES_")
    val nationalBySuffix = enumTable(File(rootDir, "include/constants/pokedex.h"), "NATIONAL_DEX_")
    return internalBySuffix
        .mapNotNull { (suffix, internal) -> nationalBySuffix[suffix]?.let { internal to it } }
        .toMap()
  }

  // #define SPECIES_<suffix> <n>
  private fun defineTable(file: File, prefix: String): Map<String, Int> {
    if (!file.exists()) return emptyMap()
    val re = Regex("""^#define\s+$prefix(\w+)\s+(\d+)\s*$""")
    return file
        .readLines()
        .mapNotNull { re.find(it.trim()) }
        .associate { it.groupValues[1] to it.groupValues[2].toInt() }
  }

  // A plain enum where each NATIONAL_DEX_<suffix> takes the next value, starting at NONE = 0.
  private fun enumTable(file: File, prefix: String): Map<String, Int> {
    if (!file.exists()) return emptyMap()
    val re = Regex("""^$prefix(\w+)\s*(?:=\s*(\d+))?,?$""")
    val out = LinkedHashMap<String, Int>()
    var next = 0
    var inEnum = false
    for (raw in file.readLines()) {
      val line = raw.trim()
      if (line.startsWith("enum")) {
        inEnum = true
        continue
      }
      if (!inEnum) continue
      if (line.startsWith("}")) break
      val m = re.find(line) ?: continue
      val id = m.groupValues[2].takeIf { it.isNotEmpty() }?.toInt() ?: next
      out[m.groupValues[1]] = id
      next = id + 1
    }
    return out
  }
}
