package de.fiereu.openmmo.common.enums

enum class MoveTarget(val mask: Int) {
  SELECTED(0),
  DEPENDS(1 shl 0),
  USER_OR_SELECTED(1 shl 1),
  RANDOM(1 shl 2),
  BOTH(1 shl 3),
  USER(1 shl 4),
  FOES_AND_ALLY(1 shl 5),
  OPPONENTS_FIELD(1 shl 6),
}
