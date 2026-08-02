package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterIntroTest :
    FunSpec({
      test("male characters start on Brendan's side of the moving truck intro") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)

          val character = store.createCharacter(userId = 1, name = "Brendan", gender = 0)

          character.info.rivalSex shouldBe 0
          character.info.dynamicWarp!!.x shouldBe 3
          character.storyVars[HoennVars.VAR_LITTLEROOT_INTRO_STATE] shouldBe 1
          character.storyVars[HoennVars.VAR_LITTLEROOT_HOUSES_STATE_BRENDAN] shouldBe 1
          (HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_MAYS_HOUSE_TRUCK in character.storyFlags) shouldBe
              true
          (HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_BRENDANS_HOUSE_TRUCK in
              character.storyFlags) shouldBe false
        }
      }

      test("female characters start on May's side of the moving truck intro") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)

          val character = store.createCharacter(userId = 1, name = "May", gender = 1)

          character.info.rivalSex shouldBe 1
          character.info.dynamicWarp!!.x shouldBe 12
          character.storyVars[HoennVars.VAR_LITTLEROOT_INTRO_STATE] shouldBe 2
          character.storyVars[HoennVars.VAR_LITTLEROOT_HOUSES_STATE_MAY] shouldBe 1
          (HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_BRENDANS_HOUSE_TRUCK in
              character.storyFlags) shouldBe true
          (HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_MAYS_HOUSE_TRUCK in character.storyFlags) shouldBe
              false
        }
      }

      test("Kanto characters use the captured Pallet bedroom start") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)

          val character =
              store.createCharacter(
                  userId = 1,
                  name = "Leaf",
                  gender = 1,
                  startingRegion = 0,
              )

          character.info.positionRegionId shouldBe 0
          character.info.positionBankId shouldBe 4
          character.info.positionMapId shouldBe 1
          character.info.positionX shouldBe 6
          character.info.positionY shouldBe 6
          character.info.dynamicWarp shouldBe null
          character.storyFlags shouldBe emptySet()
          character.storyVars shouldBe emptyMap()
        }
      }

      test("unsupported regions remain locked") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)

          shouldThrow<IllegalArgumentException> {
            store.createCharacter(
                userId = 1,
                name = "Dawn",
                gender = 1,
                startingRegion = 2,
            )
          }
        }
      }
    })
