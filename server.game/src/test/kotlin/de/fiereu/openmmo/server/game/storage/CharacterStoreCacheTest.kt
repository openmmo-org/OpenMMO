package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

private fun testPokemon(ownerId: Long): Pokemon =
    Pokemon(
        id = EntityIdService().newMonsterId(),
        ownerId = ownerId,
        container = PokemonContainer.PARTY,
        containerSlot = 0,
        dexId = 19,
        seed = 0,
        ot = "Ash",
        nickname = "",
        level = 3,
        hp = 14,
        xp = 27,
        eVs = EVs(),
        iVs = IVs(),
        moves = listOf(),
        isShiny = false,
        hasHiddenAbility = false,
        isAlpha = false,
        isSecret = false,
        isFatefulEncounter = false,
        isRaidEncounter = false,
        caughtAt = LocalDateTime.now(),
    )

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterStoreCacheTest :
    FunSpec({
      test("createCharacter inserts the aggregate into the repository") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val created = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN)
          repo.saved[created.info.id].shouldNotBeNull()
          repo.saved[created.info.id]!!.pokemon.size shouldBe 0
        }
      }

      test("mutations flush through flushAll") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id

          store.updatePosition(id, 10, 20)
          store.flushAll()

          repo.saved[id]!!.info.positionX shouldBe 10.toShort()
          repo.saved[id]!!.info.positionY shouldBe 20.toShort()
        }
      }

      test("reads come from memory, not the repository") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id

          store.addMoney(id, 5000)

          store.getCharacter(id)!!.info.money shouldBe 35000
          repo.saved[id]!!.info.money shouldBe 30000
        }
      }

      test("addPokemon copies instead of mutating the cached list") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val created = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN)
          val before = store.getCharacter(created.info.id)!!.pokemon

          store.addPokemon(created.info.id, testPokemon(created.info.id))

          before.size shouldBe 0
          store.getCharacter(created.info.id)!!.pokemon.size shouldBe 1
        }
      }

      test("updatePokemon replaces the party entry by id and marks dirty") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val created = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN)
          val mon = testPokemon(created.info.id)
          store.addPokemon(created.info.id, mon)
          store.flushAll()

          val before = store.getCharacter(created.info.id)!!.pokemon
          store.updatePokemon(created.info.id, mon.copy(level = 4, hp = 11))

          before.single().level shouldBe 3.toByte()
          val after = store.getCharacter(created.info.id)!!.pokemon.single()
          after.level shouldBe 4.toByte()
          after.hp shouldBe 11.toShort()

          store.flushAll()
          repo.saved[created.info.id]!!.pokemon.single().level shouldBe 4.toByte()
        }
      }

      test("a failed flush keeps the character dirty and retries") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id

          store.addMoney(id, 1)
          repo.failNextSave = true
          store.flushAll()
          repo.saveCount.get() shouldBe 0

          store.flushAll()
          repo.saveCount.get() shouldBe 1
          repo.saved[id]!!.info.money shouldBe 30001
        }
      }

      test("unload persists and evicts after the flush succeeds") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), this)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id
          store.addMoney(id, 5)

          store.unloadCharacterAsync(id)
          advanceUntilIdle()

          store.getCharacter(id) shouldBe null
          repo.saved[id]!!.info.money shouldBe 30005
          store.getCharactersByUser(1).map { it.info.id } shouldBe listOf(id)
        }
      }

      test("unload keeps the character cached until a save succeeds") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), this)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id
          store.addMoney(id, 1)
          repo.failNextSave = true

          store.unloadCharacterAsync(id)
          advanceUntilIdle()
          store.getCharacter(id).shouldNotBeNull()

          store.flushAll()
          store.getCharacter(id) shouldBe null
          repo.saved[id]!!.info.money shouldBe 30001
        }
      }

      test("loading a character again cancels a pending unload") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), this)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id

          store.unloadCharacterAsync(id)
          store.getOrLoadCharacter(id).shouldNotBeNull()
          advanceUntilIdle()

          store.getCharacter(id).shouldNotBeNull()
        }
      }

      test("shutdown flushes what is still dirty") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), this)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id
          store.addMoney(id, 7)

          store.shutdown()

          repo.saved[id]!!.info.money shouldBe 30007
        }
      }

      test("getCharactersByUser loads from the repository once") {
        runTest {
          val repo = FakeCharacterRepository()
          val seedStore = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id =
              seedStore.createCharacter(7, "Misty", CharacterGender.FEMALE, Region.HOENN).info.id

          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val loaded = store.getCharactersByUser(7)
          loaded.map { it.info.id } shouldBe listOf(id)
          store.getCharacter(id).shouldNotBeNull()
        }
      }

      test("deleting an owned character removes its persisted and cached aggregate") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id = store.createCharacter(7, "Misty", CharacterGender.FEMALE, Region.HOENN).info.id

          store.deleteCharacter(userId = 7, characterId = id) shouldBe true

          repo.saved[id] shouldBe null
          store.getCharacter(id) shouldBe null
          store.getCharactersByUser(7) shouldBe emptyList()
        }
      }

      test("character deletion rejects an id owned by another user") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id = store.createCharacter(7, "Misty", CharacterGender.FEMALE, Region.HOENN).info.id

          store.deleteCharacter(userId = 8, characterId = id) shouldBe false

          repo.saved[id].shouldNotBeNull()
          store.getCharacter(id).shouldNotBeNull()
        }
      }
    })
