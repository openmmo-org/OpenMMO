package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class StoryServiceTest :
    FunSpec({
      test("flags default unset, can be set and cleared") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id
          val story = StoryService(store)

          story.isFlagSet(id, "hoenn/FLAG_ADVENTURE_STARTED") shouldBe false
          story.setFlag(id, "hoenn/FLAG_ADVENTURE_STARTED")
          story.isFlagSet(id, "hoenn/FLAG_ADVENTURE_STARTED") shouldBe true
          story.clearFlag(id, "hoenn/FLAG_ADVENTURE_STARTED")
          story.isFlagSet(id, "hoenn/FLAG_ADVENTURE_STARTED") shouldBe false
        }
      }

      test("vars default to 0, store a value, and drop back to default on 0") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id
          val story = StoryService(store)

          story.getVar(id, "hoenn/VAR_LITTLEROOT_TOWN_STATE") shouldBe 0
          story.setVar(id, "hoenn/VAR_LITTLEROOT_TOWN_STATE", 3)
          story.getVar(id, "hoenn/VAR_LITTLEROOT_TOWN_STATE") shouldBe 3

          story.setVar(id, "hoenn/VAR_LITTLEROOT_TOWN_STATE", 0)
          story.getVar(id, "hoenn/VAR_LITTLEROOT_TOWN_STATE") shouldBe 0
          store.getCharacter(id)!!.storyVars.containsKey("hoenn/VAR_LITTLEROOT_TOWN_STATE") shouldBe
              false
        }
      }

      test("setFlag copies instead of mutating the cached set") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id
          val before = store.getCharacter(id)!!.storyFlags
          val initialSize = before.size

          StoryService(store).setFlag(id, "hoenn/FLAG_RESCUED_BIRCH")

          before.size shouldBe initialSize
          ("hoenn/FLAG_RESCUED_BIRCH" in before) shouldBe false
          store.getCharacter(id)!!.storyFlags.size shouldBe initialSize + 1
        }
      }

      test("flags and vars persist through a flush") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id
          val story = StoryService(store)

          story.setFlag(id, "hoenn/FLAG_ADVENTURE_STARTED")
          story.setVar(id, "hoenn/VAR_LITTLEROOT_TOWN_STATE", 4)
          store.flushAll()

          repo.saved[id]!!.storyFlags shouldBe store.getCharacter(id)!!.storyFlags
          ("hoenn/FLAG_ADVENTURE_STARTED" in repo.saved[id]!!.storyFlags) shouldBe true
          // New characters are seeded with the intro var, so check the entry rather than the map.
          repo.saved[id]!!.storyVars["hoenn/VAR_LITTLEROOT_TOWN_STATE"] shouldBe 4
        }
      }
    })
