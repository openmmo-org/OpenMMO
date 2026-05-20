package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.protocols.game.packets.EntityMovePacket
import de.fiereu.openmmo.protocols.game.packets.FaceDirectionPacket
import de.fiereu.openmmo.protocols.game.packets.MapData
import de.fiereu.openmmo.protocols.game.packets.MovementPacket
import de.fiereu.openmmo.server.game.session.SessionManager
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.world.MapManager
import de.fiereu.openmmo.server.protocol.PacketEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.channel.ChannelHandlerContext
import java.util.concurrent.atomic.AtomicInteger

private val log = KotlinLogging.logger {}

class MovementService(
    private val warpService: WarpService,
    private val mapLoadService: MapLoadService,
    private val npcService: NpcService,
    private val multiplayerService: MultiplayerService,
) {
  private val sequenceCounter = AtomicInteger(0)

  fun onMovement(event: PacketEvent<MovementPacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) return

    val charId = session.characterId ?: return
    val msg = event.packet

    log.info { "Movement: char=$charId, x=${msg.x}, y=${msg.y}, dir=${msg.direction}" }
    session.facingDirection = msg.direction

    // Store previous position for detecting wall bumps
    val prevStored = CharacterStore.getCharacter(charId)
    val prevX = prevStored?.info?.positionX?.toInt()
    val prevY = prevStored?.info?.positionY?.toInt()
    val isWallBump = prevX == msg.x && prevY == msg.y

    // Check for warps and edge transitions BEFORE updating position.
    val stored = CharacterStore.getCharacter(charId)
    if (stored != null) {
      val currentMap =
          MapManager.getMap(
              stored.info.positionRegionId,
              stored.info.positionBankId,
              stored.info.positionMapId,
          )
      if (currentMap != null) {
        // Skip warp check for the first movement after a warp
        if (session.justWarped) {
          session.justWarped = false
        } else {
          // Direct warp: player walked onto a warp tile (not just standing on it)
          val warp =
              currentMap.warps.find { w ->
                w.x == msg.x &&
                    w.y == msg.y &&
                    (w.facingDirection == null || w.facingDirection == msg.direction) &&
                    !isWallBump
              }
          if (warp != null) {
            log.info {
              "DIRECT WARP at (${msg.x}, ${msg.y}) facing ${msg.direction} → ${warp.targetRegionId}:${warp.targetBankId}:${warp.targetMapId} (${warp.targetX}, ${warp.targetY})"
            }
            warpService.executeWarp(event.ctx, charId, warp)
            return
          }

          // Adjacent warp: player is standing in front of a door tile, facing it
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
            log.info {
              "ADJACENT WARP at (${msg.x}, ${msg.y}) facing ${msg.direction} → tile ($adjX, $adjY) → ${adjWarp.targetRegionId}:${adjWarp.targetBankId}:${adjWarp.targetMapId} (${adjWarp.targetX}, ${adjWarp.targetY})"
            }
            warpService.executeWarp(event.ctx, charId, adjWarp)
            return
          }
        }

        // Map edge transition
        if (prevX != null && prevY != null && !isWallBump) {
          val dx = kotlin.math.abs(msg.x - prevX)
          val dy = kotlin.math.abs(msg.y - prevY)
          log.info {
            "EDGE CHECK: prev=($prevX, $prevY) msg=(${msg.x}, ${msg.y}) dx=$dx dy=$dy map=${currentMap.bankId}:${currentMap.mapId} h=${currentMap.height} w=${currentMap.width}"
          }
          if (dx > 1 || dy > 1) {
            val gbaDirection =
                when {
                  prevY == 0 -> 2 // North
                  prevY == currentMap.height - 1 -> 1 // South
                  prevX == 0 -> 3 // West
                  prevX == currentMap.width - 1 -> 4 // East
                  else -> null
                }
            log.info { "BIG JUMP: gbaDirection=$gbaDirection" }
            if (gbaDirection != null) {
              val connection = currentMap.connections.find { it.direction == gbaDirection }
              log.info { "Connection found: $connection" }
              if (connection != null) {
                val targetMap =
                    MapManager.getMap(
                        1,
                        connection.targetBank.toByte(),
                        connection.targetMap.toByte(),
                    )
                if (targetMap != null) {
                  val targetX =
                      when (gbaDirection) {
                        3 -> targetMap.width - 1
                        4 -> 0
                        else ->
                            (prevX - connection.unknown).coerceIn(
                                0,
                                targetMap.width - 1,
                            )
                      }
                  val targetY =
                      when (gbaDirection) {
                        1 -> 0
                        2 -> targetMap.height - 1
                        else ->
                            (prevY - connection.unknown).coerceIn(
                                0,
                                targetMap.height - 1,
                            )
                      }
                  log.info {
                    "MAP TRANSITION prev=($prevX, $prevY) dir=${msg.direction} → bank=${connection.targetBank} map=${connection.targetMap} ($targetX, $targetY)"
                  }
                  edgeTransition(
                      event.ctx,
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

    // No transition: normal movement update + EntityMove broadcast
    CharacterStore.updatePosition(charId, msg.x.toShort(), msg.y.toShort())
    session.x = msg.x.toShort()
    session.y = msg.y.toShort()
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
      event.ctx.channel().writeAndFlush(movePkt)
      multiplayerService.broadcastExcept(event.ctx.channel(), movePkt)
    }
  }

  fun onFaceDirection(event: PacketEvent<FaceDirectionPacket>) {
    val session = SessionManager.getSessionByChannel(event.ctx.channel())
    if (session == null) return

    val charId = session.characterId ?: return
    val msg = event.packet
    session.facingDirection = msg.direction

    val seq = sequenceCounter.incrementAndGet().toByte()
    val stored = CharacterStore.getCharacter(charId) ?: return
    val movePkt =
        EntityMovePacket(
            entityId = charId,
            x = stored.info.positionX.toByte(),
            y = stored.info.positionY.toByte(),
            direction = msg.direction,
            seq = seq,
        )
    event.ctx.channel().writeAndFlush(movePkt)
    multiplayerService.broadcastExcept(event.ctx.channel(), movePkt)
  }

  private fun edgeTransition(
      ctx: ChannelHandlerContext,
      charId: Long,
      connection: MapData.GbaConnection,
      targetX: Byte,
      targetY: Byte,
      direction: Direction,
  ) {
    val targetBank = connection.targetBank.toByte()
    val targetMap = connection.targetMap.toByte()
    val map = MapManager.getMap(1, targetBank, targetMap) ?: return

    val session = SessionManager.getSessionByCharacterId(charId)
    session?.bankId = targetBank.toInt()
    session?.mapId = targetMap.toInt()
    session?.x = targetX.toShort()
    session?.y = targetY.toShort()
    CharacterStore.updatePosition(
        charId,
        targetX.toShort(),
        targetY.toShort(),
        targetBank,
        targetMap,
    )

    ctx.channel()
        .write(MapManager.createLoadMapPacket(map, reloadPlayer = false, deleteCache = false))
    mapLoadService.preloadConnectedMaps(ctx, map, depth = 1, flush = false)
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
    ctx.channel().write(movePkt)
    multiplayerService.broadcastExcept(ctx.channel(), movePkt)
    ctx.channel().flush()
    log.info { "Player $charId edge-transitioned to bank=$targetBank map=$targetMap" }
  }
}
