package de.fiereu.openmmo.common.enums

enum class MoveFlag(val bit: Int) {
  MAKES_CONTACT(1 shl 0),
  PROTECT_AFFECTED(1 shl 1),
  MAGIC_COAT_AFFECTED(1 shl 2),
  SNATCH_AFFECTED(1 shl 3),
  MIRROR_MOVE_AFFECTED(1 shl 4),
  KINGS_ROCK_AFFECTED(1 shl 5),
}
