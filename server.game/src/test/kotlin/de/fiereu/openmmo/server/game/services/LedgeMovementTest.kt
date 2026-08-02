package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.TileBehavior
import de.fiereu.openmmo.maps.MapDef
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.util.Base64

class LedgeMovementTest :
    FunSpec({
      test("each directional ledge lands two tiles away") {
        val cases =
            listOf(
                Direction.DOWN to TileBehavior.JUMP_SOUTH,
                Direction.UP to TileBehavior.JUMP_NORTH,
                Direction.LEFT to TileBehavior.JUMP_WEST,
                Direction.RIGHT to TileBehavior.JUMP_EAST,
            )

        cases.forEach { (direction, behavior) ->
          val map = testMap(behavior)
          val fromX = 2 - direction.dx
          val fromY = 2 - direction.dy

          ledgeLanding(map, fromX, fromY, direction) shouldBe (2 + direction.dx to 2 + direction.dy)
          ledgeLanding(map, fromX, fromY, direction.opposite()) shouldBe null
        }
      }

      test("a ledge hop is rejected when its landing tile is blocked") {
        val map = testMap(TileBehavior.JUMP_SOUTH, blockedX = 2, blockedY = 3)

        ledgeLanding(map, 2, 1, Direction.DOWN) shouldBe null
      }
    })

private fun testMap(
    ledgeBehavior: TileBehavior,
    blockedX: Int? = null,
    blockedY: Int? = null,
): MapDef {
  val width = 5
  val height = 5
  val blocks = ByteArray(width * height * 2)
  if (blockedX != null && blockedY != null) {
    val offset = (blockedY * width + blockedX) * 2
    blocks[offset + 1] = 0x04 // movement collision 1 in raw metatile bits 10-11
  }
  val behaviors = ByteArray(width * height)
  behaviors[2 * width + 2] = ledgeBehavior.ordinal.toByte()
  return MapDef(
      regionId = 1,
      bankId = 50,
      mapId = 0,
      width = width,
      height = height,
      blockData = Base64.getEncoder().encodeToString(blocks),
      behaviorData = Base64.getEncoder().encodeToString(behaviors),
  )
}
