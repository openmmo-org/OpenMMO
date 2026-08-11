@file:JvmName("Main")

package de.fiereu.openmmo.codegen.dialog

import java.io.File

fun main(args: Array<String>) {
  require(args.size >= 5) {
    "Usage: <output-dir> <templates-dir> <class-cache-dir> <data-dir> <region>... got ${args.toList()}"
  }
  val outputDir = File(args[0])
  val templatesDir = File(args[1])
  val classCacheDir = File(args[2])
  val dataDir = File(args[3])

  for (region in args.drop(4)) {
    val tableFile = DialogTable.file(dataDir, region)
    val lines =
        DialogTable.read(tableFile)
            ?: error("no dialog table at $tableFile, refresh it with :codegen:refreshDialogTable")
    println("[dialog] $region: ${lines.size} lines from $tableFile")
    DialogRenderer(region, templatesDir, outputDir, classCacheDir).render(lines)
  }
}
