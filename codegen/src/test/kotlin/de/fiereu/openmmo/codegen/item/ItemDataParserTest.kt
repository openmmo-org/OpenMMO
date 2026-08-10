package de.fiereu.openmmo.codegen.item

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.io.File

private fun dataDir(names: List<String>, prices: Map<Int, Int>): File {
  val dir = kotlin.io.path.createTempDirectory("item-data").toFile()
  dir.resolve("item_names.json").writeText(names.joinToString(",", "[", "]") { "\"$it\"" })
  dir.resolve("items.json")
      .writeText(
          prices.entries.joinToString(",", "[", "]") { (index, price) ->
            """{"index":$index,"price":$price,"hold_effect":0}"""
          })
  return dir
}

class ItemDataParserTest :
    FunSpec({
      test("reads a name and its price into the region 5 id block") {
        val items =
            ItemDataParser(
                    dataDir(
                        listOf("None", "Master Ball", "Ultra Ball", "Poké Ball"),
                        mapOf(1 to 0, 2 to 1200, 3 to 200)))
                .parseAll()

        items.single { it.identifier == "POKE_BALL" } shouldBe
            ParsedItem("POKE_BALL", "Poké Ball", 200, listOf(5003))
      }

      test("collects every slot sharing a name into one item with several ids") {
        val items =
            ItemDataParser(
                    dataDir(
                        listOf("None", "Potion", "Antidote", "Potion"),
                        mapOf(1 to 300, 2 to 100, 3 to 300)))
                .parseAll()

        items.single { it.identifier == "POTION" }.ids shouldContainExactly listOf(5001, 5003)
      }

      test("skips index 0 and the placeholder slots") {
        val items =
            ItemDataParser(dataDir(listOf("None", "?????", "-", "", "Potion"), mapOf(4 to 300)))
                .parseAll()

        items.map { it.identifier } shouldContainExactly listOf("POTION")
      }

      test("derives identifiers that are legal Kotlin names") {
        val names = listOf("None", "Poké Ball", "Parlyz Heal", "Up-Grade", "10 Coins", "X Attack")
        val items = ItemDataParser(dataDir(names, emptyMap())).parseAll()

        items.map { it.identifier } shouldContainExactly
            listOf("POKE_BALL", "PARLYZ_HEAL", "UP_GRADE", "_10_COINS", "X_ATTACK")
      }

      test("prices an item the table has no entry for at zero") {
        val items = ItemDataParser(dataDir(listOf("None", "Parcel"), emptyMap())).parseAll()

        items.single().price shouldBe 0
      }

      test("fails loudly when the vendored data is missing") {
        val empty = kotlin.io.path.createTempDirectory("item-data-empty").toFile()

        shouldThrow<IllegalArgumentException> { ItemDataParser(empty).parseAll() }
      }
    })
