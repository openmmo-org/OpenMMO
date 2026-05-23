package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.protocols.game.packets.EntityLeavePacket
import de.fiereu.openmmo.protocols.game.packets.MapTransitionAckPacket
import de.fiereu.openmmo.protocols.game.packets.MapTransitionPacket
import de.fiereu.openmmo.protocols.game.packets.RenderScreenPacket
import de.fiereu.openmmo.server.game.session.SessionManager
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.world.MapManager
import de.fiereu.openmmo.server.game.world.WarpExitRules
import de.fiereu.openmmo.server.game.world.WarpTile
import io.github.oshai.kotlinlogging.KotlinLogging
import io.netty.channel.ChannelHandlerContext

private val log = KotlinLogging.logger {}

class WarpService(
    private val mapLoadService: MapLoadService,
) {

  fun executeWarp(ctx: ChannelHandlerContext, charId: Long, warp: WarpTile) {
    val session = SessionManager.getSessionByCharacterId(charId)
    if (session != null) {
      session.justWarped = true
    }
    val stored = CharacterStore.getCharacter(charId) ?: return

    val destMap = MapManager.getMap(warp.targetRegionId, warp.targetBankId, warp.targetMapId)

    val sourceMap =
        MapManager.getMap(
            stored.info.positionRegionId, stored.info.positionBankId, stored.info.positionMapId)

    val knownOverride =
        WarpExitRules.getKnownOverride(sourceMap, destMap, warp.targetX, warp.targetY)

    val warpFacing =
        knownOverride?.facing
            ?: WarpExitRules.inferExitFacing(
                destTileBehavior = null,
                destMap = destMap,
                destX = warp.targetX,
                destY = warp.targetY,
                sourceMap = sourceMap)

    session?.facingDirection = warpFacing

    var offsetX = warp.targetX
    var offsetY = warp.targetY

    val shouldAutoStepOffWarp =
        knownOverride?.autoStep
            ?: WarpExitRules.shouldAutoStep(
                sourceMap = sourceMap,
                destMap = destMap,
            )
    if (shouldAutoStepOffWarp) {
      val destWarp = destMap?.warps?.find { it.x == offsetX && it.y == offsetY }
      if (destWarp != null) {
        when (warpFacing) {
          Direction.UP -> offsetY--
          Direction.DOWN -> offsetY++
          Direction.LEFT -> offsetX--
          Direction.RIGHT -> offsetX++
        }
        offsetX = offsetX.coerceIn(0, destMap!!.width - 1)
        offsetY = offsetY.coerceIn(0, destMap!!.height - 1)
      }
    }

    val playerZ =
        destMap?.warps?.find { it.x == warp.targetX && it.y == warp.targetY }?.elevation
            ?: warp.targetElevation

    log.info {
      "WARP EXIT: source=${sourceMap?.bankId}:${sourceMap?.mapId} ${sourceMap?.mapType} " +
          "dest=${destMap?.bankId}:${destMap?.mapId} ${destMap?.mapType} " +
          "target=(${warp.targetX},${warp.targetY}) final=($offsetX,$offsetY) " +
          "z=$playerZ facing=$warpFacing autoStep=$shouldAutoStepOffWarp"
    }

    val newInfo =
        stored.info.copy(
            positionRegionId = warp.targetRegionId,
            positionBankId = warp.targetBankId,
            positionMapId = warp.targetMapId,
            positionX = offsetX.toShort(),
            positionY = offsetY.toShort(),
        )
    CharacterStore.updateCharacter(newInfo)

    // Multiplayer: broadcast EntityLeave to old-map players before position update
    val oldRegionId = session?.regionId
    val oldBankId = session?.bankId
    val oldMapId = session?.mapId
    if (oldBankId != null && oldMapId != null) {
      val oldMapPlayers =
          SessionManager.getOthersInMap(charId, oldRegionId ?: 1, oldBankId, oldMapId)
      for (other in oldMapPlayers) {
        other.channel.writeAndFlush(EntityLeavePacket(charId))
      }
    }

    // Update session position for multiplayer tracking
    if (session != null) {
      session.regionId = warp.targetRegionId.toInt()
      session.bankId = warp.targetBankId.toInt()
      session.mapId = warp.targetMapId.toInt()
      session.x = offsetX.toShort()
      session.y = offsetY.toShort()
    }

    // Real server warp sequence (from packets.db):
    ctx.channel().writeAndFlush(MapTransitionPacket())
    ctx.channel().writeAndFlush(RenderScreenPacket(false))
    ctx.channel().writeAndFlush(mapLoadService.createLoadEntity(newInfo, warpFacing, playerZ))
    ctx.channel().writeAndFlush(MapTransitionAckPacket(0))

    if (destMap != null) {
      ctx.channel()
          .writeAndFlush(
              MapManager.createLoadMapPacket(
                  destMap,
                  reloadPlayer = true,
                  deleteCache = true,
              ))
      mapLoadService.preloadConnectedMaps(ctx, destMap, depth = 1, reloadPlayer = true)
    } else {
      log.warn {
        "Map not found for warp target ${warp.targetRegionId}:${warp.targetBankId}:${warp.targetMapId}"
      }
    }

    log.info { "Player $charId warped to bank=${warp.targetBankId} map=${warp.targetMapId}" }
  }
}
