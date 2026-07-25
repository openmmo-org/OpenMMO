package de.fiereu.openmmo.codegen.dialog

import java.io.File

/** A `.string` text label found in the decomp: its label and the concatenated literal content. */
data class DecompText(val label: String, val content: String)

/**
 * Collects every `.string` text label from the overworld data (map and shared scripts). A label is
 * a text label when it is directly followed by one or more `.string` lines.
 */
class TextParser(private val decompDir: File) {
  private val labelLine = Regex("^(\\w+):+\\s*$")

  fun parseAll(): List<DecompText> {
    val roots = listOf(File(decompDir, "data/maps"), File(decompDir, "data/scripts"))
    val out = LinkedHashMap<String, String>()
    for (root in roots) {
      if (!root.isDirectory) continue
      root
          .walkTopDown()
          .filter { it.isFile && it.extension == "inc" }
          .forEach { file -> parseFile(file, out) }
    }
    return out.map { DecompText(it.key, it.value) }
  }

  private fun parseFile(file: File, out: MutableMap<String, String>) {
    var pending: String? = null
    var active: String? = null
    val buffer = StringBuilder()
    fun flush() {
      val label = active
      if (label != null && label !in out) out[label] = buffer.toString()
      active = null
      buffer.setLength(0)
    }
    for (raw in file.readLines()) {
      val line = raw.trim()
      val m = labelLine.matchEntire(line)
      when {
        m != null -> {
          flush()
          pending = m.groupValues[1]
        }
        line.startsWith(".string") -> {
          val label = active ?: pending ?: continue
          if (active == null) {
            active = label
            buffer.setLength(0)
          }
          buffer.append(stringLiteral(line))
        }
        else -> {
          flush()
          pending = null
        }
      }
    }
    flush()
  }

  private fun stringLiteral(line: String): String {
    val first = line.indexOf('"')
    val last = line.lastIndexOf('"')
    return if (first in 0 until last) line.substring(first + 1, last) else ""
  }
}
