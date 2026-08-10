package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.Skin
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.common.enums.SkinSlot
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

private val CREATOR_SKINS =
    mapOf(
        SkinSlot.HAIR to Skin(SkinSlot.HAIR, 4u, 24u),
        SkinSlot.EYES to Skin(SkinSlot.EYES, 15u, 0u),
        SkinSlot.TOP to Skin(SkinSlot.TOP, 3u, 2u),
    )

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterSkinsTest :
    FunSpec({
      test("creation keeps the appearance the client picked") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)

          val stored =
              store.createCharacter(
                  1,
                  "Red",
                  CharacterGender.MALE,
                  Region.KANTO,
                  skins = CREATOR_SKINS,
                  skinRegionSelectionIndex = 3,
              )

          val cached = store.getCharacter(stored.info.id)!!
          cached.skins shouldBe CREATOR_SKINS
          cached.info.skinRegionSelectionIndex shouldBe 3
        }
      }

      test("creation writes the appearance out with the rest of the aggregate") {
        runTest {
          val repository = FakeCharacterRepository()
          val store = CharacterStore(repository, EntityIdService(), backgroundScope)

          val id =
              store
                  .createCharacter(
                      1, "Red", CharacterGender.MALE, Region.KANTO, skins = CREATOR_SKINS)
                  .info
                  .id

          repository.saved[id]!!.skins shouldBe CREATOR_SKINS
        }
      }

      test("a character created without an appearance has no slots") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)

          val stored = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO)

          stored.skins.shouldBeEmpty()
          stored.info.skinRegionSelectionIndex shouldBe 0
        }
      }
    })
