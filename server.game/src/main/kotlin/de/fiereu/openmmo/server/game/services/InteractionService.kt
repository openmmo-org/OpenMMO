package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.protocols.game.packets.DialogStatePacket
import de.fiereu.openmmo.protocols.game.packets.EntityInteractPacket
import de.fiereu.openmmo.server.game.session.SessionManager
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.world.MapManager
import de.fiereu.openmmo.server.protocol.PacketEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.buffer.Unpooled

private val log = KotlinLogging.logger {}

class InteractionService(
    private val npcService: NpcService,
    private val dialogService: DialogService,
    private val packetSender: PacketSender,
) {

  fun onEntityInteract(event: PacketEvent<EntityInteractPacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) return
    val charId = session.characterId ?: return

    val npcEntityId = event.packet.entityId
    val stored = CharacterStore.getCharacter(charId) ?: return
    val currentMap =
        MapManager.getMap(
            stored.info.positionRegionId,
            stored.info.positionBankId,
            stored.info.positionMapId,
        )
    if (currentMap == null) return

    // Find which NPC this entityId belongs to
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
            log.info {
              "NPC entityIdx=${npc.entityIdx} script=${npc.script} — starting dialog (${params.size} pages)"
            }
            session.inDialog = true
            session.dialogNpcEntityId = npcEntityId
            session.dialogPages = params
            session.dialogPageIndex = 0
            val seqId = session.dialogSeqId++
            val page = params[0]
            event.ctx.channel().write(DialogStatePacket(true))
            val confirmBuf = Unpooled.buffer(9)
            confirmBuf.writeLongLE(npcEntityId)
            confirmBuf.writeByte(0xFF)
            val confirmBytes = ByteArray(9)
            confirmBuf.readBytes(confirmBytes)
            confirmBuf.release()
            packetSender.sendRaw(event.ctx, 0x07u, confirmBytes)
            dialogService.sendInteractive(
                event.ctx,
                seqId,
                npcEntityId,
                page.type,
                page.unk1,
                page.unk2,
                page.unk3,
            )
            event.ctx.channel().flush()
          } else {
            log.info { "NPC entityIdx=${npc.entityIdx} script=${npc.script} — no known params" }
          }
        } else {
          log.info { "NPC entityIdx=${npc.entityIdx} has no script (0x0) — no interaction" }
        }
        return
      }
    }

    // No NPC found — check for bg_event at tile player is facing
    val facingX =
        when (session.facingDirection) {
          Direction.RIGHT -> stored.info.positionX.toInt() + 1
          Direction.LEFT -> stored.info.positionX.toInt() - 1
          else -> stored.info.positionX.toInt()
        }
    val facingY =
        when (session.facingDirection) {
          Direction.UP -> stored.info.positionY.toInt() - 1
          Direction.DOWN -> stored.info.positionY.toInt() + 1
          else -> stored.info.positionY.toInt()
        }
    val facingDirOk: (String) -> Boolean = { dir ->
      dir == "BG_EVENT_PLAYER_FACING_ANY" ||
          (dir == "BG_EVENT_PLAYER_FACING_NORTH" && session.facingDirection == Direction.UP) ||
          (dir == "BG_EVENT_PLAYER_FACING_SOUTH" && session.facingDirection == Direction.DOWN) ||
          (dir == "BG_EVENT_PLAYER_FACING_WEST" && session.facingDirection == Direction.LEFT) ||
          (dir == "BG_EVENT_PLAYER_FACING_EAST" && session.facingDirection == Direction.RIGHT)
    }
    val bgEvent =
        currentMap.bgEvents.find { it.x == facingX && it.y == facingY && facingDirOk(it.facingDir) }
    if (bgEvent != null) {
      val params = dialogService.scriptParams(bgEvent.script)
      if (params != null) {
        log.info {
          "BG event script=${bgEvent.script} at ($facingX,$facingY) — starting dialog (${params.size} pages)"
        }
        session.inDialog = true
        session.dialogNpcEntityId = npcEntityId
        session.dialogPages = params
        session.dialogPageIndex = 0
        val seqId = session.dialogSeqId++
        val page = params[0]
        event.ctx.channel().write(DialogStatePacket(true))
        val confirmBuf = Unpooled.buffer(9)
        confirmBuf.writeLongLE(npcEntityId)
        confirmBuf.writeByte(0xFF)
        val confirmBytes = ByteArray(9)
        confirmBuf.readBytes(confirmBytes)
        confirmBuf.release()
        packetSender.sendRaw(event.ctx, 0x07u, confirmBytes)
        dialogService.sendInteractive(
            event.ctx,
            seqId,
            npcEntityId,
            page.type,
            page.unk1,
            page.unk2,
            page.unk3,
        )
        event.ctx.channel().flush()
      } else {
        log.info {
          "BG event interaction: script=${bgEvent.script} at ($facingX,$facingY) — no known params"
        }
      }
      return
    }

    log.info { "Entity interaction for entity $npcEntityId — not found on current map" }
  }
}
