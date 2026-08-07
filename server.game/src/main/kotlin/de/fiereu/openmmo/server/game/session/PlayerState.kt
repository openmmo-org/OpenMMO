package de.fiereu.openmmo.server.game.session

import de.fiereu.openmmo.common.enums.Direction
import java.util.concurrent.ConcurrentHashMap

/**
 * Per-session player state. Scripts run on their own coroutine while packets are answered on the
 * mailbox coroutine, and both threads touch every field here, so all of them are volatile.
 */
data class PlayerState(
    val userId: Int,
    @field:Volatile var characterId: Long? = null,
    @field:Volatile var justWarped: Boolean = false,
    @field:Volatile var facingDirection: Direction = Direction.DOWN,
    @field:Volatile var inDialog: Boolean = false,
    @field:Volatile var dialogNpcEntityId: Long = 0,
    @field:Volatile var dialogSeqId: Int = 0,
    @field:Volatile var regionId: Int = 1,
    @field:Volatile var bankId: Int = 51,
    @field:Volatile var mapId: Int = 3,
    @field:Volatile var x: Short = 4,
    @field:Volatile var y: Short = 2,
    @field:Volatile var elevation: Int = 0,
    /** Trusts one source tile after scripted movement. */
    @field:Volatile var acceptNextMoveSource: Boolean = false,
    /** Maps the client already holds. A warp sends deleteCache, which empties this. */
    val loadedMaps: MutableSet<Int> = ConcurrentHashMap.newKeySet(),
)

/** Packs a map address into one key for [PlayerState.loadedMaps]. */
fun mapCacheKey(regionId: Int, bankId: Int, mapId: Int): Int =
    (regionId shl 16) or (bankId shl 8) or mapId
