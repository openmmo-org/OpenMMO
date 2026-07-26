package de.fiereu.openmmo.server.game.script

import de.fiereu.openmmo.common.enums.Direction

/**
 * One step of an applymovement sequence: either a walk one tile in [direction] or a turn in place
 * to face [direction]. This is the small, game agnostic vocabulary scripts use, decomp movement
 * templates are ported into lists of these.
 */
enum class MovementStep(val direction: Direction, val walks: Boolean) {
  WALK_UP(Direction.UP, true),
  WALK_DOWN(Direction.DOWN, true),
  WALK_LEFT(Direction.LEFT, true),
  WALK_RIGHT(Direction.RIGHT, true),
  FACE_UP(Direction.UP, false),
  FACE_DOWN(Direction.DOWN, false),
  FACE_LEFT(Direction.LEFT, false),
  FACE_RIGHT(Direction.RIGHT, false),
}
