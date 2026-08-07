package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.DynamicWarp
import de.fiereu.openmmo.maps.MapManager
import de.fiereu.openmmo.net.game.packets.MapTransitionAckPacket
import de.fiereu.openmmo.net.game.packets.MapTransitionKind
import de.fiereu.openmmo.net.game.packets.MapTransitionPacket
import de.fiereu.openmmo.net.game.packets.RenderScreenPacket
import de.fiereu.openmmo.server.game.session.PlayerState
import de.fiereu.openmmo.server.game.storage.CharacterStore
import javax.inject.Inject
import javax.inject.Singleton

/** A direct, no-auto-step warp for cutscenes (the decomp's `warpsilent`). */
@Singleton
class ScriptWarpService
@Inject
constructor(
    private val mapManager: MapManager,
    private val mapLoadService: MapLoadService,
    private val characterStore: CharacterStore,
    private val presenceService: PresenceService,
    private val npcService: NpcService,
) {
  fun warp(
      session: SessionContext,
      state: PlayerState,
      destination: DynamicWarp,
  ) {
    val characterId = state.characterId ?: return
    val stored = characterStore.getCharacter(characterId) ?: return
    val map =
        mapManager.getMap(destination.regionId, destination.bankId, destination.mapId) ?: return
    val info =
        stored.info.copy(
            positionRegionId = destination.regionId,
            positionBankId = destination.bankId,
            positionMapId = destination.mapId,
            positionX = destination.x,
            positionY = destination.y,
        )

    characterStore.updateCharacter(info)
    characterStore.flushCharacterAsync(characterId)
    state.justWarped = true
    state.regionId = destination.regionId.toInt()
    state.bankId = destination.bankId.toInt()
    state.mapId = destination.mapId.toInt()
    state.x = destination.x
    state.y = destination.y
    state.facingDirection = destination.facing

    session.send(MapTransitionPacket())
    session.send(RenderScreenPacket(false))
    session.send(MapTransitionAckPacket(MapTransitionKind.WARP))
    mapLoadService.resetClientCache(session, map)
    session.send(mapManager.createLoadMapPacket(map, reloadPlayer = true, deleteCache = true))
    mapLoadService.preloadConnectedMaps(session, map, depth = 1, reloadPlayer = true)
    session.send(mapLoadService.createLoadEntity(info, destination.facing))
    npcService.spawnNpcsForMap(
        session,
        destination.bankId.toInt(),
        destination.mapId.toInt(),
        destination.regionId.toInt(),
    )
    presenceService.refresh(session)
    session.send(RenderScreenPacket(true))
  }
}
