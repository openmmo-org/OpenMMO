package de.fiereu.openmmo.codegen.maps

object RenderUtil {

  private const val EMPTY_LIST = "emptyList()"
  private const val LIST_OPEN = "listOf(\n            "
  private const val LIST_SEP = ",\n            "
  private const val LIST_CLOSE = ",\n        )"

  fun borderTiles(tiles: List<Int>): String {
    val take = tiles.take(4)
    val filled =
        if (take.size < 4) take + List(4 - take.size) { if (take.isEmpty()) 8 else take.last() }
        else take
    return filled.joinToString(", ", "listOf(", ")") {
      "Tile2D(0x${it.toString(16).uppercase().padStart(4, '0')}, 0)"
    }
  }

  fun connections(conns: List<ParsedConnection>): String {
    if (conns.isEmpty()) return EMPTY_LIST
    return conns.joinToString(LIST_SEP, LIST_OPEN, LIST_CLOSE) {
      "MapData.GbaConnection(direction = ${it.direction}, unknown = ${it.offset}," +
          " targetBank = ${it.targetBank}, targetMap = ${it.targetMap})"
    }
  }

  fun warps(warps: List<ParsedWarp>): String {
    if (warps.isEmpty()) return EMPTY_LIST
    return warps.joinToString(LIST_SEP, LIST_OPEN, LIST_CLOSE) {
      "WarpTile(x = ${it.x}, y = ${it.y}, elevation = ${it.elevation}," +
          " targetRegionId = ${it.targetRegion}, targetBankId = ${it.targetBank}," +
          " targetMapId = ${it.targetMap}," +
          " targetX = ${it.targetX}, targetY = ${it.targetY}, targetElevation = ${it.targetElevation})"
    }
  }

  fun npcs(npcs: List<ParsedNpc>): String {
    if (npcs.isEmpty()) return EMPTY_LIST
    return npcs.joinToString(LIST_SEP, LIST_OPEN, LIST_CLOSE) {
      "NpcDef(entityIdx = ${it.entityIdx}, graphicsId = ${it.graphicsId}," +
          " x = ${it.x}, y = ${it.y}, elevation = ${it.elevation}," +
          " movementType = ${it.movementType}, movementRangeX = ${it.movementRangeX}," +
          " movementRangeY = ${it.movementRangeY}, trainerType = ${it.trainerType}," +
          " facing = ${it.facing}, script = ${escapeString(it.script)})"
    }
  }

  fun bgEvents(events: List<ParsedBgEvent>): String {
    if (events.isEmpty()) return EMPTY_LIST
    return events.joinToString(LIST_SEP, LIST_OPEN, LIST_CLOSE) {
      "BgEventDef(x = ${it.x}, y = ${it.y}," +
          " facingDir = ${escapeString(it.facingDir)}, script = ${escapeString(it.script)})"
    }
  }

  private fun escapeString(s: String): String =
      "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\""
}
