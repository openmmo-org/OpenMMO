package de.fiereu.openmmo.codegen.item

import java.io.File

/**
 * Reads the item constants out of a GBA decomp header. The two GBA decomps use the same item
 * numbering, so one decomp is the single source of truth, the same way moves and species are.
 *
 * The client keys items by `regionId * 1000 + itemId`. Both GBA games share region id
 * [GBA_REGION_ID], which is what makes the Poke Ball (GBA 4) the client's 5004 and the Great Ball
 * (GBA 3) its 5003, the two ids the battle code already sends.
 */
class ItemParser(private val decompDir: File) {

  fun parseAll(): List<ParsedItem> {
    val header = File(decompDir, "include/constants/items.h")
    require(header.exists()) { "Missing ${header.path}" }
    val define = Regex("""^#define\s+ITEM_([A-Z0-9_]+)\s+(\d+)\s*$""")
    // ITEMS_COUNT closes the item list. Everything after it reuses the ITEM_ prefix for unrelated
    // constants like the bag usage types, so parsing stops there.
    return header
        .readLines()
        .takeWhile { !it.trimStart().startsWith("#define ITEMS_COUNT") }
        .mapNotNull { define.find(it.trim()) }
        .map { ParsedItem(it.groupValues[1], GBA_REGION_ID * 1000 + it.groupValues[2].toInt()) }
        // Placeholder slots are named after their hex index, for example ITEM_15C.
        .filterNot { it.name.first().isDigit() }
        .distinctBy(ParsedItem::name)
  }

  private companion object {
    const val GBA_REGION_ID = 5
  }
}
