@file:JvmName("Main")

package de.fiereu.openmmo.codegen.item

import java.io.File

fun main(args: Array<String>) {
  require(args.size >= 4) {
    "Usage: <output-dir> <templates-dir> <class-cache-dir> <decomp-dir> got ${args.toList()}"
  }
  val outputDir = File(args[0])
  val templatesDir = File(args[1])
  val classCacheDir = File(args[2])
  val decompDir = File(args[3])

  println("[item] parsing from $decompDir")
  val items = ItemParser(decompDir).parseAll()
  println("[item] parsed ${items.size} items. writing to $outputDir")
  ItemsRenderer(templatesDir, outputDir, classCacheDir).render(items)
  println("[item] done")
}
