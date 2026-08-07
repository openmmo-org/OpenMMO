package de.fiereu.openmmo.server.game.session

import de.fiereu.openmmo.common.enums.Direction

data class PlayerState(
    val userId: Int,
    var characterId: Long? = null,
    var justWarped: Boolean = false,
    var facingDirection: Direction = Direction.DOWN,
    var inDialog: Boolean = false,
    var dialogNpcEntityId: Long = 0,
    var dialogSeqId: Int = 0,
    var regionId: Int = 1,
    var bankId: Int = 51,
    var mapId: Int = 3,
    var x: Short = 4,
    var y: Short = 2,
    /** Trusts one source tile after scripted movement. */
    var acceptNextMoveSource: Boolean = false,
    /** Maps the client already holds. A warp sends deleteCache, which empties this. */
    val loadedMaps: MutableSet<Int> = mutableSetOf(),
)

/** Packs a map address into one key for [PlayerState.loadedMaps]. */
fun mapCacheKey(regionId: Int, bankId: Int, mapId: Int): Int =
    (regionId shl 16) or (bankId shl 8) or mapId
