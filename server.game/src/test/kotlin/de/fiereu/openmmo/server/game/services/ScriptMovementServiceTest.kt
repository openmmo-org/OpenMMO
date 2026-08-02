package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.DialogDataPacket
import de.fiereu.openmmo.net.game.packets.NpcUpdatePacket
import de.fiereu.openmmo.server.game.script.MovementStep
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class ScriptMovementServiceTest :
    FunSpec({
      fun service(store: CharacterStore): ScriptMovementService {
        val mapManager = MapManager()
        return ScriptMovementService(mapManager, NpcService(mapManager, store), store)
      }

      test("drive walks one tile per step and returns the final pose") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val session = FakeSession()
          val start = ScriptMovementService.Pose(5, 5, Direction.DOWN)

          val end =
              service(store)
                  .drive(
                      session,
                      entityId = 42L,
                      start = start,
                      steps =
                          listOf(
                              MovementStep.WALK_UP, MovementStep.WALK_UP, MovementStep.WALK_LEFT),
                  )

          end shouldBe ScriptMovementService.Pose(4, 3, Direction.LEFT)
          // One movement packet carrying walk_up, walk_up, walk_left (0x11, 0x11, 0x12).
          session.sent shouldBe listOf(DialogDataPacket(42L, 0, 3, byteArrayOf(0x11, 0x11, 0x12)))
        }
      }

      test("a face step turns in place without moving") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val session = FakeSession()
          val start = ScriptMovementService.Pose(8, 2, Direction.DOWN)

          val end = service(store).drive(session, 7L, start, listOf(MovementStep.FACE_RIGHT))

          end shouldBe ScriptMovementService.Pose(8, 2, Direction.RIGHT)
          // One movement packet carrying face_right (0x03).
          session.sent shouldBe listOf(DialogDataPacket(7L, 0, 1, byteArrayOf(0x03)))
        }
      }

      test("moveSelf commits the final tile to the character store") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val created = store.createCharacter(1, "Ash", CharacterGender.MALE, Region.HOENN)
          val charId = created.info.id
          val session = FakeSession(characterId = charId)
          val state = session.attributes[de.fiereu.openmmo.server.game.session.PLAYER_STATE]!!
          val startX = created.info.positionX.toInt()
          val startY = created.info.positionY.toInt()

          service(store)
              .moveSelf(session, state, listOf(MovementStep.WALK_DOWN, MovementStep.WALK_DOWN))

          store.getCharacter(charId)!!.info.positionY.toInt() shouldBe startY + 2
          store.getCharacter(charId)!!.info.positionX.toInt() shouldBe startX
        }
      }

      test("repositionSelf sends the captured coordinate update and commits it") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val created = store.createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN)
          store.updatePosition(created.info.id, 7, 14, 50, 16)
          val session = FakeSession(characterId = created.info.id, bankId = 50, mapId = 16)
          val state = session.state()

          service(store).repositionSelf(session, state, 6, 13, Direction.UP)

          session.sent shouldBe
              listOf(
                  NpcUpdatePacket(created.info.id, 1, 50, 16, 6, 13, 0xF6, 1),
              )
          store.getCharacter(created.info.id)!!.info.positionX shouldBe 6
          store.getCharacter(created.info.id)!!.info.positionY shouldBe 13
        }
      }
    })
