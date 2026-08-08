package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterFacingTest :
    FunSpec({
      test("a new character faces down") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id

          store.getCharacter(charId)!!.info.positionFacing shouldBe Direction.DOWN
        }
      }

      test("a move commits the direction it was walked in") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id

          store.updatePosition(charId, 3, 4, facing = Direction.RIGHT)

          val info = store.getCharacter(charId)!!.info
          info.positionX shouldBe 3
          info.positionFacing shouldBe Direction.RIGHT
        }
      }

      test("a move that does not name a direction keeps the stored one") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          store.updatePosition(charId, 3, 4, facing = Direction.UP)

          store.updatePosition(charId, 5, 6)

          store.getCharacter(charId)!!.info.positionFacing shouldBe Direction.UP
        }
      }
    })
