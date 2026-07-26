package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.EntityFaceTurnPacket
import de.fiereu.openmmo.net.game.packets.EntityMovePacket
import de.fiereu.openmmo.server.game.script.MovementStep
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.storage.CharacterStore
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.delay

/**
 * Drives scripted overworld movement (the decomp applymovement/waitmovement). Steps play one tile
 * at a time with a short delay between them, so the call only returns once the whole path is done,
 * which is exactly waitmovement.
 *
 * Story cutscenes are per player, so the movement is sent to the acting player only and is not
 * broadcast to other players who happen to share the map. It reuses [EntityMovePacket], the same
 * packet the server already uses to show other entities walking.
 */
@Singleton
class ScriptMovementService
@Inject
constructor(
    private val mapManager: MapManager,
    private val npcService: NpcService,
    private val characterStore: CharacterStore,
) {
  data class Pose(val x: Int, val y: Int, val facing: Direction)

  /** Walk a map npc (its decomp local id, that is its entityIdx) through [steps] for the player. */
  suspend fun moveNpc(
      session: SessionContext,
      state: PlayerState,
      localId: Int,
      steps: List<MovementStep>,
  ) {
    val charId = state.characterId ?: return
    val info = characterStore.getCharacter(charId)?.info ?: return
    val map =
        mapManager.getMap(info.positionRegionId, info.positionBankId, info.positionMapId) ?: return
    val npc = map.npcs.firstOrNull { it.entityIdx == localId } ?: return
    val entityId =
        npcService.getNpcEntityId(info.positionBankId.toInt(), info.positionMapId.toInt(), localId)
            ?: return
    drive(session, entityId, Pose(npc.x, npc.y, npc.facing), steps)
  }

  /** Walk the player's own avatar through [steps] and commit the final tile as authoritative. */
  suspend fun moveSelf(
      session: SessionContext,
      state: PlayerState,
      steps: List<MovementStep>,
  ) {
    val charId = state.characterId ?: return
    val info = characterStore.getCharacter(charId)?.info ?: return
    val start = Pose(info.positionX.toInt(), info.positionY.toInt(), state.facingDirection)
    val end = drive(session, info.id, start, steps)
    // Keep the server position in step with where the cutscene left the player, otherwise the next
    // move packet from the client looks like a desync and the player is snapped back.
    characterStore.updatePosition(charId, end.x.toShort(), end.y.toShort())
    state.x = end.x.toShort()
    state.y = end.y.toShort()
    state.facingDirection = end.facing
  }

  /** Sends one packet per step from [start] and waits between them. Returns the final pose. */
  suspend fun drive(
      session: SessionContext,
      entityId: Long,
      start: Pose,
      steps: List<MovementStep>,
  ): Pose {
    var pose = start
    for (step in steps) {
      pose =
          if (step.walks) {
            val nx = pose.x + step.direction.dx
            val ny = pose.y + step.direction.dy
            session.send(EntityMovePacket(entityId, nx, ny, step.direction))
            delay(WALK_STEP_MS)
            Pose(nx, ny, step.direction)
          } else {
            session.send(EntityFaceTurnPacket(entityId, step.direction.ordinal.toByte()))
            delay(FACE_STEP_MS)
            pose.copy(facing = step.direction)
          }
    }
    return pose
  }

  private companion object {
    // Rough GBA step timings, tune against the client if the animation and server drift apart.
    const val WALK_STEP_MS = 250L
    const val FACE_STEP_MS = 120L
  }
}
