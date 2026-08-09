package de.fiereu.openmmo.codegen.item

import java.io.File

/**
 * Reads the item constants out of a GBA decomp header. The two GBA decomps use the same item
 * numbering, so one decomp is the single source of truth, the same way moves and species are.
 *
 * The client keys an item by `regionId * 1000 + itemId` and holds one item table per region.
 * [regionId] picks the table that matches the decomp's own numbering, which leaves the Poke Ball at
 * its decomp 4.
 */
class ItemParser(private val decompDir: File, private val regionId: Int) {

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
        .map { ParsedItem(it.groupValues[1], regionId * 1000 + it.groupValues[2].toInt()) }
        // Placeholder slots are named after their hex index, for example ITEM_15C.
        .filterNot { it.name.first().isDigit() }
        .distinctBy(ParsedItem::name)
  }
}
