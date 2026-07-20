package de.fiereu.openmmo.server.game.world.interest

import de.fiereu.network.SessionContext
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Tracks which sessions belong to which interest groups and fans packets out to a group. It is
 * purpose-agnostic: the same instance backs overworld presence (see [PresenceService]), guild chat,
 * or battle updates depending on the [InterestKey] used. What a group *means* (spawning entities,
 * area-of-interest filtering, ...) lives in the consumer, not here.
 */
@Singleton
class InterestManager @Inject constructor() {
  private val buckets = ConcurrentHashMap<InterestKey, MutableSet<SessionContext>>()
  private val membership = ConcurrentHashMap<SessionContext, MutableSet<InterestKey>>()

  fun join(ctx: SessionContext, key: InterestKey) {
    buckets.getOrPut(key) { ConcurrentHashMap.newKeySet() }.add(ctx)
    membership.getOrPut(ctx) { ConcurrentHashMap.newKeySet() }.add(key)
  }

  fun leave(ctx: SessionContext, key: InterestKey) {
    buckets[key]?.let { members ->
      members.remove(ctx)
      if (members.isEmpty()) buckets.remove(key, members)
    }
    membership[ctx]?.let { keys ->
      keys.remove(key)
      if (keys.isEmpty()) membership.remove(ctx, keys)
    }
  }

  /** Drop the session from every group it belongs to (e.g. on disconnect). */
  fun leaveAll(ctx: SessionContext) {
    val keys: Set<InterestKey> = membership.remove(ctx) ?: return
    for (key in keys) {
      buckets[key]?.let { members ->
        members.remove(ctx)
        if (members.isEmpty()) buckets.remove(key, members)
      }
    }
  }

  /** A snapshot of the group's members. Safe to iterate while the group mutates. */
  fun members(key: InterestKey): Set<SessionContext> = buckets[key]?.toSet() ?: emptySet()

  /** The groups the session currently belongs to. */
  fun keysOf(ctx: SessionContext): Set<InterestKey> = membership[ctx]?.toSet() ?: emptySet()

  /** Send a packet to every member of the group, optionally excluding one session (the sender). */
  fun broadcast(key: InterestKey, packet: Any, exclude: SessionContext? = null) {
    val members: Set<SessionContext> = buckets[key] ?: return
    for (ctx in members) if (ctx !== exclude) ctx.send(packet)
  }
}
