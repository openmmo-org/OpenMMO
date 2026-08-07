package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.maps.MapDef
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.FaceDirectionPacket
import de.fiereu.openmmo.net.game.packets.GbaEntityMovePacket
import de.fiereu.openmmo.net.game.packets.MovementPacket
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import de.fiereu.openmmo.server.game.testsupport.movementService
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.util.Base64
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

// Our own empty map, so the test does not depend on real tile data.
private const val BANK = 120
private const val MAP = 120
private const val START_X: Short = 2
private const val START_Y: Short = 2

private fun openMapManager(): MapManager {
  val size = 5
  val map =
      MapDef(
          regionId = 1,
          bankId = BANK.toByte(),
          mapId = MAP.toByte(),
          width = size,
          height = size,
          blockData = Base64.getEncoder().encodeToString(ByteArray(size * size * 2)),
          behaviorData = Base64.getEncoder().encodeToString(ByteArray(size * size)),
      )
  return MapManager().also { it.register(map) }
}

@OptIn(ExperimentalCoroutinesApi::class)
class CutsceneMovementLockTest :
    FunSpec({
      suspend fun CharacterStore.playerAt(): Pair<FakeSession, Long> {
        val charId = createCharacter(1, "May", CharacterGender.FEMALE, Region.HOENN).info.id
        updatePosition(charId, START_X, START_Y, BANK.toByte(), MAP.toByte())
        val session = FakeSession(characterId = charId, bankId = BANK, mapId = MAP)
        session.state().x = START_X
        session.state().y = START_Y
        return session to charId
      }

      test("a running script keeps the player on its tile") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val (session, charId) = store.playerAt()
          session.state().inDialog = true

          movementService(store, openMapManager())
              .onMovement(
                  PacketEvent(
                      MovementPacket(START_X.toInt(), START_Y.toInt(), Direction.UP), session))

          val info = store.getCharacter(charId)!!.info
          info.positionX shouldBe START_X
          info.positionY shouldBe START_Y
          val reset = session.sent.filterIsInstance<GbaEntityMovePacket>().single()
          (reset.x to reset.y) shouldBe (START_X.toInt() to START_Y.toInt())
          reset.direction shouldBe Direction.DOWN
          session.state().facingDirection shouldBe Direction.DOWN
        }
      }

      test("a running script blocks turning in place") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val (session, charId) = store.playerAt()
          session.state().inDialog = true

          movementService(store, openMapManager())
              .onFaceDirection(PacketEvent(FaceDirectionPacket(Direction.LEFT), session))

          session.state().facingDirection shouldBe Direction.DOWN
          val reset = session.sent.filterIsInstance<GbaEntityMovePacket>().single()
          reset.direction shouldBe Direction.DOWN
          (reset.x to reset.y) shouldBe (START_X.toInt() to START_Y.toInt())
        }
      }

      test("the player moves again once the script is over") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val (session, charId) = store.playerAt()
          session.state().inDialog = false

          movementService(store, openMapManager())
              .onMovement(
                  PacketEvent(
                      MovementPacket(START_X.toInt(), START_Y.toInt(), Direction.UP), session))

          store.getCharacter(charId)!!.info.positionY shouldBe (START_Y - 1).toShort()
        }
      }
    })
