package de.fiereu.openmmo.maps

import de.fiereu.openmmo.common.enums.TileBehavior
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class HoennMapBehaviorTest :
    FunSpec({
      test("generated Hoenn maps retain the ledge behaviors used by Emerald") {
        val maps = MapManager()
        val behaviors = buildSet {
          for (bank in 50..83) {
            for (mapId in 0..255) {
              maps.getMap(1, bank, mapId)?.tiles?.forEach { add(it.behavior) }
            }
          }
        }

        behaviors shouldContainAll
            listOf(
                TileBehavior.JUMP_EAST,
                TileBehavior.JUMP_WEST,
                TileBehavior.JUMP_SOUTH,
            )

        maps.getMap(1, 50, 26)!!.tiles.any { it.behavior == TileBehavior.JUMP_SOUTH } shouldBe true
      }
    })
