package de.fiereu.openmmo.maps

import de.fiereu.openmmo.common.enums.Direction

data class WarpTile(
    val x: Int,
    val y: Int,
    val elevation: Int = 0,
    val targetRegionId: Byte,
    val targetBankId: Byte,
    val targetMapId: Byte,
    val targetX: Int,
    val targetY: Int,
    val targetElevation: Int = 0,
    val facingDirection: Direction? = null,
    /** Direction the player faces after this warp, or null to infer it. */
    val exitFacing: Direction? = null,
    /**
     * A MAP_DYNAMIC warp whose destination is not baked in but set at runtime (the decomp
     * setdynamicwarp). The target fields are placeholders, the real destination comes from the
     * player's stored dynamic warp when they step on it.
     */
    val dynamic: Boolean = false,
)
