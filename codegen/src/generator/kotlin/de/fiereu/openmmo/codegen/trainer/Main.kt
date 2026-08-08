@file:JvmName("Main")

package de.fiereu.openmmo.codegen.trainer

import java.io.File

fun main(args: Array<String>) {
  require(args.size >= 4) {
    "Usage: <output-dir> <templates-dir> <class-cache-dir> <region|decomp>... got ${args.toList()}"
  }
  val outputDir = File(args[0])
  val templatesDir = File(args[1])
  val classCacheDir = File(args[2])

  for (spec in args.drop(3)) {
    val (region, decomp) = spec.split("|")
    val trainers = TrainerParser(File(decomp)).parseAll()
    println("[trainer] $region: parsed ${trainers.size} trainers from $decomp")
    TrainerRenderer(region, templatesDir, outputDir, classCacheDir).render(trainers)
  }
  println("[trainer] done")
}
