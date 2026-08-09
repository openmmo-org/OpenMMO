package de.fiereu.openmmo.codegen.item

import de.fiereu.openmmo.common.enums.Region
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ItemParserTest :
    FunSpec({
      test("prefixes decomp item ids with their region and stops at the list end") {
        val decomp = kotlin.io.path.createTempDirectory("items").toFile()
        decomp.resolve("include/constants").mkdirs()
        decomp
            .resolve("include/constants/items.h")
            .writeText(
                """
            #define ITEM_NONE 0
            #define ITEM_GREAT_BALL 3
            #define ITEM_POKE_BALL 4
            #define ITEM_15C 348
            #define ITEM_LIST_END 0xFFFF
            #define ITEMS_COUNT 377
            #define ITEM_USE_MAIL 0
            #define ITEM_HAS_EFFECT(item) ((item) >= ITEM_POTION)
            """
                    .trimIndent())

        ItemParser(decomp, Region.KANTO.wireValue.toInt()).parseAll() shouldBe
            listOf(
                ParsedItem("NONE", 0),
                ParsedItem("GREAT_BALL", 3),
                ParsedItem("POKE_BALL", 4),
            )

        ItemParser(decomp, Region.HOENN.wireValue.toInt()).parseAll() shouldBe
            listOf(
                ParsedItem("NONE", 1000),
                ParsedItem("GREAT_BALL", 1003),
                ParsedItem("POKE_BALL", 1004),
            )
      }
    })
