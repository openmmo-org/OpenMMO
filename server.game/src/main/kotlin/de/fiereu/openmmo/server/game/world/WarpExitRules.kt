package de.fiereu.openmmo.server.game.world

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.MapType
import de.fiereu.openmmo.maps.MapDef

data class WarpExitOverride(
    val facing: Direction,
    val autoStep: Boolean,
)

object WarpExitRules {

  fun inferExitFacing(
      destTileBehavior: String?,
      destMap: MapDef?,
      destX: Int,
      destY: Int,
      sourceMap: MapDef?,
  ): Direction {
    if (destMap == null) return Direction.DOWN

    when (destTileBehavior) {
      "MB_NON_ANIMATED_DOOR",
      "MB_ANIMATED_DOOR" -> return Direction.DOWN
      "MB_NORTH_ARROW_WARP" -> return Direction.DOWN
      "MB_SOUTH_ARROW_WARP" -> return Direction.UP
      "MB_WEST_ARROW_WARP" -> return Direction.RIGHT
      "MB_EAST_ARROW_WARP" -> return Direction.LEFT
    }

    val sourceMapType = sourceMap?.mapType

    getKnownOverride(sourceMap, destMap, destX, destY)?.let {
      return it.facing
    }

    if (isUndergroundMap(destMap.mapType) && !isUndergroundMap(sourceMapType)) {
      return Direction.UP
    }

    if (isBuildingMap(destMap.mapType) && !isBuildingMap(sourceMapType)) {
      return Direction.UP
    }

    if (isBuildingMap(sourceMapType) && !isBuildingMap(destMap.mapType)) {
      return Direction.DOWN
    }

    if (isBuildingMap(sourceMapType) && isBuildingMap(destMap.mapType)) {
      return Direction.DOWN
    }

    return inferFacingFromExactEdge(destMap, destX, destY)
  }

  fun getKnownOverride(
      sourceMap: MapDef?,
      destMap: MapDef?,
      destX: Int,
      destY: Int,
  ): WarpExitOverride? {
    if (destMap == null) return null

    if (destMap.bankId.toInt() == 74 && destMap.mapId.toInt() == 4) {
      return when {
        destX == 4 && destY == 10 -> WarpExitOverride(Direction.UP, autoStep = false)
        destX == 29 && destY == 16 -> WarpExitOverride(Direction.UP, autoStep = false)
        destX == 18 && destY == 20 -> WarpExitOverride(Direction.UP, autoStep = false)
        else -> null
      }
    }

    if (destMap.bankId.toInt() == 74 && destMap.mapId.toInt() == 11) {
      return when {
        (destX == 14 && destY == 5) || (destX == 15 && destY == 5) ->
            WarpExitOverride(Direction.DOWN, autoStep = false)
        (destX == 16 && destY == 38) ||
            (destX == 17 && destY == 38) ||
            (destX == 36 && destY == 38) ||
            (destX == 37 && destY == 38) -> WarpExitOverride(Direction.UP, autoStep = false)
        else -> null
      }
    }

    return null
  }

  private fun inferFacingFromExactEdge(
      destMap: MapDef,
      destX: Int,
      destY: Int,
  ): Direction {
    return when {
      destY <= 1 -> Direction.DOWN
      destY >= destMap.height - 2 -> Direction.UP
      destX <= 1 -> Direction.RIGHT
      destX >= destMap.width - 2 -> Direction.LEFT
      else -> Direction.DOWN
    }
  }

  fun shouldAutoStep(
      sourceMap: MapDef?,
      destMap: MapDef?,
  ): Boolean {
    if (sourceMap == null || destMap == null) return false

    val sourceBuilding =
        sourceMap.mapType == MapType.INSIDE || sourceMap.mapType == MapType.SECRET_BASE

    val destBuilding = destMap.mapType == MapType.INSIDE || destMap.mapType == MapType.SECRET_BASE

    val enteringUnderground =
        destMap.mapType == MapType.UNDERGROUND && sourceMap.mapType != MapType.UNDERGROUND

    if (sourceBuilding && !destBuilding) return true
    if (enteringUnderground) return true

    return false
  }

  fun isBuildingMap(type: MapType?): Boolean {
    return type == MapType.INSIDE || type == MapType.SECRET_BASE
  }

  fun isUndergroundMap(type: MapType?): Boolean {
    return type == MapType.UNDERGROUND
  }
}
