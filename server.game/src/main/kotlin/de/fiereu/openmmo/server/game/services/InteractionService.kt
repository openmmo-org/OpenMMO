package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.DialogStatePacket
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
    private val dialogService: DialogService,
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
      val expectedId = npcService.getNpcEntityId(bankId, mapId, npc.entityIdx)
      if (expectedId == npcEntityId) {
        log.info {
          "Entity interaction: NPC entityIdx=${npc.entityIdx} entityId=$npcEntityId script=${npc.script}"
        }

        if (npc.script != "0x0") {
          val params = dialogService.scriptParams(npc.script)
          if (params != null) {
            state.inDialog = true
            state.dialogNpcEntityId = npcEntityId
            state.dialogPages = params
            state.dialogPageIndex = 0
            val seqId = state.dialogSeqId++
            val page = params[0]
            ctx.send(DialogStatePacket(true))
            dialogService.sendInteractive(
                ctx,
                seqId,
                npcEntityId,
                page.type,
                page.unk1,
                page.unk2,
                page.unk3,
            )
          }
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
      val params = dialogService.scriptParams(bgEvent.script)
      if (params != null) {
        state.inDialog = true
        state.dialogNpcEntityId = npcEntityId
        state.dialogPages = params
        state.dialogPageIndex = 0
        val seqId = state.dialogSeqId++
        val page = params[0]
        ctx.send(DialogStatePacket(true))
        dialogService.sendInteractive(
            ctx,
            seqId,
            npcEntityId,
            page.type,
            page.unk1,
            page.unk2,
            page.unk3,
        )
      }
      return
    }

    log.info { "Entity interaction for entity $npcEntityId not found on current map" }
  }
}
