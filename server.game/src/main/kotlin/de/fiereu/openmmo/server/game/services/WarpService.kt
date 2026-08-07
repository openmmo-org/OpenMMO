package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.maps.WarpTile
import de.fiereu.openmmo.net.game.packets.MapTransitionAckPacket
import de.fiereu.openmmo.net.game.packets.MapTransitionKind
import de.fiereu.openmmo.net.game.packets.MapTransitionPacket
import de.fiereu.openmmo.net.game.packets.RenderScreenPacket
import de.fiereu.openmmo.server.game.session.PENDING_MAP_LOAD
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.session.SCRIPT_SCOPE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.world.WarpExitRules
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

private val log = KotlinLogging.logger {}

// A player must not stay gated if the client never answers the transition.
private val ARRIVAL_TIMEOUT = 10.seconds

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
    val stored = characterStore.getCharacter(charId) ?: return

    // Check the map first. Moving the player onto one we do not have would strand it there.
    val destMap = mapManager.getMap(warp.targetRegionId, warp.targetBankId, warp.targetMapId)
    if (destMap == null) {
      log.warn {
        "Map not found for warp target ${warp.targetRegionId}:${warp.targetBankId}:${warp.targetMapId}"
      }
      return
    }

    state?.justWarped = true
    // Leave now, so the old map's observers do not keep a ghost for the whole transition.
    presenceService.leave(ctx)

    val sourceMap =
        mapManager.getMap(
            stored.info.positionRegionId, stored.info.positionBankId, stored.info.positionMapId)

    val knownOverride =
        WarpExitRules.getKnownOverride(sourceMap, destMap, warp.targetX, warp.targetY)
    val destBehavior = destMap.tileAt(warp.targetX, warp.targetY)?.behavior

    val warpFacing =
        warp.exitFacing
            ?: knownOverride?.facing
            ?: WarpExitRules.inferExitFacing(
                destTileBehavior = destBehavior,
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
            ?: WarpExitRules.shouldAutoStep(
                sourceMap = sourceMap,
                destMap = destMap,
                destTileBehavior = destBehavior,
            )
    if (shouldAutoStepOffWarp) {
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
        destMap.warps.find { it.x == warp.targetX && it.y == warp.targetY }?.elevation
            ?: warp.targetElevation

    log.info {
      "WARP EXIT: source=${sourceMap?.bankId}:${sourceMap?.mapId} dest=${destMap.bankId}:${destMap.mapId} target=(${warp.targetX},${warp.targetY}) final=($offsetX,$offsetY) z=$playerZ facing=$warpFacing autoStep=$shouldAutoStepOffWarp"
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
      state.elevation = playerZ
    }

    // Only fade out and send the map. onRequestPlayer does the arrival and fades back in.
    ctx.send(MapTransitionPacket())
    ctx.send(RenderScreenPacket(false))
    ctx.send(MapTransitionAckPacket(MapTransitionKind.WARP))

    mapLoadService.resetClientCache(ctx, destMap)
    ctx.send(mapManager.createLoadMapPacket(destMap, reloadPlayer = true, deleteCache = true))
    mapLoadService.preloadConnectedMaps(ctx, destMap, depth = 1, reloadPlayer = true)
    if (state != null) awaitArrival(ctx, state, charId)

    log.info { "Player $charId warped to bank=${warp.targetBankId} map=${warp.targetMapId}" }
  }

  /**
   * Releases the player if the client never asks for it. Without this a lost RequestPlayer leaves
   * the player faded out and unable to move for the rest of the session.
   */
  private fun awaitArrival(ctx: SessionContext, state: PlayerState, charId: Long) {
    val loaded = CompletableDeferred<Unit>()
    ctx.attributes[PENDING_MAP_LOAD] = loaded
    // The session's own scope, so a disconnect cancels this instead of reviving a dead session.
    val scope =
        ctx.attributes.getOrPut(SCRIPT_SCOPE) {
          CoroutineScope(SupervisorJob() + Dispatchers.Default)
        }
    scope.launch {
      if (withTimeoutOrNull(ARRIVAL_TIMEOUT) { loaded.await() } != null) return@launch
      // A newer warp owns the gate now, leave it to its own deadline.
      if (ctx.attributes[PENDING_MAP_LOAD] !== loaded) return@launch
      if (!ctx.channel.isActive) return@launch
      ctx.attributes.remove(PENDING_MAP_LOAD)
      log.warn { "Character $charId never asked for its player after a warp" }
      state.justWarped = false
      ctx.send(RenderScreenPacket(true))
      presenceService.enter(ctx)
    }
  }
}
