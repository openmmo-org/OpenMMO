package de.fiereu.openmmo.common.enums

enum class Direction {
  DOWN,
  UP,
  LEFT,
  RIGHT,
  DIVE,
  EMERGE;

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
