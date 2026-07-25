package de.fiereu.openmmo.codegen.script

import java.io.File

/**
 * Every executable script in the decomp keyed by its label, with the command lines that make up its
 * body. Scripts live in the map scripts and in the shared script files. A label ending in `::`
 * opens a script, a label ending in a single `:` opens data such as text and ends the current
 * script.
 */
class ScriptIndex private constructor(private val commands: Map<String, List<String>>) {

  fun commandsFor(label: String): List<String>? = commands[label]

  companion object {
    private val labelLine = Regex("^(\\w+)::?\\s*$")

    fun build(decompDir: File): ScriptIndex {
      val roots = listOf(File(decompDir, "data/maps"), File(decompDir, "data/scripts"))
      val out = LinkedHashMap<String, MutableList<String>>()
      for (root in roots) {
        if (!root.isDirectory) continue
        root
            .walkTopDown()
            .filter { it.isFile && it.extension == "inc" }
            .forEach { parseFile(it, out) }
      }
      return ScriptIndex(out)
    }

    private fun parseFile(file: File, out: MutableMap<String, MutableList<String>>) {
      // Labels stacked with no commands between them are aliases for the same body.
      val pending = mutableListOf<String>()
      for (raw in file.readLines()) {
        val line = raw.trim()
        val match = labelLine.matchEntire(line)
        if (match != null) {
          if (line.endsWith("::")) {
            val label = match.groupValues[1]
            out.getOrPut(label) { mutableListOf() }
            pending.add(label)
          } else {
            // A data label ends the current script body.
            pending.clear()
          }
          continue
        }
        if (line.isEmpty()) {
          // A blank line separates one script from the next.
          pending.clear()
          continue
        }
        if (line.startsWith("@")) continue
        for (label in pending) out.getValue(label).add(line)
      }
    }
  }
}
