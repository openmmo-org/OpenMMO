package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.maps.WarpTile
import de.fiereu.openmmo.net.game.packets.MapTransitionAckPacket
import de.fiereu.openmmo.net.game.packets.MapTransitionPacket
import de.fiereu.openmmo.net.game.packets.RenderScreenPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.world.WarpExitRules
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

@Singleton
class WarpService
@Inject
constructor(
    private val mapLoadService: MapLoadService,
    private val mapManager: MapManager,
    private val characterStore: CharacterStore,
    private val presenceService: PresenceService,
) {

  fun executeWarp(ctx: SessionContext, charId: Long, warp: WarpTile) {
    val state = ctx.attributes[PLAYER_STATE]
    state?.justWarped = true
    val stored = characterStore.getCharacter(charId) ?: return

    val destMap = mapManager.getMap(warp.targetRegionId, warp.targetBankId, warp.targetMapId)
    val sourceMap =
        mapManager.getMap(
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
                sourceMap = sourceMap,
            )

    state?.facingDirection = warpFacing

    var offsetX = warp.targetX
    var offsetY = warp.targetY

    val shouldAutoStepOffWarp =
        knownOverride?.autoStep
            ?: WarpExitRules.shouldAutoStep(sourceMap = sourceMap, destMap = destMap)
    if (shouldAutoStepOffWarp && destMap != null) {
      val destWarp = destMap.warps.find { it.x == offsetX && it.y == offsetY }
      if (destWarp != null) {
        offsetX +=
            when (warpFacing) {
              Direction.LEFT -> -1
              Direction.RIGHT -> 1
              else -> 0
            }
        offsetY +=
            when (warpFacing) {
              Direction.UP -> -1
              Direction.DOWN -> 1
              else -> 0
            }
        offsetX = offsetX.coerceIn(0, destMap.width - 1)
        offsetY = offsetY.coerceIn(0, destMap.height - 1)
      }
    }

    val playerZ =
        destMap?.warps?.find { it.x == warp.targetX && it.y == warp.targetY }?.elevation
            ?: warp.targetElevation

    log.info {
      "WARP EXIT: source=${sourceMap?.bankId}:${sourceMap?.mapId} dest=${destMap?.bankId}:${destMap?.mapId} target=(${warp.targetX},${warp.targetY}) final=($offsetX,$offsetY) z=$playerZ facing=$warpFacing autoStep=$shouldAutoStepOffWarp"
    }

    val newInfo =
        stored.info.copy(
            positionRegionId = warp.targetRegionId,
            positionBankId = warp.targetBankId,
            positionMapId = warp.targetMapId,
            positionX = offsetX.toShort(),
            positionY = offsetY.toShort(),
        )
    characterStore.updateCharacter(newInfo)
    characterStore.flushCharacterAsync(charId)

    if (state != null) {
      state.regionId = warp.targetRegionId.toInt()
      state.bankId = warp.targetBankId.toInt()
      state.mapId = warp.targetMapId.toInt()
      state.x = offsetX.toShort()
      state.y = offsetY.toShort()
    }

    ctx.send(MapTransitionPacket())
    ctx.send(RenderScreenPacket(false))
    ctx.send(mapLoadService.createLoadEntity(newInfo, warpFacing, playerZ))
    ctx.send(MapTransitionAckPacket(0))

    if (destMap != null) {
      ctx.send(mapManager.createLoadMapPacket(destMap, reloadPlayer = true, deleteCache = true))
      mapLoadService.preloadConnectedMaps(ctx, destMap, depth = 1, reloadPlayer = true)
    } else {
      log.warn {
        "Map not found for warp target ${warp.targetRegionId}:${warp.targetBankId}:${warp.targetMapId}"
      }
    }

    presenceService.refresh(ctx)

    log.info { "Player $charId warped to bank=${warp.targetBankId} map=${warp.targetMapId}" }
  }
}
