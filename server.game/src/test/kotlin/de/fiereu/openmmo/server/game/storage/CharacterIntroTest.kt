package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
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

          val character = store.createCharacter(1, "Brendan", CharacterGender.MALE, Region.HOENN)

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

          val character = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN)

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

      test("Kanto characters start in the Pallet bedroom with FireRed's new game flags") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)

          val character =
              store.createCharacter(
                  userId = 1,
                  name = "Leaf",
                  gender = CharacterGender.FEMALE,
                  startingRegion = Region.KANTO,
              )

          character.info.positionRegionId shouldBe 0
          character.info.positionBankId shouldBe 4
          character.info.positionMapId shouldBe 1
          character.info.positionX shouldBe 6
          character.info.positionY shouldBe 6
          // Kanto has no truck ride, so nothing depends on the player's dynamic warp yet.
          character.info.dynamicWarp shouldBe null
          character.storyVars shouldBe emptyMap()
          // Oak waits in the grass rather than in his lab or in town.
          (KantoFlags.FLAG_HIDE_OAK_IN_HIS_LAB in character.storyFlags) shouldBe true
          (KantoFlags.FLAG_HIDE_OAK_IN_PALLET_TOWN in character.storyFlags) shouldBe true
          // The rival and the three starter balls are there from the start.
          (KantoFlags.FLAG_HIDE_RIVAL_IN_LAB in character.storyFlags) shouldBe false
          (KantoFlags.FLAG_HIDE_BULBASAUR_BALL in character.storyFlags) shouldBe false
        }
      }

      test("unsupported region wire values remain locked") { Region.byWireValue(2) shouldBe null }
    })
