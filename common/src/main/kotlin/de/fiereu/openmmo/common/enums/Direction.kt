package de.fiereu.openmmo.common.enums

enum class Direction(val dx: Int, val dy: Int) {
  DOWN(0, 1),
  UP(0, -1),
  LEFT(-1, 0),
  RIGHT(1, 0),
  DIVE(0, 0),
  EMERGE(0, 0);

  fun opposite(): Direction =
      when (this) {
        DOWN -> UP
        UP -> DOWN
        LEFT -> RIGHT
        RIGHT -> LEFT
        DIVE -> EMERGE
        EMERGE -> DIVE
      }
}
