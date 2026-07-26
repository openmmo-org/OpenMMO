package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.EntityInteractPacket
import de.fiereu.openmmo.net.game.packets.TileInteractPacket
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptRegistry
import de.fiereu.openmmo.server.game.script.ScriptRunner
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.StoredCharacter
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

@Singleton
class InteractionService
@Inject
constructor(
    private val npcService: NpcService,
    private val mapManager: MapManager,
    private val characterStore: CharacterStore,
    private val scriptRegistry: ScriptRegistry,
    private val scriptRunner: ScriptRunner,
) {

  /** The player pressed the action button on a specific entity, that is an npc. */
  fun onEntityInteract(event: PacketEvent<EntityInteractPacket>) {
    val session = event.session
    val state = session.attributes[PLAYER_STATE] ?: return
    if (state.inDialog) return
    val stored = currentCharacter(state) ?: return
    val npcEntityId = event.packet.entityId

    val currentMap =
        mapManager.getMap(
            stored.info.positionRegionId,
            stored.info.positionBankId,
            stored.info.positionMapId,
        ) ?: return
    val bankId = stored.info.positionBankId.toInt()
    val mapId = stored.info.positionMapId.toInt()

    for (npc in currentMap.npcs) {
      if (npcService.getNpcEntityId(bankId, mapId, npc.entityIdx) == npcEntityId) {
        val script = scriptRegistry.forLabel(npc.script)
        if (script != null) {
          runScript(session, state, script, npcEntityId)
        } else {
          log.info { "NPC entityIdx=${npc.entityIdx} script=${npc.script} has no wired dialog" }
        }
        return
      }
    }
    log.info { "Entity interaction for entity $npcEntityId not found on current map" }
  }

  /** The player pressed the action button on the tile they face, a sign or a piece of furniture. */
  fun onTileInteract(event: PacketEvent<TileInteractPacket>) {
    val session = event.session
    val state = session.attributes[PLAYER_STATE] ?: return
    if (state.inDialog) return
    val stored = currentCharacter(state) ?: return

    val currentMap =
        mapManager.getMap(
            stored.info.positionRegionId,
            stored.info.positionBankId,
            stored.info.positionMapId,
        ) ?: return

    val facingX =
        when (state.facingDirection) {
          Direction.RIGHT -> stored.info.positionX.toInt() + 1
          Direction.LEFT -> stored.info.positionX.toInt() - 1
          else -> stored.info.positionX.toInt()
        }
    val facingY =
        when (state.facingDirection) {
          Direction.UP -> stored.info.positionY.toInt() - 1
          Direction.DOWN -> stored.info.positionY.toInt() + 1
          else -> stored.info.positionY.toInt()
        }
    val bgEvent =
        currentMap.bgEvents.find {
          it.x == facingX && it.y == facingY && facingDirOk(it.facingDir, state.facingDirection)
        }
    if (bgEvent == null) {
      log.debug { "Tile interaction at ($facingX, $facingY) has no bg event" }
      return
    }
    val script = scriptRegistry.forLabel(bgEvent.script)
    if (script != null) {
      runScript(session, state, script, entityId = -1)
    } else {
      log.info { "Bg event at ($facingX, $facingY) script=${bgEvent.script} has no wired dialog" }
    }
  }

  private fun currentCharacter(state: PlayerState): StoredCharacter? {
    val charId = state.characterId ?: return null
    return characterStore.getCharacter(charId)
  }

  private fun facingDirOk(eventDir: String, facing: Direction): Boolean =
      eventDir == "BG_EVENT_PLAYER_FACING_ANY" ||
          (eventDir == "BG_EVENT_PLAYER_FACING_NORTH" && facing == Direction.UP) ||
          (eventDir == "BG_EVENT_PLAYER_FACING_SOUTH" && facing == Direction.DOWN) ||
          (eventDir == "BG_EVENT_PLAYER_FACING_WEST" && facing == Direction.LEFT) ||
          (eventDir == "BG_EVENT_PLAYER_FACING_EAST" && facing == Direction.RIGHT)

  private fun runScript(
      session: SessionContext,
      state: PlayerState,
      script: Script,
      entityId: Long,
  ) = scriptRunner.run(session, state, script, entityId)
}
