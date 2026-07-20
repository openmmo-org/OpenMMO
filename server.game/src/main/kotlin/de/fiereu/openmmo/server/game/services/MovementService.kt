package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.maps.MapDef
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.EntityFaceTurnPacket
import de.fiereu.openmmo.net.game.packets.EntityMovePacket
import de.fiereu.openmmo.net.game.packets.FaceDirectionPacket
import de.fiereu.openmmo.net.game.packets.MapData
import de.fiereu.openmmo.net.game.packets.MovementPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

@Singleton
class MovementService
@Inject
constructor(
    private val warpService: WarpService,
    private val mapLoadService: MapLoadService,
    private val npcService: NpcService,
    private val presenceService: PresenceService,
    private val mapManager: MapManager,
    private val characterStore: CharacterStore,
) {

  /** One step. The client sends the tile it left and the direction, the server derives the rest. */
  fun onMovement(event: PacketEvent<MovementPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val msg = event.packet
    state.facingDirection = msg.direction
    log.debug { "Movement: char=$charId from (${msg.x}, ${msg.y}) dir=${msg.direction}" }

    val stored = characterStore.getCharacter(charId) ?: return
    val fromX = stored.info.positionX.toInt()
    val fromY = stored.info.positionY.toInt()
    val atServerTile = msg.x == fromX && msg.y == fromY

    if (state.justWarped) {
      // Ignore steps until the client reports the warp destination, so a packet still in flight
      // from the old map is dropped rather than applied against the new one.
      if (!atServerTile) return
      state.justWarped = false
    } else if (!atServerTile) {
      log.debug {
        "DESYNC: char=$charId claims (${msg.x}, ${msg.y}), server has ($fromX, $fromY), resetting"
      }
      sendPositionReset(ctx, charId, fromX, fromY, state.facingDirection)
      return
    }

    val currentMap =
        mapManager.getMap(
            stored.info.positionRegionId,
            stored.info.positionBankId,
            stored.info.positionMapId,
        ) ?: return

    val toX = fromX + msg.direction.dx
    val toY = fromY + msg.direction.dy

    // Walking off the edge of a map hands the player to the neighbouring map, if there is one.
    if (toX !in 0 until currentMap.width || toY !in 0 until currentMap.height) {
      val connection = currentMap.connections.find { it.direction == msg.direction }
      val targetMap =
          connection?.let { mapManager.getMap(1, it.targetBank.toByte(), it.targetMap.toByte()) }
      if (connection == null || targetMap == null) {
        sendPositionReset(ctx, charId, fromX, fromY, msg.direction)
        return
      }
      val entryX =
          when (msg.direction) {
            Direction.LEFT -> targetMap.width - 1
            Direction.RIGHT -> 0
            else -> (fromX - connection.unknown).coerceIn(0, targetMap.width - 1)
          }
      val entryY =
          when (msg.direction) {
            Direction.UP -> targetMap.height - 1
            Direction.DOWN -> 0
            else -> (fromY - connection.unknown).coerceIn(0, targetMap.height - 1)
          }
      edgeTransition(ctx, charId, connection, entryX.toByte(), entryY.toByte(), msg.direction)
      return
    }

    // A warp fires on the tile the player steps onto, not the one it left.
    val warp =
        currentMap.warps.find { w ->
          w.x == toX &&
              w.y == toY &&
              (w.facingDirection == null || w.facingDirection == msg.direction)
        }
    if (warp != null) {
      log.info { "WARP at ($toX, $toY) facing ${msg.direction}" }
      warpService.executeWarp(ctx, charId, warp)
      return
    }

    if (!isWalkable(currentMap, toX, toY)) {
      log.debug { "WALL: char=$charId blocked at ($toX, $toY)" }
      sendPositionReset(ctx, charId, fromX, fromY, msg.direction)
      return
    }

    characterStore.updatePosition(charId, toX.toShort(), toY.toShort())
    state.x = toX.toShort()
    state.y = toY.toShort()

    // The client already walked itself there, so only the observers need telling.
    presenceService.broadcastToObservers(
        ctx,
        EntityMovePacket(entityId = charId, x = toX, y = toY, direction = msg.direction),
    )
  }

  /** Snap the client back to the position the server considers authoritative. */
  private fun sendPositionReset(
      ctx: SessionContext,
      charId: Long,
      x: Int,
      y: Int,
      direction: Direction,
  ) {
    ctx.send(EntityMovePacket(entityId = charId, x = x, y = y, direction = direction))
  }

  /** Turning in place. Only observers need it, the client has already turned itself. */
  fun onFaceDirection(event: PacketEvent<FaceDirectionPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val msg = event.packet
    state.facingDirection = msg.direction

    presenceService.broadcastToObservers(
        ctx,
        EntityFaceTurnPacket(entityId = charId, facing = msg.direction.ordinal.toByte()),
    )
  }

  private fun isWalkable(map: MapDef, x: Int, y: Int): Boolean {
    if (x !in 0 until map.width || y !in 0 until map.height) return false
    val tile = map.tileAt(x, y) ?: return true
    return !tile.blocksMovement()
  }

  private fun edgeTransition(
      ctx: SessionContext,
      charId: Long,
      connection: MapData.GbaConnection,
      targetX: Byte,
      targetY: Byte,
      direction: Direction,
  ) {
    val targetBank = connection.targetBank.toByte()
    val targetMap = connection.targetMap.toByte()
    val map = mapManager.getMap(1, targetBank, targetMap) ?: return

    val state = ctx.attributes[PLAYER_STATE]
    if (state != null) {
      state.bankId = targetBank.toInt()
      state.mapId = targetMap.toInt()
      state.x = targetX.toShort()
      state.y = targetY.toShort()
    }
    characterStore.updatePosition(
        charId,
        targetX.toShort(),
        targetY.toShort(),
        targetBank,
        targetMap,
    )
    presenceService.refresh(ctx)

    mapLoadService.preloadConnectedMaps(ctx, map, depth = 1)
    npcService.spawnNpcsForMap(ctx, targetBank.toInt(), targetMap.toInt())

    ctx.send(
        EntityMovePacket(
            entityId = charId,
            x = targetX.toInt(),
            y = targetY.toInt(),
            direction = direction,
        ))
    log.info { "Player $charId edge-transitioned to bank=$targetBank map=$targetMap" }
  }
}
