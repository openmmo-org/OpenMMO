package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_UP
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

// Oak's lab, where the starter cutscene walks the player eight tiles up from the door.
private const val KANTO: Byte = 0
private const val INDOOR_PALLET_BANK: Byte = 4
private const val OAKS_LAB_MAP: Byte = 3

@OptIn(ExperimentalCoroutinesApi::class)
class ScriptMovementBoundsTest :
    FunSpec({
      fun service(store: CharacterStore): ScriptMovementService {
        val maps = MapManager()
        return ScriptMovementService(maps, NpcService(maps, store), store)
      }

      suspend fun CharacterStore.labCharacter(x: Short, y: Short): Long {
        val id = createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
        updatePosition(id, x, y, INDOOR_PALLET_BANK, OAKS_LAB_MAP)
        return id
      }

      test("a scripted walk inside the map commits its final tile") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.labCharacter(x = 6, y = 12)
          val session =
              FakeSession(
                  characterId = charId,
                  regionId = KANTO.toInt(),
                  bankId = INDOOR_PALLET_BANK.toInt(),
                  mapId = OAKS_LAB_MAP.toInt(),
              )

          service(store).moveSelf(session, session.state(), List(8) { WALK_UP })
          advanceUntilIdle()

          store.getCharacter(charId)!!.info.positionY shouldBe 4
        }
      }

      test("a scripted walk that would leave the map commits nothing") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          // Where an interrupted starter cutscene leaves the player before it replays.
          val charId = store.labCharacter(x = 6, y = 4)
          val session =
              FakeSession(
                  characterId = charId,
                  regionId = KANTO.toInt(),
                  bankId = INDOOR_PALLET_BANK.toInt(),
                  mapId = OAKS_LAB_MAP.toInt(),
              )

          service(store).moveSelf(session, session.state(), List(8) { WALK_UP })
          advanceUntilIdle()

          store.getCharacter(charId)!!.info.positionY shouldBe 4
        }
      }

      test("repositioning off the map is refused and sends nothing") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.labCharacter(x = 6, y = 4)
          val session =
              FakeSession(
                  characterId = charId,
                  regionId = KANTO.toInt(),
                  bankId = INDOOR_PALLET_BANK.toInt(),
                  mapId = OAKS_LAB_MAP.toInt(),
              )

          service(store).repositionSelf(session, session.state(), 99, 99, Direction.DOWN)

          store.getCharacter(charId)!!.info.positionX shouldBe 6
          session.sent shouldBe emptyList()
        }
      }
    })
