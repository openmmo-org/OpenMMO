package de.fiereu.openmmo.server.game.script

import de.fiereu.openmmo.common.enums.Direction

/**
 * One step of an applymovement sequence: either a walk one tile in [direction] or a turn in place
 * to face [direction]. [action] is the client movement-action byte (from packet captures: face
 * 0x00-0x03, walk 0x10-0x13); a whole sequence of these is sent in one movement packet. This is the
 * small game agnostic vocabulary scripts use.
 */
enum class MovementStep(
    val direction: Direction,
    val walks: Boolean,
    val action: Int,
    val changesFacing: Boolean = true,
    val fast: Boolean = false,
) {
  FACE_DOWN(Direction.DOWN, false, 0x00),
  FACE_UP(Direction.UP, false, 0x01),
  FACE_LEFT(Direction.LEFT, false, 0x02),
  FACE_RIGHT(Direction.RIGHT, false, 0x03),
  WALK_DOWN(Direction.DOWN, true, 0x10),
  WALK_UP(Direction.UP, true, 0x11),
  WALK_LEFT(Direction.LEFT, true, 0x12),
  WALK_RIGHT(Direction.RIGHT, true, 0x13),
  FAST_DOWN(Direction.DOWN, true, 0x1D, fast = true),
  FAST_UP(Direction.UP, true, 0x1E, fast = true),
  FAST_LEFT(Direction.LEFT, true, 0x1F, fast = true),
  FAST_RIGHT(Direction.RIGHT, true, 0x20, fast = true),
  WALK_IN_PLACE_FAST_LEFT(Direction.LEFT, false, 0x23, fast = true),
  WALK_IN_PLACE_FAST_RIGHT(Direction.RIGHT, false, 0x24, fast = true),
  // Captured delay actions use Emerald ids plus eight.
  DELAY_8(Direction.DOWN, false, 0x1B, changesFacing = false),
  DELAY_16(Direction.DOWN, false, 0x1C, changesFacing = false),
  // Hides the entity in place, used at the end of a walk into a door (decomp set_invisible).
  SET_INVISIBLE(Direction.DOWN, false, 0x60, changesFacing = false),
}
