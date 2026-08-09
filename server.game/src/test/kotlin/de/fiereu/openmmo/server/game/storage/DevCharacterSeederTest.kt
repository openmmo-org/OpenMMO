package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.CharacterPermissions
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.common.hasPermission
import de.fiereu.openmmo.server.game.config.ConfigLoader
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class DevCharacterSeederTest :
    FunSpec({
      val config = ConfigLoader.load()

      test("seeds one developer character per region on its new game start") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)

          DevCharacterSeeder(store, config).seed()

          val seeded = store.getCharactersByUser(1)
          seeded.size shouldBe Region.entries.size
          Region.entries.forEach { region ->
            val start = NewGameStarts.forRegion(region, female = false)
            val character = seeded.single { it.info.positionRegionId == region.wireValue }
            character.info.positionBankId shouldBe start.bankId
            character.info.positionMapId shouldBe start.mapId
            character.info.positionX shouldBe start.x
            character.info.positionY shouldBe start.y
            character.storyFlags shouldBe start.storyFlags
            character.info.hasPermission(CharacterPermissions.DEVELOPER) shouldBe true
          }
        }
      }

      test("running twice does not add a second set") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val seeder = DevCharacterSeeder(store, config)

          seeder.seed()
          seeder.seed()

          store.getCharactersByUser(1).size shouldBe Region.entries.size
        }
      }
    })
