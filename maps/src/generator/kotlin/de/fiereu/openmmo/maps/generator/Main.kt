@file:JvmName("Main")

package de.fiereu.openmmo.maps.generator

import java.io.File

private data class RegionSource(val region: RegionConstants, val dir: File)

private fun parseRegionSource(spec: String): RegionSource {
  val parts = spec.split("|")
  require(parts.size == 2) { "Invalid region spec '$spec', expected <region>|<dir>" }
  val region = REGIONS[parts[0]] ?: error("Unknown region '${parts[0]}', known: ${REGIONS.keys}")
  return RegionSource(region = region, dir = File(parts[1]))
}

fun main(args: Array<String>) {
  require(args.size >= 4) {
    "Usage: <output-dir> <templates-dir> <class-cache-dir> <region|dir>... got ${args.toList()}"
  }
  val outputDir = File(args[0])
  val templatesDir = File(args[1])
  val classCacheDir = File(args[2])
  val sources = args.drop(3).map(::parseRegionSource)

  val maps =
      sources.flatMap { (region, dir) ->
        println("[maps] parsing ${region.name} (region ${region.regionId}) from $dir")
        PretGbaParser(dir, region, MovementTypes.read(dir)).parseAll()
      }
  println("[maps] parsed ${maps.size} maps. writing to $outputDir")
  MapsRenderer(templatesDir, outputDir, classCacheDir).render(maps)
  println("[maps] done")
}
