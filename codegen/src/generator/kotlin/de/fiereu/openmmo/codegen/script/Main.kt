@file:JvmName("Main")

package de.fiereu.openmmo.codegen.script

import java.io.File

/**
 * One shot bootstrap of the overworld script stubs. Run by hand, the output is committed source and
 * is never regenerated on build, so hand written ports are safe. Args are the server.game kotlin
 * source root, the roms folder, then one `region|gameCode|decompDir` per region.
 */
fun main(args: Array<String>) {
  require(args.size >= 3) {
    "Usage: <server-game-kotlin-src> <roms-dir> <region|gameCode|decomp>... got ${args.toList()}"
  }
  val outputDir = File(args[0])
  val romsDir = File(args[1])

  val assigned = mutableSetOf<String>()
  val mapValues = mutableListOf<String>()
  for (spec in args.drop(2)) {
    val (region, gameCode, decomp) = spec.split("|")
    mapValues +=
        ScriptGenerator(region, outputDir).generate(File(decomp), romsDir, gameCode, assigned)
  }

  writeRegistry(outputDir, mapValues)
  println("[script] wrote registry over ${mapValues.size} maps, ${assigned.size} scripts")
}

private fun writeRegistry(outputDir: File, mapValues: List<String>) {
  val pkg = "de.fiereu.openmmo.server.game.script.generated"
  val chunks = mapValues.chunked(40)
  val sb = StringBuilder()
  sb.append("package ").append(pkg).append("\n\n")
  sb.append("import de.fiereu.openmmo.server.game.script.Script\n\n")
  sb.append("/**\n")
  sb.append(
      " * Every ported script keyed by its decomp label. Bootstrapped once from the decomp then\n")
  sb.append(
      " * edited by hand, so this file is committed source and is not regenerated on build.\n")
  sb.append(" */\n")
  sb.append("internal object GeneratedScripts {\n")
  sb.append("  val byLabel: Map<String, Script> =\n")
  sb.append("      HashMap<String, Script>().apply {\n")
  for (i in chunks.indices) sb.append("        chunk").append(i).append("()\n")
  sb.append("      }\n\n")
  for ((i, chunk) in chunks.withIndex()) {
    sb.append("  private fun MutableMap<String, Script>.chunk").append(i).append("() {\n")
    for (value in chunk) sb.append("    putAll(").append(value).append(")\n")
    sb.append("  }\n\n")
  }
  sb.append("}\n")

  val file = File(outputDir, pkg.replace('.', '/') + "/GeneratedScripts.kt")
  file.parentFile.mkdirs()
  file.writeText(sb.toString())
}
