package de.fiereu.openmmo.common

import de.fiereu.openmmo.common.enums.Direction
import java.time.LocalDateTime

data class CharacterInfo(
    val id: Long,
    val name: String,
    val namePrefix: String = "",
    val userId: Int,
    val rivalSex: Byte,
    val lastLogin: LocalDateTime,
    val createdAt: LocalDateTime,
    val money: Int,
    val permissions: Int,
    val remainingSafariSteps: Short,
    val remainingSafariBalls: Byte,
    val pcExtraSlots: Byte,
    val battleBoxExtraSlots: Byte,
    val templateAmount: Byte,
    val positionRegionId: Byte,
    val positionBankId: Byte,
    val positionMapId: Byte,
    val positionX: Short,
    val positionY: Short,
    /** Not on the wire. LoadEntity carries the facing the client draws. */
    val positionFacing: Direction = Direction.DOWN,
    val repelLeft: Short,
    val repelItemId: Short,
    val lureLeft: Short,
    val lureItemId: Short,
    /** Runtime warp destination for MAP_DYNAMIC warps (setdynamicwarp), or null if none is set. */
    val dynamicWarp: DynamicWarp? = null,
)
