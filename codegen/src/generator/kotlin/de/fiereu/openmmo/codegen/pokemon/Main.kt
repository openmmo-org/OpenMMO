@file:JvmName("Main")

package de.fiereu.openmmo.codegen.pokemon

import java.io.File

fun main(args: Array<String>) {
  require(args.size >= 4) {
    "Usage: <output-dir> <templates-dir> <class-cache-dir> <decomp-dir> got ${args.toList()}"
  }
  val outputDir = File(args[0])
  val templatesDir = File(args[1])
  val classCacheDir = File(args[2])
  val decompDir = File(args[3])

  println("[pokemon] parsing from $decompDir")
  val species = SpeciesParser(decompDir).parseAll()
  println("[pokemon] parsed ${species.size} species. writing to $outputDir")
  PokemonRenderer(templatesDir, outputDir, classCacheDir).render(species)
  println("[pokemon] done")
}
