package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.net.game.packets.EntityLeavePacket
import de.fiereu.openmmo.net.game.packets.LoadEntityPacket
import de.fiereu.openmmo.server.game.session.PLAYER_STATE
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.world.interest.InterestManager
import de.fiereu.openmmo.server.game.world.interest.InterestPolicy
import de.fiereu.openmmo.server.game.world.interest.MapInterestKey
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Overworld presence built on the generic [InterestManager]. A player's map is one interest group.
 * This layer adds the entity spawn/despawn semantics and the area-of-interest filter that only make
 * sense for spatial presence.
 */
@Singleton
class PresenceService
@Inject
constructor(
    private val interestManager: InterestManager,
    private val policy: InterestPolicy,
    private val mapLoadService: MapLoadService,
    private val characterStore: CharacterStore,
) {

  /** Spawn the player into its map group and exchange entity snapshots with its observers. */
  fun enter(ctx: SessionContext) {
    val key = mapKeyFor(ctx) ?: return
    spawnInto(ctx, key)
  }

  /** Despawn the player from its map observers and drop it from the map group. */
  fun leave(ctx: SessionContext) {
    val key = currentMapKey(ctx) ?: mapKeyFor(ctx) ?: return
    interestManager.leave(ctx, key)
    val entityId = entityIdOf(ctx) ?: return
    for (other in observers(ctx, key)) other.send(EntityLeavePacket(entityId))
  }

  /** Send a packet to everyone observing the player on its current map (excludes the player). */
  fun broadcastToObservers(ctx: SessionContext, packet: Any) {
    val key = currentMapKey(ctx) ?: mapKeyFor(ctx) ?: return
    for (other in observers(ctx, key)) other.send(packet)
  }

  /**
   * Recompute the player's map group from its (already updated) state: despawn it from the map it
   * left, spawn it into the map it joined. Call after changing a player's map.
   */
  fun refresh(ctx: SessionContext) {
    val newKey = mapKeyFor(ctx)
    val oldKey = currentMapKey(ctx)
    if (oldKey == newKey) return
    if (oldKey != null) {
      interestManager.leave(ctx, oldKey)
      val entityId = entityIdOf(ctx)
      if (entityId != null) {
        for (other in observers(ctx, oldKey)) other.send(EntityLeavePacket(entityId))
      }
    }
    if (newKey != null) spawnInto(ctx, newKey)
  }

  private fun spawnInto(ctx: SessionContext, key: MapInterestKey) {
    val observers = observers(ctx, key)
    val self = loadEntityFor(ctx)
    for (other in observers) {
      if (self != null) other.send(self)
      loadEntityFor(other)?.let { ctx.send(it) }
    }
    interestManager.join(ctx, key)
  }

  private fun observers(ctx: SessionContext, key: MapInterestKey): List<SessionContext> =
      policy.filter(ctx, interestManager.members(key))

  private fun mapKeyFor(ctx: SessionContext): MapInterestKey? {
    val state = ctx.attributes[PLAYER_STATE] ?: return null
    return MapInterestKey(state.regionId, state.bankId, state.mapId)
  }

  private fun currentMapKey(ctx: SessionContext): MapInterestKey? =
      interestManager.keysOf(ctx).filterIsInstance<MapInterestKey>().firstOrNull()

  private fun entityIdOf(ctx: SessionContext): Long? = ctx.attributes[PLAYER_STATE]?.characterId

  private fun loadEntityFor(ctx: SessionContext): LoadEntityPacket? {
    val state = ctx.attributes[PLAYER_STATE] ?: return null
    val charId = state.characterId ?: return null
    val stored = characterStore.getCharacter(charId) ?: return null
    return mapLoadService.createLoadEntity(
        stored.info, state.facingDirection, party = stored.pokemon)
  }
}
