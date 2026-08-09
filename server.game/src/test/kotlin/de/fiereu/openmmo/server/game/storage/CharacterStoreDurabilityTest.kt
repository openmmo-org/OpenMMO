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
import kotlinx.coroutines.test.runTest

/**
 * Anything a player can trade or spend has to be in the database before the call returns. Position
 * and hp may wait for a checkpoint, because replaying a few seconds of walking costs nothing, while
 * an item the client has already been told about must not disappear in a crash.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CharacterStoreDurabilityTest :
    FunSpec({
      test("granting an item persists it without waiting for a flush") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id

          store.addItem(id, itemId = 17, amount = 3)

          repo.saved[id]!!.items[17] shouldBe 3
        }
      }

      test("spending money persists before the call returns") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id

          store.addMoney(id, -500)

          repo.saved[id]!!.info.money shouldBe 29500
        }
      }

      test("acquiring a monster persists it before the call returns") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val created = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN)

          store.addPokemon(created.info.id, caughtMonster(created.info.id))

          repo.saved[created.info.id]!!.pokemon.size shouldBe 1
        }
      }

      test("walking does not pay for a write, it waits for the checkpoint") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val created = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN)
          val before = repo.saveCount.get()

          store.updateCharacter(created.info.copy(positionX = 42))

          repo.saveCount.get() shouldBe before
          store.flushAll()
          repo.saved[created.info.id]!!.info.positionX shouldBe 42
        }
      }

      test("a refused item change neither mutates nor writes") {
        runTest {
          val repo = FakeCharacterRepository()
          val store = CharacterStore(repo, EntityIdService(), backgroundScope)
          val id = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN).info.id
          store.addItem(id, itemId = 17, amount = 1)
          val writes = repo.saveCount.get()

          store.addItem(id, itemId = 17, amount = -5) shouldBe false

          repo.saveCount.get() shouldBe writes
          store.getCharacter(id).shouldNotBeNull().items[17] shouldBe 1
        }
      }
    })

private fun caughtMonster(ownerId: Long): Pokemon =
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
