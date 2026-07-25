package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.EntityInteractPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
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
) {

  fun onEntityInteract(event: PacketEvent<EntityInteractPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return

    val npcEntityId = event.packet.entityId
    val stored = characterStore.getCharacter(charId) ?: return
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
        log.info {
          "Entity interaction: NPC entityIdx=${npc.entityIdx} entityId=$npcEntityId script=${npc.script}"
        }
        return
      }
    }

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
    val facingDirOk: (String) -> Boolean = { dir ->
      dir == "BG_EVENT_PLAYER_FACING_ANY" ||
          (dir == "BG_EVENT_PLAYER_FACING_NORTH" && state.facingDirection == Direction.UP) ||
          (dir == "BG_EVENT_PLAYER_FACING_SOUTH" && state.facingDirection == Direction.DOWN) ||
          (dir == "BG_EVENT_PLAYER_FACING_WEST" && state.facingDirection == Direction.LEFT) ||
          (dir == "BG_EVENT_PLAYER_FACING_EAST" && state.facingDirection == Direction.RIGHT)
    }
    val bgEvent =
        currentMap.bgEvents.find { it.x == facingX && it.y == facingY && facingDirOk(it.facingDir) }
    if (bgEvent != null) {
      log.info { "Entity interaction: bg event at ($facingX, $facingY) script=${bgEvent.script}" }
      return
    }

    log.info { "Entity interaction for entity $npcEntityId not found on current map" }
  }
}
