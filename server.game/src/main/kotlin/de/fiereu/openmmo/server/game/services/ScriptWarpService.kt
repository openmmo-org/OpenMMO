package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.DynamicWarp
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.MapTransitionAckPacket
import de.fiereu.openmmo.net.game.packets.MapTransitionKind
import de.fiereu.openmmo.net.game.packets.MapTransitionPacket
import de.fiereu.openmmo.net.game.packets.RenderScreenPacket
import de.fiereu.openmmo.server.game.session.PENDING_MAP_LOAD
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.storage.CharacterStore
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.withTimeoutOrNull

private val log = KotlinLogging.logger {}

// A cutscene must not hang if the client never answers the transition.
private val MAP_LOAD_TIMEOUT = 10.seconds

/** A direct, no-auto-step warp for cutscenes (the decomp's `warpsilent`). */
@Singleton
class ScriptWarpService
@Inject
constructor(
    private val mapManager: MapManager,
    private val mapLoadService: MapLoadService,
    private val characterStore: CharacterStore,
    private val presenceService: PresenceService,
) {
  /** Warps and waits until the client has loaded the destination, so the script can go on. */
  suspend fun warp(
      session: SessionContext,
      state: PlayerState,
      destination: DynamicWarp,
  ) {
    val characterId = state.characterId ?: return
    val stored = characterStore.getCharacter(characterId) ?: return
    val map = mapManager.getMap(destination.regionId, destination.bankId, destination.mapId)
    if (map == null) {
      log.warn {
        "Map not found for scripted warp ${destination.regionId}:${destination.bankId}:${destination.mapId}"
      }
      return
    }
    val info =
        stored.info.copy(
            positionRegionId = destination.regionId,
            positionBankId = destination.bankId,
            positionMapId = destination.mapId,
            positionX = destination.x,
            positionY = destination.y,
            positionFacing = destination.facing,
        )

    characterStore.updateCharacter(info)
    characterStore.flushCharacterAsync(characterId)
    state.justWarped = true
    // Leave now, so the old map's observers do not keep a ghost for the whole transition.
    presenceService.leave(session)
    state.regionId = destination.regionId.toInt()
    state.bankId = destination.bankId.toInt()
    state.mapId = destination.mapId.toInt()
    state.x = destination.x
    state.y = destination.y
    state.elevation = map.tileAt(destination.x.toInt(), destination.y.toInt())?.elevation ?: 0
    state.facingDirection = destination.facing

    val loaded = CompletableDeferred<Unit>()
    session.attributes[PENDING_MAP_LOAD] = loaded

    session.send(MapTransitionPacket())
    session.send(RenderScreenPacket(false))
    session.send(MapTransitionAckPacket(MapTransitionKind.WARP))
    mapLoadService.resetClientCache(session, map)
    session.send(mapManager.createLoadMapPacket(map, reloadPlayer = true, deleteCache = true))
    mapLoadService.preloadConnectedMaps(session, map, depth = 1, reloadPlayer = true)

    try {
      if (withTimeoutOrNull(MAP_LOAD_TIMEOUT) { loaded.await() } == null) {
        log.warn {
          "Character $characterId did not load ${destination.bankId}:${destination.mapId}"
        }
        // No arrival will follow, so give the screen and the map group back here.
        state.justWarped = false
        session.send(RenderScreenPacket(true))
        presenceService.enter(session)
      }
    } finally {
      if (session.attributes[PENDING_MAP_LOAD] === loaded) {
        session.attributes.remove(PENDING_MAP_LOAD)
      }
    }
  }
}
