package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterStoreCacheTest :
    FunSpec({
      test("createCharacter inserts the aggregate into the repository") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val created = store.createCharacter(1, "Ash")
          repo.saved[created.info.id].shouldNotBeNull()
          repo.saved[created.info.id]!!.pokemon.size shouldBe 2
        }
      }

      test("mutations flush through flushAll") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id = store.createCharacter(1, "Ash").info.id

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
          val id = store.createCharacter(1, "Ash").info.id

          store.addMoney(id, 5000)

          store.getCharacter(id)!!.info.money shouldBe 35000
          repo.saved[id]!!.info.money shouldBe 30000
        }
      }

      test("addPokemon copies instead of mutating the cached list") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val created = store.createCharacter(1, "Ash")
          val before = store.getCharacter(created.info.id)!!.pokemon

          val extra = before.first().copy(id = EntityIdService().newMonsterId(), containerSlot = 2)
          store.addPokemon(created.info.id, extra)

          before.size shouldBe 2
          store.getCharacter(created.info.id)!!.pokemon.size shouldBe 3
        }
      }

      test("a failed flush keeps the character dirty and retries") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id = store.createCharacter(1, "Ash").info.id

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
          val id = store.createCharacter(1, "Ash").info.id
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
          val id = store.createCharacter(1, "Ash").info.id
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
          val id = store.createCharacter(1, "Ash").info.id

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
          val id = store.createCharacter(1, "Ash").info.id
          store.addMoney(id, 7)

          store.shutdown()

          repo.saved[id]!!.info.money shouldBe 30007
        }
      }

      test("getCharactersByUser loads from the repository once") {
        runTest {
          val repo = FakeCharacterRepository()
          val seedStore = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id = seedStore.createCharacter(7, "Misty").info.id

          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val loaded = store.getCharactersByUser(7)
          loaded.map { it.info.id } shouldBe listOf(id)
          store.getCharacter(id).shouldNotBeNull()
        }
      }
    })
