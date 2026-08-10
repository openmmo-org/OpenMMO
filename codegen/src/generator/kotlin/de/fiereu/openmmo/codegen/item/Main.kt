@file:JvmName("Main")

package de.fiereu.openmmo.codegen.item

import java.io.File

fun main(args: Array<String>) {
  require(args.size >= 4) {
    "Usage: <output-dir> <templates-dir> <class-cache-dir> <item-data-dir> got ${args.toList()}"
  }
  val outputDir = File(args[0])
  val templatesDir = File(args[1])
  val classCacheDir = File(args[2])
  val dataDir = File(args[3])

  println("[item] parsing from $dataDir")
  val items = ItemDataParser(dataDir).parseAll()
  val ids = items.sumOf { it.ids.size }
  println("[item] parsed ${items.size} items carrying $ids ids. writing to $outputDir")
  ItemsRenderer(templatesDir, outputDir, classCacheDir).render(items)
  println("[item] done")
}
