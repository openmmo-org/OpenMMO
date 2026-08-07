package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.DynamicWarp
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.test.DockerAvailable
import de.fiereu.openmmo.db.game.tables.references.CHARACTER_ITEMS
import de.fiereu.openmmo.db.game.tables.references.POKEMON
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.annotation.EnabledIf
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.Dispatchers
import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.testcontainers.containers.PostgreSQLContainer

@EnabledIf(DockerAvailable::class)
class CharacterRepositoryIT :
    FunSpec({
      val container = PostgreSQLContainer<Nothing>("postgres:18")
      val entityIds = EntityIdService()
      lateinit var repository: JooqCharacterRepository
      lateinit var dsl: DSLContext

      // Postgres timestamps have microsecond precision, so nanos would not round-trip.
      fun now(): LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)

      fun monster(ownerId: Long, container: PokemonContainer, slot: Short): Pokemon =
          Pokemon(
              id = entityIds.newMonsterId(),
              ownerId = ownerId,
              container = container,
              containerSlot = slot,
              dexId = 495,
              seed = 12345,
              ot = "Ash",
              nickname = "Smugleaf",
              level = 5,
              hp = 20,
              xp = 165,
              eVs = EVs().also { it.hp = 4 },
              iVs = IVs().also { it.atk = 31 },
              moves =
                  listOf(
                      PokemonMove(33, 35),
                      PokemonMove(43, 30),
                      PokemonMove(0, 0),
                      PokemonMove(0, 0),
                  ),
              isShiny = true,
              hasHiddenAbility = false,
              isAlpha = false,
              isSecret = false,
              isFatefulEncounter = true,
              isRaidEncounter = false,
              caughtAt = now(),
          )

      // Each test uses its own userId. User 1 belongs to the seeded dev character.
      fun aggregate(userId: Int): StoredCharacter {
        val id = entityIds.newCharacterId()
        val info =
            CharacterInfo(
                id = id,
                name = "Ash",
                namePrefix = "",
                userId = userId,
                rivalSex = 1,
                lastLogin = now(),
                createdAt = now(),
                money = 30000,
                permissions = 8,
                remainingSafariSteps = 12,
                remainingSafariBalls = 3,
                pcExtraSlots = 0,
                battleBoxExtraSlots = 0,
                templateAmount = 0,
                positionRegionId = 1,
                positionBankId = 51,
                positionMapId = 3,
                positionX = 4,
                positionY = 4,
                repelLeft = 0,
                repelItemId = 0,
                lureLeft = 0,
                lureItemId = 0,
            )
        return StoredCharacter(
            info = info,
            pokemon = mutableListOf(monster(id, PokemonContainer.PARTY, 0)),
            pcStorage = mutableListOf(monster(id, PokemonContainer.PC, 0)),
            items = mutableMapOf(4 to 10),
        )
      }

      // An all-zero stats monster, like the seeded starters. Hydration must not choke on it.
      fun zeroStatsMonster(ownerId: Long): Pokemon =
          monster(ownerId, PokemonContainer.PARTY, 1)
              .copy(eVs = EVs(), iVs = IVs(), isShiny = false, isFatefulEncounter = false)

      beforeSpec {
        container.start()
        Flyway.configure()
            .dataSource(container.jdbcUrl, container.username, container.password)
            .locations("classpath:db/migration", "classpath:db/dev")
            .load()
            .migrate()
        dsl = DSL.using(container.jdbcUrl, container.username, container.password)
        repository = JooqCharacterRepository(dsl, Dispatchers.IO)
      }

      afterSpec { container.stop() }

      test("insert and load round-trips the whole aggregate") {
        val stored = aggregate(userId = 60)
        stored.pokemon.add(zeroStatsMonster(stored.info.id))
        repository.insertAggregate(stored)

        val loaded = repository.loadById(stored.info.id).shouldNotBeNull()
        loaded.info shouldBe stored.info
        loaded.pokemon shouldBe stored.pokemon
        loaded.pcStorage shouldBe stored.pcStorage
        loaded.items shouldBe stored.items
        (loaded.info.id and 0xFFFF) shouldBe CHARACTER_ID_TAG
      }

      test("loadByUser returns only that user's characters") {
        val mine = aggregate(userId = 77)
        val other = aggregate(userId = 78)
        repository.insertAggregate(mine)
        repository.insertAggregate(other)

        repository.loadByUser(77).map { it.info.id } shouldBe listOf(mine.info.id)
      }

      test("saveChanges writes the character, pokemon, and items that changed") {
        val stored = aggregate(userId = 90)
        repository.insertAggregate(stored)

        val kept = stored.pokemon.single()
        val added = monster(stored.info.id, PokemonContainer.PARTY, 1)
        val current =
            stored.copy(
                info = stored.info.copy(money = 999, positionX = 12),
                pokemon = mutableListOf(kept.copy(hp = 3), added),
                pcStorage = mutableListOf(),
                items = mutableMapOf(4 to 9, 13 to 1),
            )
        repository.saveChanges(stored, current)

        val loaded = repository.loadById(stored.info.id).shouldNotBeNull()
        loaded.info.money shouldBe 999
        loaded.info.positionX shouldBe 12
        loaded.pokemon.map { it.id } shouldBe listOf(kept.id, added.id)
        loaded.pokemon.first().hp shouldBe 3
        loaded.pcStorage.shouldBeEmpty()
        loaded.items shouldBe mapOf(4 to 9, 13 to 1)
      }

      test("a character only change leaves the pokemon and item rows alone") {
        val stored = aggregate(userId = 93)
        repository.insertAggregate(stored)
        // A row that gets rewritten loses these.
        dsl.update(POKEMON)
            .set(POKEMON.NICKNAME, "sentinel")
            .where(POKEMON.OWNER_ID.eq(stored.info.id))
            .execute()
        dsl.update(CHARACTER_ITEMS)
            .set(CHARACTER_ITEMS.QUANTITY, 4242)
            .where(CHARACTER_ITEMS.CHARACTER_ID.eq(stored.info.id))
            .execute()

        repository.saveChanges(stored, stored.copy(info = stored.info.copy(money = 1)))

        val loaded = repository.loadById(stored.info.id).shouldNotBeNull()
        loaded.info.money shouldBe 1
        loaded.pokemon.single().nickname shouldBe "sentinel"
        loaded.pcStorage.single().nickname shouldBe "sentinel"
        loaded.items shouldBe mapOf(4 to 4242)
      }

      test("saveChanges drops released monsters, spent items, and cleared story state") {
        val stored =
            aggregate(userId = 94).let {
              it.copy(storyFlags = mutableSetOf("a", "b"), storyVars = mutableMapOf("v" to 7))
            }
        repository.insertAggregate(stored)

        val current =
            stored.copy(
                pokemon = mutableListOf(),
                items = mutableMapOf(),
                storyFlags = mutableSetOf("b"),
                storyVars = mutableMapOf(),
            )
        repository.saveChanges(stored, current)

        val loaded = repository.loadById(stored.info.id).shouldNotBeNull()
        loaded.pokemon.shouldBeEmpty()
        loaded.pcStorage.size shouldBe 1
        loaded.items.shouldBeEmpty()
        loaded.storyFlags shouldBe setOf("b")
        loaded.storyVars.shouldBeEmpty()
      }

      test("saveChanges clears a dynamic warp") {
        val stored =
            aggregate(userId = 95).let {
              it.copy(info = it.info.copy(dynamicWarp = DynamicWarp(1, 50, 9, 3, 10, Direction.UP)))
            }
        repository.insertAggregate(stored)

        repository.saveChanges(stored, stored.copy(info = stored.info.copy(dynamicWarp = null)))

        repository.loadById(stored.info.id).shouldNotBeNull().info.dynamicWarp.shouldBeNull()
      }

      test("saveChanges without a base writes every row") {
        val stored = aggregate(userId = 96)

        repository.saveChanges(null, stored)

        val loaded = repository.loadById(stored.info.id).shouldNotBeNull()
        loaded.pokemon shouldBe stored.pokemon
        loaded.items shouldBe stored.items
      }

      test("loadById returns null for an unknown id") {
        repository.loadById(entityIds.newCharacterId()).shouldBeNull()
      }

      test("a duplicate container slot is rejected") {
        val stored = aggregate(userId = 92)
        stored.pokemon.add(monster(stored.info.id, PokemonContainer.PARTY, 0))
        shouldThrowAny { repository.insertAggregate(stored) }
      }

      test("the seed migration provides the dev characters with their starters") {
        val seeded = repository.loadByUser(1)
        seeded.map { it.info.name } shouldBe listOf("Test")
        seeded.single().pokemon.map { it.dexId } shouldBe listOf(1, 19)
      }
    })
