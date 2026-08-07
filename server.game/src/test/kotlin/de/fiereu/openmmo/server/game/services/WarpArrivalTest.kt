package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.DynamicWarp
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.maps.WarpTile
import de.fiereu.openmmo.net.game.packets.LoadMapPacket
import de.fiereu.openmmo.net.game.packets.RenderScreenPacket
import de.fiereu.openmmo.server.game.session.PENDING_MAP_LOAD
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

// No map is registered here, so a warp aimed at it cannot complete.
private const val MISSING_BANK: Byte = 120
private const val MISSING_MAP: Byte = 120

@OptIn(ExperimentalCoroutinesApi::class)
class WarpArrivalTest :
    FunSpec({
      test("a warp gates movement until the client asks for its player") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN).info.id
          val session = FakeSession(characterId = charId, bankId = 51, mapId = 3)
          val mapManager = MapManager()
          val warps = WarpService(MapLoadService(mapManager), mapManager, store)

          warps.executeWarp(
              session,
              charId,
              WarpTile(
                  x = 0,
                  y = 0,
                  targetRegionId = 1,
                  targetBankId = 50,
                  targetMapId = 9,
                  targetX = 5,
                  targetY = 8,
              ),
          )

          session.state().justWarped shouldBe true
          session.sent.filterIsInstance<RenderScreenPacket>() shouldBe
              listOf(RenderScreenPacket(false))
        }
      }

      test("a warp to a map that is not loaded does not leave the player gated") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN).info.id
          val session = FakeSession(characterId = charId, bankId = 51, mapId = 3)
          val mapManager = MapManager()
          mapManager.getMap(1, MISSING_BANK, MISSING_MAP) shouldBe null
          val warps = WarpService(MapLoadService(mapManager), mapManager, store)

          warps.executeWarp(
              session,
              charId,
              WarpTile(
                  x = 0,
                  y = 0,
                  targetRegionId = 1,
                  targetBankId = MISSING_BANK,
                  targetMapId = MISSING_MAP,
                  targetX = 0,
                  targetY = 0,
              ),
          )

          session.state().justWarped shouldBe false
          session.sent.filterIsInstance<RenderScreenPacket>().last() shouldBe
              RenderScreenPacket(true)
        }
      }

      test("a warp for an unknown character does not gate movement") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val session = FakeSession(characterId = 404)
          val mapManager = MapManager()
          val warps = WarpService(MapLoadService(mapManager), mapManager, store)

          warps.executeWarp(
              session,
              404,
              WarpTile(
                  x = 0,
                  y = 0,
                  targetRegionId = 1,
                  targetBankId = 50,
                  targetMapId = 9,
                  targetX = 5,
                  targetY = 8,
              ),
          )

          session.state().justWarped shouldBe false
          session.sent shouldBe emptyList()
        }
      }

      test("a scripted warp resumes once the client has loaded the map") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN).info.id
          val session = FakeSession(characterId = charId, bankId = 51, mapId = 3)
          val mapManager = MapManager()
          val warps = ScriptWarpService(mapManager, MapLoadService(mapManager), store)

          val cutscene = launch { warps.warp(session, session.state(), littlerootTown()) }
          // Not advanceUntilIdle, that would run the clock past the load timeout.
          runCurrent()

          session.state().justWarped shouldBe true
          session.sent.filterIsInstance<LoadMapPacket>().isEmpty() shouldBe false

          // What onRequestPlayer does on arrival.
          session.attributes.remove(PENDING_MAP_LOAD)!!.complete(Unit)
          runCurrent()
          cutscene.join()

          session.attributes[PENDING_MAP_LOAD] shouldBe null
          session.sent.filterIsInstance<RenderScreenPacket>() shouldBe
              listOf(RenderScreenPacket(false))
        }
      }

      test("a scripted warp the client never answers gives movement back") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN).info.id
          val session = FakeSession(characterId = charId, bankId = 51, mapId = 3)
          val mapManager = MapManager()
          val warps = ScriptWarpService(mapManager, MapLoadService(mapManager), store)

          val cutscene = launch { warps.warp(session, session.state(), littlerootTown()) }
          advanceUntilIdle()
          cutscene.join()

          session.state().justWarped shouldBe false
          session.attributes[PENDING_MAP_LOAD] shouldBe null
          session.sent.filterIsInstance<RenderScreenPacket>().last() shouldBe
              RenderScreenPacket(true)
        }
      }
    })

private fun littlerootTown() =
    DynamicWarp(
        regionId = 1,
        bankId = 50,
        mapId = 9,
        x = 5,
        y = 8,
        facing = Direction.DOWN,
    )
