package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.DynamicWarp
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.maps.WarpTile
import de.fiereu.openmmo.net.game.packets.EntityLeavePacket
import de.fiereu.openmmo.net.game.packets.LoadMapPacket
import de.fiereu.openmmo.net.game.packets.RenderScreenPacket
import de.fiereu.openmmo.server.game.session.PENDING_MAP_LOAD
import de.fiereu.openmmo.server.game.session.SCRIPT_SCOPE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.server.game.world.interest.PassThroughInterestPolicy
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

// No map is registered here, so a warp aimed at it cannot complete.
private const val MISSING_BANK: Byte = 120
private const val MISSING_MAP: Byte = 120

// Longer than either warp service waits for the client.
private val ARRIVAL_DEADLINE_PASSED = 15.seconds

private fun littlerootTown() =
    DynamicWarp(
        regionId = 1,
        bankId = 50,
        mapId = 9,
        x = 5,
        y = 8,
        facing = Direction.DOWN,
    )

private fun warpTo(bankId: Byte, mapId: Byte) =
    WarpTile(
        x = 0,
        y = 0,
        targetRegionId = 1,
        targetBankId = bankId,
        targetMapId = mapId,
        targetX = 5,
        targetY = 8,
    )

// What codegen emits for a MAP_DYNAMIC tile. Every target is a placeholder.
private fun dynamicWarpTile() =
    WarpTile(
        x = 4,
        y = 2,
        targetRegionId = 0,
        targetBankId = 0,
        targetMapId = 0,
        targetX = 0,
        targetY = 0,
        dynamic = true,
    )

@OptIn(ExperimentalCoroutinesApi::class)
class WarpArrivalTest :
    FunSpec({
      /** Every service a warp touches, sharing one interest manager. */
      class Fixture(store: CharacterStore) {
        val mapManager = MapManager()
        val mapLoad = MapLoadService(mapManager)
        val presence =
            PresenceService(InterestManager(), PassThroughInterestPolicy(), mapLoad, store)
        val warps = WarpService(mapLoad, mapManager, store, presence)
        val scriptWarps = ScriptWarpService(mapManager, mapLoad, store, presence)
      }

      /** The warp deadline runs on the session's scope, which a disconnect cancels. */
      fun FakeSession.useScope(scope: CoroutineScope) = also { attributes[SCRIPT_SCOPE] = scope }

      suspend fun CharacterStore.player(name: String = "May") =
          createCharacter(1, name, CharacterGender.FEMALE, Region.HOENN).info.id

      test("a warp gates movement until the client asks for its player") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val session =
              FakeSession(characterId = charId, bankId = 51, mapId = 3).useScope(backgroundScope)
          val f = Fixture(store)

          f.warps.executeWarp(session, charId, warpTo(50, 9))
          runCurrent()

          session.state().justWarped shouldBe true
          session.sent.filterIsInstance<RenderScreenPacket>() shouldBe
              listOf(RenderScreenPacket(false))
        }
      }

      test("a warp the client never answers gives movement back") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val session =
              FakeSession(characterId = charId, bankId = 51, mapId = 3).useScope(backgroundScope)
          val f = Fixture(store)

          f.warps.executeWarp(session, charId, warpTo(50, 9))
          advanceTimeBy(ARRIVAL_DEADLINE_PASSED)
          runCurrent()

          session.state().justWarped shouldBe false
          session.attributes[PENDING_MAP_LOAD] shouldBe null
          session.sent.filterIsInstance<RenderScreenPacket>().last() shouldBe
              RenderScreenPacket(true)
        }
      }

      test("an arrival cancels the deadline of the warp it belongs to") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val session =
              FakeSession(characterId = charId, bankId = 51, mapId = 3).useScope(backgroundScope)
          val f = Fixture(store)

          f.warps.executeWarp(session, charId, warpTo(50, 9))
          runCurrent()
          // What onRequestPlayer does on arrival.
          session.state().justWarped = false
          session.attributes.remove(PENDING_MAP_LOAD)!!.complete(Unit)
          advanceUntilIdle()

          session.state().justWarped shouldBe false
          session.sent.filterIsInstance<RenderScreenPacket>() shouldBe
              listOf(RenderScreenPacket(false))
        }
      }

      test("a second warp keeps the gate its predecessor's deadline would have opened") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val session =
              FakeSession(characterId = charId, bankId = 51, mapId = 3).useScope(backgroundScope)
          val f = Fixture(store)

          f.warps.executeWarp(session, charId, warpTo(50, 9))
          runCurrent()
          val first = session.attributes[PENDING_MAP_LOAD]
          f.warps.executeWarp(session, charId, warpTo(51, 3))
          runCurrent()
          (session.attributes[PENDING_MAP_LOAD] === first) shouldBe false

          // The second warp arrives, then the first one's deadline passes.
          session.state().justWarped = false
          session.attributes.remove(PENDING_MAP_LOAD)!!.complete(Unit)
          session.sent.clear()
          advanceTimeBy(ARRIVAL_DEADLINE_PASSED)
          runCurrent()

          session.sent shouldBe emptyList()
        }
      }

      test("a warp to a map that is not loaded leaves the player where it was") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val session =
              FakeSession(characterId = charId, bankId = 51, mapId = 3).useScope(backgroundScope)
          val f = Fixture(store)
          f.mapManager.getMap(1, MISSING_BANK, MISSING_MAP) shouldBe null
          val before = store.getCharacter(charId)!!.info

          f.warps.executeWarp(session, charId, warpTo(MISSING_BANK, MISSING_MAP))
          advanceUntilIdle()

          session.state().justWarped shouldBe false
          session.state().mapId shouldBe 3
          store.getCharacter(charId)!!.info.positionMapId shouldBe before.positionMapId
          session.sent shouldBe emptyList()
        }
      }

      test("a dynamic warp goes where the script pointed it, not to its placeholder target") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val session =
              FakeSession(characterId = charId, bankId = 75, mapId = 40).useScope(backgroundScope)
          val f = Fixture(store)
          val target = store.getCharacter(charId)!!.info.dynamicWarp!!

          f.warps.executeWarp(session, charId, dynamicWarpTile())
          runCurrent()

          val info = store.getCharacter(charId)!!.info
          info.positionRegionId shouldBe target.regionId
          info.positionBankId shouldBe target.bankId
          info.positionMapId shouldBe target.mapId
          info.positionX shouldBe target.x
          info.positionY shouldBe target.y
          session.state().facingDirection shouldBe target.facing
        }
      }

      test("a dynamic warp with no destination set leaves the player where it was") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val session = FakeSession(characterId = charId).useScope(backgroundScope)
          val f = Fixture(store)
          val before = store.getCharacter(charId)!!.info

          f.warps.executeWarp(session, charId, dynamicWarpTile())
          advanceUntilIdle()

          session.state().justWarped shouldBe false
          store.getCharacter(charId)!!.info shouldBe before
          session.sent shouldBe emptyList()
        }
      }

      test("a warp for an unknown character does not gate movement") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val session = FakeSession(characterId = 404).useScope(backgroundScope)
          val f = Fixture(store)

          f.warps.executeWarp(session, 404, warpTo(50, 9))
          advanceUntilIdle()

          session.state().justWarped shouldBe false
          session.sent shouldBe emptyList()
        }
      }

      test("a disconnect during the transition cancels the deadline") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val scope = CoroutineScope(backgroundScope.coroutineContext + Job())
          val session = FakeSession(characterId = charId, bankId = 51, mapId = 3).useScope(scope)
          val f = Fixture(store)

          f.warps.executeWarp(session, charId, warpTo(50, 9))
          runCurrent()
          // What onInactive does.
          scope.cancel()
          session.sent.clear()
          advanceTimeBy(ARRIVAL_DEADLINE_PASSED)
          runCurrent()

          session.sent shouldBe emptyList()
        }
      }

      test("a warp carries the elevation of its destination to the arrival") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val session =
              FakeSession(characterId = charId, bankId = 51, mapId = 3).useScope(backgroundScope)
          val f = Fixture(store)
          val stairs = f.mapManager.getMap(1, 51, 3)!!.warps.first()

          f.warps.executeWarp(
              session,
              charId,
              warpTo(51, 3).copy(targetX = stairs.x, targetY = stairs.y, targetElevation = 3),
          )
          runCurrent()

          session.state().elevation shouldBe (stairs.elevation)
        }
      }

      test("warping despawns the player from the map it left right away") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player("May")
          val session =
              FakeSession(characterId = charId, bankId = 51, mapId = 3).useScope(backgroundScope)
          val watcher = FakeSession(characterId = store.player("Brendan"), bankId = 51, mapId = 3)
          val f = Fixture(store)
          f.presence.enter(session)
          f.presence.enter(watcher)
          watcher.sent.clear()

          f.warps.executeWarp(session, charId, warpTo(50, 9))
          runCurrent()

          watcher.sent shouldBe listOf(EntityLeavePacket(charId))
        }
      }

      test("arriving on the same map still re-exchanges entity snapshots") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player("May")
          val session =
              FakeSession(characterId = charId, bankId = 51, mapId = 3).useScope(backgroundScope)
          val watcher = FakeSession(characterId = store.player("Brendan"), bankId = 51, mapId = 3)
          val f = Fixture(store)
          f.presence.enter(session)
          f.presence.enter(watcher)
          session.sent.clear()

          // The arrival re-enters the map the player never really left.
          f.presence.enter(session)

          session.sent.map { it::class } shouldBe listOf(watcher.sent.first()::class)
        }
      }

      test("a scripted warp resumes once the client has loaded the map") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val session =
              FakeSession(characterId = charId, bankId = 51, mapId = 3).useScope(backgroundScope)
          val f = Fixture(store)

          val cutscene = launch { f.scriptWarps.warp(session, session.state(), littlerootTown()) }
          // Not advanceUntilIdle, that would run the clock past the load timeout.
          runCurrent()

          session.state().justWarped shouldBe true
          session.sent.filterIsInstance<LoadMapPacket>().isEmpty() shouldBe false

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
          val charId = store.player()
          val session =
              FakeSession(characterId = charId, bankId = 51, mapId = 3).useScope(backgroundScope)
          val f = Fixture(store)

          val cutscene = launch { f.scriptWarps.warp(session, session.state(), littlerootTown()) }
          advanceUntilIdle()
          cutscene.join()

          session.state().justWarped shouldBe false
          session.attributes[PENDING_MAP_LOAD] shouldBe null
          session.sent.filterIsInstance<RenderScreenPacket>().last() shouldBe
              RenderScreenPacket(true)
        }
      }

      test("a scripted warp to a map that is not loaded does nothing") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.player()
          val session =
              FakeSession(characterId = charId, bankId = 51, mapId = 3).useScope(backgroundScope)
          val f = Fixture(store)

          f.scriptWarps.warp(
              session,
              session.state(),
              littlerootTown().copy(bankId = MISSING_BANK, mapId = MISSING_MAP),
          )

          session.state().justWarped shouldBe false
          session.sent shouldBe emptyList()
        }
      }
    })
