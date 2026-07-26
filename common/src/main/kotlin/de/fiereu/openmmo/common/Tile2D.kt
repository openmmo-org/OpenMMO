package de.fiereu.openmmo.common

import de.fiereu.openmmo.common.enums.TileBehavior

data class Tile2D(
    val material: Short,
    val collision: Byte,
    val behavior: TileBehavior = TileBehavior.NORMAL,
) {

  val movementCollision: Int
    get() = collision.toInt() and 0x03

  val elevation: Int
    get() = (collision.toInt() shr 2) and 0x0F

  fun blocksMovement(): Boolean = movementCollision != 0
}
