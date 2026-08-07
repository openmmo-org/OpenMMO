package de.fiereu.openmmo.server.game.session

import de.fiereu.openmmo.common.enums.Direction

/**
 * Per-session player state. Scripts run on their own coroutine and write most of this, while the
 * netty thread reads it to answer packets, so the fields both touch are volatile.
 */
data class PlayerState(
    val userId: Int,
    var characterId: Long? = null,
    @field:Volatile var justWarped: Boolean = false,
    @field:Volatile var facingDirection: Direction = Direction.DOWN,
    @field:Volatile var inDialog: Boolean = false,
    var dialogNpcEntityId: Long = 0,
    var dialogSeqId: Int = 0,
    @field:Volatile var regionId: Int = 1,
    @field:Volatile var bankId: Int = 51,
    @field:Volatile var mapId: Int = 3,
    @field:Volatile var x: Short = 4,
    @field:Volatile var y: Short = 2,
    /** Trusts one source tile after scripted movement. */
    @field:Volatile var acceptNextMoveSource: Boolean = false,
    /** Maps the client already holds. A warp sends deleteCache, which empties this. */
    val loadedMaps: MutableSet<Int> = mutableSetOf(),
)

/** Packs a map address into one key for [PlayerState.loadedMaps]. */
fun mapCacheKey(regionId: Int, bankId: Int, mapId: Int): Int =
    (regionId shl 16) or (bankId shl 8) or mapId
