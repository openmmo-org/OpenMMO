package de.fiereu.openmmo.server.game.services

import de.fiereu.network.PacketEvent
import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.EntityMovePacket
import de.fiereu.openmmo.net.game.packets.FaceDirectionPacket
import de.fiereu.openmmo.net.game.packets.MapData
import de.fiereu.openmmo.net.game.packets.MovementPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.atomic.AtomicInteger
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
    private val multiplayerService: MultiplayerService,
    private val mapManager: MapManager,
    private val characterStore: CharacterStore,
) {
  private val sequenceCounter = AtomicInteger(0)

  fun onMovement(event: PacketEvent<MovementPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val msg = event.packet

    log.info { "Movement: char=$charId, x=${msg.x}, y=${msg.y}, dir=${msg.direction}" }
    state.facingDirection = msg.direction

    val prevStored = characterStore.getCharacter(charId)
    val prevX = prevStored?.info?.positionX?.toInt()
    val prevY = prevStored?.info?.positionY?.toInt()
    val isWallBump = prevX == msg.x && prevY == msg.y

    val stored = characterStore.getCharacter(charId)
    if (stored != null) {
      val currentMap =
          mapManager.getMap(
              stored.info.positionRegionId,
              stored.info.positionBankId,
              stored.info.positionMapId,
          )
      if (currentMap != null) {
        if (state.justWarped) {
          state.justWarped = false
        } else {
          val warp =
              currentMap.warps.find { w ->
                w.x == msg.x &&
                    w.y == msg.y &&
                    (w.facingDirection == null || w.facingDirection == msg.direction) &&
                    !isWallBump
              }
          if (warp != null) {
            log.info { "DIRECT WARP at (${msg.x}, ${msg.y}) facing ${msg.direction}" }
            warpService.executeWarp(ctx, charId, warp)
            return
          }

          val adjX =
              msg.x +
                  when (msg.direction) {
                    Direction.RIGHT -> 1
                    Direction.LEFT -> -1
                    else -> 0
                  }
          val adjY =
              msg.y +
                  when (msg.direction) {
                    Direction.UP -> -1
                    Direction.DOWN -> 1
                    else -> 0
                  }
          val adjWarp =
              currentMap.warps.find { w ->
                w.x == adjX &&
                    w.y == adjY &&
                    (w.facingDirection == null || w.facingDirection == msg.direction)
              }
          if (adjWarp != null) {
            log.info { "ADJACENT WARP at (${msg.x}, ${msg.y}) facing ${msg.direction}" }
            warpService.executeWarp(ctx, charId, adjWarp)
            return
          }
        }

        if (prevX != null && prevY != null && !isWallBump) {
          val dx = kotlin.math.abs(msg.x - prevX)
          val dy = kotlin.math.abs(msg.y - prevY)
          if (dx > 1 || dy > 1) {
            val gbaDirection =
                when {
                  prevY == 0 -> 2
                  prevY == currentMap.height - 1 -> 1
                  prevX == 0 -> 3
                  prevX == currentMap.width - 1 -> 4
                  else -> null
                }
            if (gbaDirection != null) {
              val connection = currentMap.connections.find { it.direction == gbaDirection }
              if (connection != null) {
                val targetMap =
                    mapManager.getMap(
                        1,
                        connection.targetBank.toByte(),
                        connection.targetMap.toByte(),
                    )
                if (targetMap != null) {
                  val targetX =
                      when (gbaDirection) {
                        3 -> targetMap.width - 1
                        4 -> 0
                        else -> (prevX - connection.unknown).coerceIn(0, targetMap.width - 1)
                      }
                  val targetY =
                      when (gbaDirection) {
                        1 -> 0
                        2 -> targetMap.height - 1
                        else -> (prevY - connection.unknown).coerceIn(0, targetMap.height - 1)
                      }
                  edgeTransition(
                      ctx,
                      charId,
                      connection,
                      targetX.toByte(),
                      targetY.toByte(),
                      msg.direction,
                  )
                  return
                }
              }
            }
          }
        }
      }
    }

    characterStore.updatePosition(charId, msg.x.toShort(), msg.y.toShort())
    state.x = msg.x.toShort()
    state.y = msg.y.toShort()
    if (!isWallBump) {
      val seq = sequenceCounter.incrementAndGet().toByte()
      val movePkt =
          EntityMovePacket(
              entityId = charId,
              x = msg.x.toByte(),
              y = msg.y.toByte(),
              direction = msg.direction,
              seq = seq,
          )
      ctx.send(movePkt)
      multiplayerService.broadcastExcept(ctx, movePkt)
    }
  }

  fun onFaceDirection(event: PacketEvent<FaceDirectionPacket>) {
    val ctx = event.session
    val state = ctx.attributes[PLAYER_STATE] ?: return
    val charId = state.characterId ?: return
    val msg = event.packet
    state.facingDirection = msg.direction

    val seq = sequenceCounter.incrementAndGet().toByte()
    val stored = characterStore.getCharacter(charId) ?: return
    val movePkt =
        EntityMovePacket(
            entityId = charId,
            x = stored.info.positionX.toByte(),
            y = stored.info.positionY.toByte(),
            direction = msg.direction,
            seq = seq,
        )
    ctx.send(movePkt)
    multiplayerService.broadcastExcept(ctx, movePkt)
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

    mapLoadService.preloadConnectedMaps(ctx, map, depth = 1)
    npcService.spawnNpcsForMap(ctx, targetBank.toInt(), targetMap.toInt())

    val seq = sequenceCounter.incrementAndGet().toByte()
    val movePkt =
        EntityMovePacket(
            entityId = charId,
            x = targetX,
            y = targetY,
            direction = direction,
            seq = seq,
        )
    ctx.send(movePkt)
    multiplayerService.broadcastExcept(ctx, movePkt)
    log.info { "Player $charId edge-transitioned to bank=$targetBank map=$targetMap" }
  }
}
