@file:JvmName("Main")

package de.fiereu.openmmo.codegen.dialog

import de.fiereu.openmmo.common.enums.Region
import java.io.File

// A GBA dialog id is the retail ROM file offset with the region on top, which is how the client
// knows the ROM to resolve it in. Captured Kanto ids carry 0 there and Hoenn ids carry 1.
private const val REGION_SHIFT = 28

private fun regionMode(region: String): Int {
  val known =
      Region.entries.find { it.name.equals(region, ignoreCase = true) }
          ?: error("unknown region '$region', its dialog ids cannot be built")
  return known.wireValue.toInt() shl REGION_SHIFT
}

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

  val texts = TextParser(decompDir).parseAll()
  println("[dialog] $region: parsed ${texts.size} text labels from $decompDir")

  // Without a ROM we cannot resolve textIds, but the enums must still exist so code that
  // references them compiles (for example in CI, where ROMs are never present). Emit every
  // label with a placeholder textId so the build works, just without real dialog ids.
  if (rom == null) {
    println(
        "[dialog] no $gameCode ROM found in $romsDir, generating $region labels without textIds")
    renderer.render(texts.map { DialogLine(it.label, 0, RenderUtil.preview(it.content)) })
    return
  }

  val charmap = Charmap.load(File(decompDir, "charmap.txt"))
  val mode = regionMode(region)

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
        DialogLine(t.label, mode or offset, RenderUtil.preview(t.content))
      }

  println(
      "[dialog] $region: resolved ${lines.size} lines (skipped $unencodable unencodable, $notFound not in ROM)")
  renderer.render(lines)
}
