@file:JvmName("Main")

package de.fiereu.openmmo.codegen.typechart

import java.io.File

fun main(args: Array<String>) {
  require(args.size >= 4) {
    "Usage: <output-dir> <templates-dir> <class-cache-dir> <decomp-dir> got ${args.toList()}"
  }
  val outputDir = File(args[0])
  val templatesDir = File(args[1])
  val classCacheDir = File(args[2])
  val decompDir = File(args[3])

  println("[typechart] parsing from $decompDir")
  val matchups = TypeChartParser(decompDir).parseAll()
  println("[typechart] parsed ${matchups.size} matchups. writing to $outputDir")
  TypeChartRenderer(templatesDir, outputDir, classCacheDir).render(matchups)
  println("[typechart] done")
}
