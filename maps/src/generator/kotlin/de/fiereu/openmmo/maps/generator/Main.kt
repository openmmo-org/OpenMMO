@file:JvmName("Main")

package de.fiereu.openmmo.maps.generator

import java.io.File

fun main(args: Array<String>) {
  require(args.size == 4) {
    "Usage: <pokeemerald-dir> <output-dir> <templates-dir> <class-cache-dir> got ${args.toList()}"
  }
  val pokeemeraldDir = File(args[0])
  val outputDir = File(args[1])
  val templatesDir = File(args[2])
  val classCacheDir = File(args[3])

  println("[maps] parsing pokeemerald from $pokeemeraldDir")
  val maps = PokeemeraldParser(pokeemeraldDir).parseAll()
  println("[maps] parsed ${maps.size} maps. writing to $outputDir")
  MapsRenderer(templatesDir, outputDir, classCacheDir).render(maps)
  println("[maps] done")
}
