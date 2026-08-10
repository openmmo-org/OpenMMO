package de.fiereu.openmmo.codegen.item

import java.io.File
import java.text.Normalizer
import java.util.Locale
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

// A wire id is regionId * 1000 + index, and the client answers in the region 5 table.
private const val ITEM_REGION_BLOCK = 5000

private val PLACEHOLDER_NAMES = setOf("None", "?????", "???", "-", "?", "")

/** A name's position in `item_names.json` is the index `items.json` keys by. */
class ItemDataParser(private val dataDir: File) {

  private val json = Json { ignoreUnknownKeys = true }

  fun parseAll(): List<ParsedItem> {
    val itemsFile = File(dataDir, "items.json")
    val namesFile = File(dataDir, "item_names.json")
    require(itemsFile.exists() && namesFile.exists()) {
      "Item data not found at $dataDir (missing ${itemsFile.name} or ${namesFile.name})"
    }

    val names =
        json.parseToJsonElement(namesFile.readText()).jsonArray.map { it.jsonPrimitive.content }
    val prices =
        json.parseToJsonElement(itemsFile.readText()).jsonArray.associate { entry ->
          val obj = entry.jsonObject
          obj.getValue("index").jsonPrimitive.int to obj.getValue("price").jsonPrimitive.int
        }

    return names
        .withIndex()
        .filterNot { (index, name) -> index == 0 || name.trim() in PLACEHOLDER_NAMES }
        .mapNotNull { (index, name) ->
          val identifier = identifierOf(name)
          if (identifier.isEmpty()) null else Triple(identifier, name, index)
        }
        .groupBy { it.first }
        .map { (identifier, group) ->
          // Two different names normalising the same would silently become one item.
          val grouped = group.map { it.second }.distinct()
          check(grouped.size == 1) { "Items $grouped all derive the identifier $identifier" }
          val lowest = group.minBy { it.third }
          ParsedItem(
              identifier = identifier,
              name = lowest.second,
              price = prices[lowest.third] ?: 0,
              ids = group.map { ITEM_REGION_BLOCK + it.third }.sorted(),
          )
        }
        .sortedBy { it.ids.first() }
  }

  /** Turns a display name into a Kotlin constant name: "Poké Ball" becomes POKE_BALL. */
  private fun identifierOf(name: String): String =
      Normalizer.normalize(name, Normalizer.Form.NFD)
          .replace(Regex("\\p{Mn}+"), "")
          .uppercase(Locale.ROOT)
          .replace(Regex("[^A-Z0-9]+"), "_")
          .trim('_')
          .let { if (it.firstOrNull()?.isDigit() == true) "_$it" else it }
}
