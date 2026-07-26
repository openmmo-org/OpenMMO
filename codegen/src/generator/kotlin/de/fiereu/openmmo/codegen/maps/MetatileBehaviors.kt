package de.fiereu.openmmo.codegen.maps

import de.fiereu.openmmo.common.enums.TileBehavior
import java.io.File
import java.util.Base64

/**
 * Resolves the [TileBehavior] of each tile from the decomp tileset metatile attributes, so the
 * server can tell grass from plain ground. Emerald packs attributes as 16-bit words (behavior in
 * bits 0-7), FireRed as 32-bit words (behavior in bits 0-8). Behaviors are classified by name so
 * the two games' differing numeric values do not matter.
 */
class MetatileBehaviors
private constructor(
    private val rootDir: File,
    private val primaryCount: Int,
    private val attrWidth: Int,
    private val behaviorMask: Int,
    private val idToCategory: Map<Int, TileBehavior>,
    private val attributePaths: Map<String, String>,
    private val tilesetAttributeSymbol: Map<String, String>,
) {

  // gTileset_X -> per local metatile index -> behavior ordinal, read from its .bin on first use.
  private val tilesetCache = HashMap<String, IntArray>()

  /** Base64 of one behavior ordinal byte per tile, in the same order as the block data. */
  fun behaviorData(
      primaryTileset: String?,
      secondaryTileset: String?,
      blockBytes: ByteArray
  ): String {
    val tileCount = blockBytes.size / 2
    if (tileCount == 0) return ""
    val primary = primaryTileset?.let { categories(it) } ?: IntArray(0)
    val secondary = secondaryTileset?.let { categories(it) } ?: IntArray(0)
    val out = ByteArray(tileCount)
    for (i in 0 until tileCount) {
      val raw =
          (blockBytes[i * 2].toInt() and 0xFF) or ((blockBytes[i * 2 + 1].toInt() and 0xFF) shl 8)
      val metatileId = raw and 0x3FF
      val ordinal =
          if (metatileId < primaryCount) primary.getOrNull(metatileId)
          else secondary.getOrNull(metatileId - primaryCount)
      out[i] = (ordinal ?: TileBehavior.NORMAL.ordinal).toByte()
    }
    return Base64.getEncoder().encodeToString(out)
  }

  private fun categories(tilesetName: String): IntArray =
      tilesetCache.getOrPut(tilesetName) {
        val symbol = tilesetAttributeSymbol[tilesetName] ?: return@getOrPut IntArray(0)
        val path = attributePaths[symbol] ?: return@getOrPut IntArray(0)
        val file = File(rootDir, path)
        if (!file.exists()) return@getOrPut IntArray(0)
        val bytes = file.readBytes()
        IntArray(bytes.size / attrWidth) { m ->
          var attr = 0
          for (b in 0 until attrWidth) {
            attr = attr or ((bytes[m * attrWidth + b].toInt() and 0xFF) shl (8 * b))
          }
          (idToCategory[attr and behaviorMask] ?: TileBehavior.NORMAL).ordinal
        }
      }

  companion object {
    fun read(rootDir: File): MetatileBehaviors {
      val behaviorIds = parseBehaviorIds(File(rootDir, "include/constants/metatile_behaviors.h"))
      val idToCategory =
          behaviorIds.mapNotNull { (name, id) -> classify(name)?.let { id to it } }.toMap()
      val (attributePaths, width) =
          parseAttributeIncbins(File(rootDir, "src/data/tilesets/metatiles.h"))
      val behaviorMask = if (width == 4) 0x1FF else 0xFF
      return MetatileBehaviors(
          rootDir = rootDir,
          primaryCount = readPrimaryCount(File(rootDir, "include/fieldmap.h")),
          attrWidth = width,
          behaviorMask = behaviorMask,
          idToCategory = idToCategory,
          attributePaths = attributePaths,
          tilesetAttributeSymbol =
              parseTilesetAttributes(File(rootDir, "src/data/tilesets/headers.h")),
      )
    }

    // Only the behaviors we act on are mapped, everything else stays NORMAL.
    private fun classify(name: String): TileBehavior? =
        when (name) {
          "MB_TALL_GRASS" -> TileBehavior.TALL_GRASS
          "MB_LONG_GRASS" -> TileBehavior.LONG_GRASS
          else -> null
        }

    // FireRed lists behaviors as #define constants, Emerald as a plain enum (with a stray #define
    // or two). Parse both so either game resolves regardless of which style it mixes in.
    private fun parseBehaviorIds(file: File): Map<String, Int> {
      if (!file.exists()) return emptyMap()
      val out = LinkedHashMap<String, Int>()
      val defineRe = Regex("""#define\s+(MB_\w+)\s+(0x[0-9A-Fa-f]+|\d+)""")
      for (m in defineRe.findAll(file.readText())) out[m.groupValues[1]] =
          parseInt(m.groupValues[2])
      // Enum entries take sequential values, but some are given an explicit value mid-list.
      val entryRe = Regex("""^(MB_\w+)\s*(?:=\s*(0x[0-9A-Fa-f]+|\d+))?""")
      var next = 0
      var inEnum = false
      for (raw in file.readLines()) {
        val line = raw.trim()
        if (line.startsWith("enum")) {
          inEnum = true
          continue
        }
        if (!inEnum) continue
        if (line.startsWith("}")) {
          inEnum = false
          continue
        }
        val m = entryRe.find(line) ?: continue
        val id = m.groupValues[2].takeIf { it.isNotEmpty() }?.let { parseInt(it) } ?: next
        out.putIfAbsent(m.groupValues[1], id)
        next = id + 1
      }
      return out
    }

    private fun readPrimaryCount(file: File): Int {
      if (!file.exists()) return 512
      val re = Regex("""#define\s+NUM_METATILES_IN_PRIMARY\s+(\d+)""")
      return file.readLines().firstNotNullOfOrNull {
        re.find(it.trim())?.groupValues?.get(1)?.toInt()
      } ?: 512
    }

    // gMetatileAttributes_X[] = INCBIN_U16/U32("path"). The width tells us the attribute size.
    private fun parseAttributeIncbins(file: File): Pair<Map<String, String>, Int> {
      if (!file.exists()) return emptyMap<String, String>() to 2
      val re = Regex("""(gMetatileAttributes_\w+)\[]\s*=\s*INCBIN_U(16|32)\("([^"]+)"\)""")
      val map = LinkedHashMap<String, String>()
      var width = 2
      for (m in re.findAll(file.readText())) {
        map[m.groupValues[1]] = m.groupValues[3]
        width = if (m.groupValues[2] == "32") 4 else 2
      }
      return map to width
    }

    // Walk the tileset structs and pair each gTileset_X with its metatileAttributes symbol.
    private fun parseTilesetAttributes(file: File): Map<String, String> {
      if (!file.exists()) return emptyMap()
      val tilesetRe = Regex("""const struct Tileset\s+(gTileset_\w+)""")
      val attrRe = Regex("""\.metatileAttributes\s*=\s*(gMetatileAttributes_\w+)""")
      val out = LinkedHashMap<String, String>()
      var current: String? = null
      for (line in file.readLines()) {
        tilesetRe.find(line)?.let { current = it.groupValues[1] }
        attrRe.find(line)?.let { m -> current?.let { out[it] = m.groupValues[1] } }
      }
      return out
    }

    private fun parseInt(s: String): Int =
        if (s.startsWith("0x") || s.startsWith("0X")) s.substring(2).toInt(16) else s.toInt()
  }
}
