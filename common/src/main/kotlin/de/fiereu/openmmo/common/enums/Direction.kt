package de.fiereu.openmmo.common.enums

enum class Direction {
  DOWN,
  UP,
  LEFT,
  RIGHT;

  fun opposite(): Direction =
      when (this) {
        DOWN -> UP
        UP -> DOWN
        LEFT -> RIGHT
        RIGHT -> LEFT
      }
}
