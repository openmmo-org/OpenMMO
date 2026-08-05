@file:JvmName("Main")

package de.fiereu.openmmo.codegen.learnset

import java.io.File

fun main(args: Array<String>) {
  require(args.size >= 4) {
    "Usage: <output-dir> <templates-dir> <class-cache-dir> <decomp-dir> got ${args.toList()}"
  }
  val outputDir = File(args[0])
  val templatesDir = File(args[1])
  val classCacheDir = File(args[2])
  val decompDir = File(args[3])

  println("[learnset] parsing from $decompDir")
  val learnsets = LearnsetParser(decompDir).parseAll()
  println("[learnset] parsed ${learnsets.size} learnsets. writing to $outputDir")
  LearnsetRenderer(templatesDir, outputDir, classCacheDir).render(learnsets)
  println("[learnset] done")
}
