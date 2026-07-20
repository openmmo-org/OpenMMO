package de.fiereu.openmmo.common.enums

enum class MovementType(vararg mappings: Pair<Region, RegionMovementType>) {
  NONE(Region.HOENN to RegionMovementType.NONE, Region.KANTO to RegionMovementType.NONE),
  LOOK_AROUND(
      Region.HOENN to RegionMovementType.LOOK_AROUND,
      Region.KANTO to RegionMovementType.LOOK_AROUND),
  WANDER_AROUND(
      Region.HOENN to RegionMovementType.WANDER_AROUND,
      Region.KANTO to RegionMovementType.WANDER_AROUND),
  WANDER_UP_AND_DOWN(
      Region.HOENN to RegionMovementType.WANDER_UP_AND_DOWN,
      Region.KANTO to RegionMovementType.WANDER_UP_AND_DOWN),
  WANDER_DOWN_AND_UP(
      Region.HOENN to RegionMovementType.WANDER_DOWN_AND_UP,
      Region.KANTO to RegionMovementType.WANDER_DOWN_AND_UP),
  WANDER_LEFT_AND_RIGHT(
      Region.HOENN to RegionMovementType.WANDER_LEFT_AND_RIGHT,
      Region.KANTO to RegionMovementType.WANDER_LEFT_AND_RIGHT),
  WANDER_RIGHT_AND_LEFT(
      Region.HOENN to RegionMovementType.WANDER_RIGHT_AND_LEFT,
      Region.KANTO to RegionMovementType.WANDER_RIGHT_AND_LEFT),
  FACE_UP(Region.HOENN to RegionMovementType.FACE_UP, Region.KANTO to RegionMovementType.FACE_UP),
  FACE_DOWN(
      Region.HOENN to RegionMovementType.FACE_DOWN, Region.KANTO to RegionMovementType.FACE_DOWN),
  FACE_LEFT(
      Region.HOENN to RegionMovementType.FACE_LEFT, Region.KANTO to RegionMovementType.FACE_LEFT),
  FACE_RIGHT(
      Region.HOENN to RegionMovementType.FACE_RIGHT, Region.KANTO to RegionMovementType.FACE_RIGHT),
  PLAYER(Region.HOENN to RegionMovementType.PLAYER, Region.KANTO to RegionMovementType.PLAYER),
  BERRY_TREE_GROWTH(
      Region.HOENN to RegionMovementType.BERRY_TREE_GROWTH,
      Region.KANTO to RegionMovementType.BERRY_TREE_GROWTH),
  FACE_DOWN_AND_UP(
      Region.HOENN to RegionMovementType.FACE_DOWN_AND_UP,
      Region.KANTO to RegionMovementType.FACE_DOWN_AND_UP),
  FACE_LEFT_AND_RIGHT(
      Region.HOENN to RegionMovementType.FACE_LEFT_AND_RIGHT,
      Region.KANTO to RegionMovementType.FACE_LEFT_AND_RIGHT),
  FACE_UP_AND_LEFT(
      Region.HOENN to RegionMovementType.FACE_UP_AND_LEFT,
      Region.KANTO to RegionMovementType.FACE_UP_AND_LEFT),
  FACE_UP_AND_RIGHT(
      Region.HOENN to RegionMovementType.FACE_UP_AND_RIGHT,
      Region.KANTO to RegionMovementType.FACE_UP_AND_RIGHT),
  FACE_DOWN_AND_LEFT(
      Region.HOENN to RegionMovementType.FACE_DOWN_AND_LEFT,
      Region.KANTO to RegionMovementType.FACE_DOWN_AND_LEFT),
  FACE_DOWN_AND_RIGHT(
      Region.HOENN to RegionMovementType.FACE_DOWN_AND_RIGHT,
      Region.KANTO to RegionMovementType.FACE_DOWN_AND_RIGHT),
  FACE_DOWN_UP_AND_LEFT(
      Region.HOENN to RegionMovementType.FACE_DOWN_UP_AND_LEFT,
      Region.KANTO to RegionMovementType.FACE_DOWN_UP_AND_LEFT),
  FACE_DOWN_UP_AND_RIGHT(
      Region.HOENN to RegionMovementType.FACE_DOWN_UP_AND_RIGHT,
      Region.KANTO to RegionMovementType.FACE_DOWN_UP_AND_RIGHT),
  FACE_UP_LEFT_AND_RIGHT(
      Region.HOENN to RegionMovementType.FACE_UP_LEFT_AND_RIGHT,
      Region.KANTO to RegionMovementType.FACE_UP_LEFT_AND_RIGHT),
  FACE_DOWN_LEFT_AND_RIGHT(
      Region.HOENN to RegionMovementType.FACE_DOWN_LEFT_AND_RIGHT,
      Region.KANTO to RegionMovementType.FACE_DOWN_LEFT_AND_RIGHT),
  ROTATE_COUNTERCLOCKWISE(
      Region.HOENN to RegionMovementType.ROTATE_COUNTERCLOCKWISE,
      Region.KANTO to RegionMovementType.ROTATE_COUNTERCLOCKWISE),
  ROTATE_CLOCKWISE(
      Region.HOENN to RegionMovementType.ROTATE_CLOCKWISE,
      Region.KANTO to RegionMovementType.ROTATE_CLOCKWISE),
  WALK_UP_AND_DOWN(
      Region.HOENN to RegionMovementType.WALK_UP_AND_DOWN,
      Region.KANTO to RegionMovementType.WALK_UP_AND_DOWN),
  WALK_DOWN_AND_UP(
      Region.HOENN to RegionMovementType.WALK_DOWN_AND_UP,
      Region.KANTO to RegionMovementType.WALK_DOWN_AND_UP),
  WALK_LEFT_AND_RIGHT(
      Region.HOENN to RegionMovementType.WALK_LEFT_AND_RIGHT,
      Region.KANTO to RegionMovementType.WALK_LEFT_AND_RIGHT),
  WALK_RIGHT_AND_LEFT(
      Region.HOENN to RegionMovementType.WALK_RIGHT_AND_LEFT,
      Region.KANTO to RegionMovementType.WALK_RIGHT_AND_LEFT),
  WALK_SEQUENCE_UP_RIGHT_LEFT_DOWN(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_UP_RIGHT_LEFT_DOWN,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_UP_RIGHT_LEFT_DOWN),
  WALK_SEQUENCE_RIGHT_LEFT_DOWN_UP(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_RIGHT_LEFT_DOWN_UP,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_RIGHT_LEFT_DOWN_UP),
  WALK_SEQUENCE_DOWN_UP_RIGHT_LEFT(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_DOWN_UP_RIGHT_LEFT,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_DOWN_UP_RIGHT_LEFT),
  WALK_SEQUENCE_LEFT_DOWN_UP_RIGHT(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_LEFT_DOWN_UP_RIGHT,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_LEFT_DOWN_UP_RIGHT),
  WALK_SEQUENCE_UP_LEFT_RIGHT_DOWN(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_UP_LEFT_RIGHT_DOWN,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_UP_LEFT_RIGHT_DOWN),
  WALK_SEQUENCE_LEFT_RIGHT_DOWN_UP(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_LEFT_RIGHT_DOWN_UP,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_LEFT_RIGHT_DOWN_UP),
  WALK_SEQUENCE_DOWN_UP_LEFT_RIGHT(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_DOWN_UP_LEFT_RIGHT,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_DOWN_UP_LEFT_RIGHT),
  WALK_SEQUENCE_RIGHT_DOWN_UP_LEFT(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_RIGHT_DOWN_UP_LEFT,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_RIGHT_DOWN_UP_LEFT),
  WALK_SEQUENCE_LEFT_UP_DOWN_RIGHT(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_LEFT_UP_DOWN_RIGHT,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_LEFT_UP_DOWN_RIGHT),
  WALK_SEQUENCE_UP_DOWN_RIGHT_LEFT(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_UP_DOWN_RIGHT_LEFT,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_UP_DOWN_RIGHT_LEFT),
  WALK_SEQUENCE_RIGHT_LEFT_UP_DOWN(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_RIGHT_LEFT_UP_DOWN,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_RIGHT_LEFT_UP_DOWN),
  WALK_SEQUENCE_DOWN_RIGHT_LEFT_UP(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_DOWN_RIGHT_LEFT_UP,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_DOWN_RIGHT_LEFT_UP),
  WALK_SEQUENCE_RIGHT_UP_DOWN_LEFT(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_RIGHT_UP_DOWN_LEFT,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_RIGHT_UP_DOWN_LEFT),
  WALK_SEQUENCE_UP_DOWN_LEFT_RIGHT(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_UP_DOWN_LEFT_RIGHT,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_UP_DOWN_LEFT_RIGHT),
  WALK_SEQUENCE_LEFT_RIGHT_UP_DOWN(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_LEFT_RIGHT_UP_DOWN,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_LEFT_RIGHT_UP_DOWN),
  WALK_SEQUENCE_DOWN_LEFT_RIGHT_UP(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_DOWN_LEFT_RIGHT_UP,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_DOWN_LEFT_RIGHT_UP),
  WALK_SEQUENCE_UP_LEFT_DOWN_RIGHT(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_UP_LEFT_DOWN_RIGHT,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_UP_LEFT_DOWN_RIGHT),
  WALK_SEQUENCE_DOWN_RIGHT_UP_LEFT(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_DOWN_RIGHT_UP_LEFT,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_DOWN_RIGHT_UP_LEFT),
  WALK_SEQUENCE_LEFT_DOWN_RIGHT_UP(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_LEFT_DOWN_RIGHT_UP,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_LEFT_DOWN_RIGHT_UP),
  WALK_SEQUENCE_RIGHT_UP_LEFT_DOWN(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_RIGHT_UP_LEFT_DOWN,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_RIGHT_UP_LEFT_DOWN),
  WALK_SEQUENCE_UP_RIGHT_DOWN_LEFT(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_UP_RIGHT_DOWN_LEFT,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_UP_RIGHT_DOWN_LEFT),
  WALK_SEQUENCE_DOWN_LEFT_UP_RIGHT(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_DOWN_LEFT_UP_RIGHT,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_DOWN_LEFT_UP_RIGHT),
  WALK_SEQUENCE_LEFT_UP_RIGHT_DOWN(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_LEFT_UP_RIGHT_DOWN,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_LEFT_UP_RIGHT_DOWN),
  WALK_SEQUENCE_RIGHT_DOWN_LEFT_UP(
      Region.HOENN to RegionMovementType.WALK_SEQUENCE_RIGHT_DOWN_LEFT_UP,
      Region.KANTO to RegionMovementType.WALK_SEQUENCE_RIGHT_DOWN_LEFT_UP),
  COPY_PLAYER(
      Region.HOENN to RegionMovementType.COPY_PLAYER,
      Region.KANTO to RegionMovementType.COPY_PLAYER),
  COPY_PLAYER_OPPOSITE(
      Region.HOENN to RegionMovementType.COPY_PLAYER_OPPOSITE,
      Region.KANTO to RegionMovementType.COPY_PLAYER_OPPOSITE),
  COPY_PLAYER_COUNTERCLOCKWISE(
      Region.HOENN to RegionMovementType.COPY_PLAYER_COUNTERCLOCKWISE,
      Region.KANTO to RegionMovementType.COPY_PLAYER_COUNTERCLOCKWISE),
  COPY_PLAYER_CLOCKWISE(
      Region.HOENN to RegionMovementType.COPY_PLAYER_CLOCKWISE,
      Region.KANTO to RegionMovementType.COPY_PLAYER_CLOCKWISE),
  TREE_DISGUISE(
      Region.HOENN to RegionMovementType.TREE_DISGUISE,
      Region.KANTO to RegionMovementType.TREE_DISGUISE),
  MOUNTAIN_DISGUISE(
      Region.HOENN to RegionMovementType.MOUNTAIN_DISGUISE,
      Region.KANTO to RegionMovementType.MOUNTAIN_DISGUISE),
  COPY_PLAYER_IN_GRASS(
      Region.HOENN to RegionMovementType.COPY_PLAYER_IN_GRASS,
      Region.KANTO to RegionMovementType.COPY_PLAYER_IN_GRASS),
  COPY_PLAYER_OPPOSITE_IN_GRASS(
      Region.HOENN to RegionMovementType.COPY_PLAYER_OPPOSITE_IN_GRASS,
      Region.KANTO to RegionMovementType.COPY_PLAYER_OPPOSITE_IN_GRASS),
  COPY_PLAYER_COUNTERCLOCKWISE_IN_GRASS(
      Region.HOENN to RegionMovementType.COPY_PLAYER_COUNTERCLOCKWISE_IN_GRASS,
      Region.KANTO to RegionMovementType.COPY_PLAYER_COUNTERCLOCKWISE_IN_GRASS),
  COPY_PLAYER_CLOCKWISE_IN_GRASS(
      Region.HOENN to RegionMovementType.COPY_PLAYER_CLOCKWISE_IN_GRASS,
      Region.KANTO to RegionMovementType.COPY_PLAYER_CLOCKWISE_IN_GRASS),
  BURIED(Region.HOENN to RegionMovementType.BURIED, Region.KANTO to RegionMovementType.BURIED),
  WALK_IN_PLACE_DOWN(
      Region.HOENN to RegionMovementType.WALK_IN_PLACE_DOWN,
      Region.KANTO to RegionMovementType.WALK_IN_PLACE_DOWN),
  WALK_IN_PLACE_UP(
      Region.HOENN to RegionMovementType.WALK_IN_PLACE_UP,
      Region.KANTO to RegionMovementType.WALK_IN_PLACE_UP),
  WALK_IN_PLACE_LEFT(
      Region.HOENN to RegionMovementType.WALK_IN_PLACE_LEFT,
      Region.KANTO to RegionMovementType.WALK_IN_PLACE_LEFT),
  WALK_IN_PLACE_RIGHT(
      Region.HOENN to RegionMovementType.WALK_IN_PLACE_RIGHT,
      Region.KANTO to RegionMovementType.WALK_IN_PLACE_RIGHT),
  JOG_IN_PLACE_DOWN(
      Region.HOENN to RegionMovementType.HOENN_JOG_IN_PLACE_DOWN,
      Region.KANTO to RegionMovementType.KANTO_JOG_IN_PLACE_DOWN),
  JOG_IN_PLACE_UP(
      Region.HOENN to RegionMovementType.HOENN_JOG_IN_PLACE_UP,
      Region.KANTO to RegionMovementType.KANTO_JOG_IN_PLACE_UP),
  JOG_IN_PLACE_LEFT(
      Region.HOENN to RegionMovementType.HOENN_JOG_IN_PLACE_LEFT,
      Region.KANTO to RegionMovementType.KANTO_JOG_IN_PLACE_LEFT),
  JOG_IN_PLACE_RIGHT(
      Region.HOENN to RegionMovementType.HOENN_JOG_IN_PLACE_RIGHT,
      Region.KANTO to RegionMovementType.KANTO_JOG_IN_PLACE_RIGHT),
  INVISIBLE(
      Region.HOENN to RegionMovementType.INVISIBLE, Region.KANTO to RegionMovementType.INVISIBLE),
  RUN_IN_PLACE_DOWN(Region.HOENN to RegionMovementType.HOENN_RUN_IN_PLACE_DOWN),
  RUN_IN_PLACE_UP(Region.HOENN to RegionMovementType.HOENN_RUN_IN_PLACE_UP),
  RUN_IN_PLACE_LEFT(Region.HOENN to RegionMovementType.HOENN_RUN_IN_PLACE_LEFT),
  RUN_IN_PLACE_RIGHT(Region.HOENN to RegionMovementType.HOENN_RUN_IN_PLACE_RIGHT),
  WALK_SLOWLY_IN_PLACE_DOWN(Region.HOENN to RegionMovementType.HOENN_WALK_SLOWLY_IN_PLACE_DOWN),
  WALK_SLOWLY_IN_PLACE_UP(Region.HOENN to RegionMovementType.HOENN_WALK_SLOWLY_IN_PLACE_UP),
  WALK_SLOWLY_IN_PLACE_LEFT(Region.HOENN to RegionMovementType.HOENN_WALK_SLOWLY_IN_PLACE_LEFT),
  WALK_SLOWLY_IN_PLACE_RIGHT(Region.HOENN to RegionMovementType.HOENN_WALK_SLOWLY_IN_PLACE_RIGHT),
  WALK_IN_PLACE_FAST_DOWN(Region.KANTO to RegionMovementType.KANTO_WALK_IN_PLACE_FAST_DOWN),
  WALK_IN_PLACE_FAST_UP(Region.KANTO to RegionMovementType.KANTO_WALK_IN_PLACE_FAST_UP),
  WALK_IN_PLACE_FAST_LEFT(Region.KANTO to RegionMovementType.KANTO_WALK_IN_PLACE_FAST_LEFT),
  WALK_IN_PLACE_FAST_RIGHT(Region.KANTO to RegionMovementType.KANTO_WALK_IN_PLACE_FAST_RIGHT),
  RAISE_HAND_AND_STOP(Region.KANTO to RegionMovementType.KANTO_RAISE_HAND_AND_STOP),
  RAISE_HAND_AND_JUMP(Region.KANTO to RegionMovementType.KANTO_RAISE_HAND_AND_JUMP),
  RAISE_HAND_AND_SWIM(Region.KANTO to RegionMovementType.KANTO_RAISE_HAND_AND_SWIM),
  WANDER_AROUND_SLOWER(Region.KANTO to RegionMovementType.KANTO_WANDER_AROUND_SLOWER),
  ;

  private val byRegion = mappings.toMap()

  fun forRegion(region: Region): RegionMovementType = byRegion[region] ?: RegionMovementType.NONE
}
