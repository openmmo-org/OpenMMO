package de.fiereu.openmmo.server.game.world

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.TileBehavior
import de.fiereu.openmmo.maps.MapManager
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val KANTO = 0
private const val TOWNS_AND_ROUTES_BANK = 3
private const val PALLET_TOWN_MAP = 0
private const val ROUTE_4_MAP = 22
private const val INDOOR_PALLET_BANK = 4
private const val PLAYERS_HOUSE_1F = 0
private const val PLAYERS_HOUSE_2F = 1

class WarpTileBehaviorTest :
    FunSpec({
      val maps = MapManager()

      test("Kanto doors and stairs are classified from the decomp") {
        val town = maps.getMap(KANTO, TOWNS_AND_ROUTES_BANK, PALLET_TOWN_MAP)!!
        town.tileAt(6, 7)!!.behavior shouldBe TileBehavior.DOOR

        // Right goes up, left comes back down.
        val house1f = maps.getMap(KANTO, INDOOR_PALLET_BANK, PLAYERS_HOUSE_1F)!!
        val house2f = maps.getMap(KANTO, INDOOR_PALLET_BANK, PLAYERS_HOUSE_2F)!!
        house1f.tileAt(10, 2)!!.behavior shouldBe TileBehavior.STAIR_WARP_EAST
        house2f.tileAt(10, 2)!!.behavior shouldBe TileBehavior.STAIR_WARP_WEST
        house1f.tileAt(10, 2)!!.behavior.warpsWhenWalking shouldBe Direction.RIGHT
        house2f.tileAt(10, 2)!!.behavior.warpsWhenWalking shouldBe Direction.LEFT
      }

      test("standing on stairs only warps when walking the way they point") {
        val stairs = maps.getMap(KANTO, INDOOR_PALLET_BANK, PLAYERS_HOUSE_1F)!!.tileAt(10, 2)!!

        stairs.behavior.warpsWhenWalking shouldBe Direction.RIGHT
        (stairs.behavior.warpsWhenWalking == Direction.LEFT) shouldBe false
        (stairs.behavior.warpsWhenWalking == Direction.UP) shouldBe false
        (stairs.behavior.warpsWhenWalking == Direction.DOWN) shouldBe false
        stairs.behavior.warpsOnStep shouldBe false
      }

      test("a door drops the player out facing down and off the tile") {
        val town = maps.getMap(KANTO, TOWNS_AND_ROUTES_BANK, PALLET_TOWN_MAP)!!
        val house = maps.getMap(KANTO, INDOOR_PALLET_BANK, PLAYERS_HOUSE_1F)!!

        WarpExitRules.inferExitFacing(
            destTileBehavior = TileBehavior.DOOR,
            destMap = town,
            destX = 6,
            destY = 7,
            sourceMap = house,
        ) shouldBe Direction.DOWN
        WarpExitRules.shouldAutoStep(house, town, TileBehavior.DOOR) shouldBe true
        TileBehavior.DOOR.warpsWhenWalking shouldBe null
      }

      test("a cave entrance warps on the step, in any direction") {
        val route4 = maps.getMap(KANTO, TOWNS_AND_ROUTES_BANK, ROUTE_4_MAP)!!
        val entrance = route4.tileAt(19, 5)!!.behavior

        entrance shouldBe TileBehavior.NON_ANIMATED_DOOR
        entrance.warpsOnStep shouldBe true
        entrance.warpsWhenWalking shouldBe null
      }

      test("stairs leave the player standing on the tile") {
        val house1f = maps.getMap(KANTO, INDOOR_PALLET_BANK, PLAYERS_HOUSE_1F)!!
        val house2f = maps.getMap(KANTO, INDOOR_PALLET_BANK, PLAYERS_HOUSE_2F)!!

        WarpExitRules.shouldAutoStep(house1f, house2f, TileBehavior.STAIR_WARP_EAST) shouldBe false
      }
    })
