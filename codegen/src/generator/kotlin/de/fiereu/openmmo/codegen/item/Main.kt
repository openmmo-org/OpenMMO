@file:JvmName("Main")

package de.fiereu.openmmo.codegen.item

import de.fiereu.openmmo.common.enums.Region
import java.io.File

fun main(args: Array<String>) {
  require(args.size >= 4) {
    "Usage: <output-dir> <templates-dir> <class-cache-dir> <region|decomp> got ${args.toList()}"
  }
  val outputDir = File(args[0])
  val templatesDir = File(args[1])
  val classCacheDir = File(args[2])
  val (region, decomp) = args[3].split("|")
  val decompDir = File(decomp)
  val regionId = Region.valueOf(region.uppercase()).wireValue.toInt()

  println("[item] parsing $region items from $decompDir")
  val items = ItemParser(decompDir, regionId).parseAll()
  println("[item] parsed ${items.size} items. writing to $outputDir")
  ItemsRenderer(templatesDir, outputDir, classCacheDir).render(items)
  println("[item] done")
}
