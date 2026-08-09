package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.TileBehavior
import de.fiereu.openmmo.maps.MapDef
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.EntityFaceTurnPacket
import de.fiereu.openmmo.net.game.packets.FaceDirectionPacket
import de.fiereu.openmmo.net.game.packets.GbaEntityMovePacket
import de.fiereu.openmmo.net.game.packets.MapData
import de.fiereu.openmmo.net.game.packets.MovementPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/** Resolve a cardinal Gen-3 ledge hop to its tile two spaces away. */
internal fun ledgeLanding(
    map: MapDef,
    fromX: Int,
    fromY: Int,
    direction: Direction,
): Pair<Int, Int>? {
  val expectedBehavior =
      when (direction) {
        Direction.DOWN -> TileBehavior.JUMP_SOUTH
        Direction.UP -> TileBehavior.JUMP_NORTH
        Direction.LEFT -> TileBehavior.JUMP_WEST
        Direction.RIGHT -> TileBehavior.JUMP_EAST
        Direction.DIVE,
        Direction.EMERGE -> return null
      }
  val ledgeX = fromX + direction.dx
  val ledgeY = fromY + direction.dy
  if (map.tileAt(ledgeX, ledgeY)?.behavior != expectedBehavior) return null

  val landingX = fromX + direction.dx * 2
  val landingY = fromY + direction.dy * 2
  if (landingX !in 0 until map.width || landingY !in 0 until map.height) return null
  if (map.tileAt(landingX, landingY)?.blocksMovement() == true) return null
  return landingX to landingY
}

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
    private val encounterService: EncounterService,
    private val mapScriptService: MapScriptService,
) {

  /** One step. The client sends the tile it left and the direction, the server derives the rest. */
  fun onMovement(event: PacketEvent<MovementPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val msg = event.packet
    log.debug { "Movement: char=$charId from (${msg.x}, ${msg.y}) dir=${msg.direction}" }

    val stored = characterStore.getCharacter(charId) ?: return
    val currentMap =
        mapManager.getMap(
            stored.info.positionRegionId,
            stored.info.positionBankId,
            stored.info.positionMapId,
        ) ?: return
    var fromX = stored.info.positionX.toInt()
    var fromY = stored.info.positionY.toInt()
    if (state.acceptNextMoveSource &&
        msg.x in 0 until currentMap.width &&
        msg.y in 0 until currentMap.height) {
      // Trust the client after scripted movement.
      fromX = msg.x
      fromY = msg.y
      characterStore.updatePosition(charId, fromX.toShort(), fromY.toShort())
      state.x = fromX.toShort()
      state.y = fromY.toShort()
      state.acceptNextMoveSource = false
    }

    val atServerTile = msg.x == fromX && msg.y == fromY

    when {
      // Drop every step until the client asks for its player, else one left over from the old map
      // can fire a second warp.
      state.justWarped -> return
      // A script owns the player, like the decomp's lockall.
      state.inDialog -> {
        sendPositionReset(ctx, charId, currentMap, fromX, fromY, state.facingDirection)
        return
      }
      !atServerTile -> {
        log.debug {
          "DESYNC: char=$charId claims (${msg.x}, ${msg.y}), server has ($fromX, $fromY), resetting"
        }
        sendPositionReset(ctx, charId, currentMap, fromX, fromY, msg.direction)
        return
      }
    }

    // Only once the step is accepted, so a locked player keeps the facing its script left.
    state.facingDirection = msg.direction

    var toX = fromX + msg.direction.dx
    var toY = fromY + msg.direction.dy

    // Ledge hops land two tiles away.
    ledgeLanding(currentMap, fromX, fromY, msg.direction)?.let { landing ->
      toX = landing.first
      toY = landing.second
    }

    // Stairs and arrow warps fire from the tile the player stands on.
    val standingBehavior = currentMap.tileAt(fromX, fromY)?.behavior
    if (standingBehavior?.warpsWhenWalking == msg.direction) {
      val onTileWarp = currentMap.warps.find { it.x == fromX && it.y == fromY }
      if (onTileWarp != null) {
        warpService.executeWarp(ctx, charId, onTileWarp)
        return
      }
    }

    // Walking off the edge of a map hands the player to the neighbouring map, if there is one.
    if (toX !in 0 until currentMap.width || toY !in 0 until currentMap.height) {
      val connection = currentMap.connections.find { it.direction == msg.direction }
      // Connections stay inside one region.
      val targetMap =
          connection?.let {
            mapManager.getMap(currentMap.regionId, it.targetBank.toByte(), it.targetMap.toByte())
          }
      if (connection == null || targetMap == null) {
        sendPositionReset(ctx, charId, currentMap, fromX, fromY, msg.direction)
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
      edgeTransition(ctx, charId, currentMap.regionId, connection, entryX.toByte(), entryY.toByte())
      return
    }

    // A door only warps when walked into from below, a ladder warps on the step itself.
    val targetBehavior = currentMap.tileAt(toX, toY)?.behavior
    val stepsIntoWarp =
        when (targetBehavior) {
          TileBehavior.DOOR -> msg.direction == Direction.UP
          else -> targetBehavior?.warpsOnStep == true
        }
    val warp = if (!stepsIntoWarp) null else currentMap.warps.find { w -> w.x == toX && w.y == toY }
    if (warp != null) {
      log.info { "WARP at ($toX, $toY) facing ${msg.direction}" }
      warpService.executeWarp(ctx, charId, warp)
      return
    }

    if (!isWalkable(currentMap, toX, toY)) {
      log.debug { "WALL: char=$charId blocked at ($toX, $toY)" }
      sendPositionReset(ctx, charId, currentMap, fromX, fromY, msg.direction)
      return
    }

    characterStore.updatePosition(charId, toX.toShort(), toY.toShort(), facing = msg.direction)
    state.x = toX.toShort()
    state.y = toY.toShort()

    // The client already walked itself there, so only the observers need telling.
    presenceService.broadcastToObservers(
        ctx,
        gbaMovePacket(charId, currentMap, toX, toY, msg.direction),
    )

    // Story coordinate events take precedence over random encounters on the same step.
    if (!mapScriptService.onStep(ctx, state, currentMap, toX, toY)) {
      encounterService.onStep(ctx, charId, currentMap, toX, toY)
    }
  }

  /** Snap the client back to the position the server considers authoritative. */
  private fun sendPositionReset(
      ctx: SessionContext,
      charId: Long,
      map: MapDef,
      x: Int,
      y: Int,
      direction: Direction,
  ) {
    ctx.send(gbaMovePacket(charId, map, x, y, direction))
  }

  private fun gbaMovePacket(
      charId: Long,
      map: MapDef,
      x: Int,
      y: Int,
      direction: Direction,
  ): GbaEntityMovePacket =
      GbaEntityMovePacket(
          entityId = charId,
          bankId = map.bankId.toInt() and 0xff,
          mapId = map.mapId.toInt() and 0xff,
          x = x,
          y = y,
          movementMode = 2,
          direction = direction,
      )

  /** Turning in place. Only observers need it, the client has already turned itself. */
  fun onFaceDirection(event: PacketEvent<FaceDirectionPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    if (state.inDialog) {
      // The client already turned itself, so put it back. A face turn is only ever sent about
      // other entities, so the correction rides on the same packet a blocked step uses.
      val stored = characterStore.getCharacter(charId) ?: return
      val map =
          mapManager.getMap(
              stored.info.positionRegionId,
              stored.info.positionBankId,
              stored.info.positionMapId,
          ) ?: return
      sendPositionReset(
          ctx,
          charId,
          map,
          stored.info.positionX.toInt(),
          stored.info.positionY.toInt(),
          state.facingDirection,
      )
      return
    }
    val msg = event.packet
    state.facingDirection = msg.direction
    characterStore.updatePosition(charId, state.x, state.y, facing = msg.direction)

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
      regionId: Byte,
      connection: MapData.GbaConnection,
      targetX: Byte,
      targetY: Byte,
  ) {
    val targetBank = connection.targetBank.toByte()
    val targetMap = connection.targetMap.toByte()
    val map = mapManager.getMap(regionId, targetBank, targetMap) ?: return

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
    characterStore.flushCharacterAsync(charId)
    presenceService.refresh(ctx)

    mapLoadService.preloadConnectedMaps(ctx, map, depth = 1)
    npcService.spawnNpcsForMap(ctx, targetBank.toInt(), targetMap.toInt(), regionId.toInt())

    if (state != null) mapScriptService.onMapEnter(ctx, state, map)

    log.info { "Player $charId edge-transitioned to bank=$targetBank map=$targetMap" }
  }
}
