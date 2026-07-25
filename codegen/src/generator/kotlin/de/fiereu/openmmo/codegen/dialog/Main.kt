@file:JvmName("Main")

package de.fiereu.openmmo.codegen.dialog

import java.io.File

// GBA dialog ids carry the retail ROM file offset in the low bits with the mode-1 resolver on top.
private const val GBA_MODE = 0x10000000

fun main(args: Array<String>) {
  require(args.size >= 5) {
    "Usage: <output-dir> <templates-dir> <class-cache-dir> <roms-dir> <region|gameCode|decomp>... got ${args.toList()}"
  }
  val outputDir = File(args[0])
  val templatesDir = File(args[1])
  val classCacheDir = File(args[2])
  val romsDir = File(args[3])

  for (spec in args.drop(4)) {
    val (region, gameCode, decomp) = spec.split("|")
    generateRegion(region, gameCode, File(decomp), romsDir, outputDir, templatesDir, classCacheDir)
  }
}

private fun generateRegion(
    region: String,
    gameCode: String,
    decompDir: File,
    romsDir: File,
    outputDir: File,
    templatesDir: File,
    classCacheDir: File,
) {
  val renderer = DialogRenderer(region, templatesDir, outputDir, classCacheDir)
  val rom = RomIndex.find(romsDir, gameCode)
  if (rom == null) {
    println("[dialog] no $gameCode ROM found in $romsDir, generating empty $region")
    renderer.render(emptyList())
    return
  }

  val charmap = Charmap.load(File(decompDir, "charmap.txt"))
  val texts = TextParser(decompDir).parseAll()
  println("[dialog] $region: parsed ${texts.size} text labels from $decompDir")

  var unencodable = 0
  var notFound = 0
  val lines =
      texts.mapNotNull { t ->
        val bytes = charmap.encode(t.content)
        if (bytes == null) {
          unencodable++
          return@mapNotNull null
        }
        val offset = rom.offsetOf(bytes)
        if (offset < 0) {
          notFound++
          return@mapNotNull null
        }
        DialogLine(t.label, GBA_MODE or offset, RenderUtil.preview(t.content))
      }

  println(
      "[dialog] $region: resolved ${lines.size} lines (skipped $unencodable unencodable, $notFound not in ROM)")
  renderer.render(lines)
}
