package de.fiereu.openmmo.maps

import de.fiereu.openmmo.common.Tile2D
import de.fiereu.openmmo.common.enums.EncounterMethod
import de.fiereu.openmmo.common.enums.EncounterType
import de.fiereu.openmmo.common.enums.Lighting
import de.fiereu.openmmo.common.enums.MapType
import de.fiereu.openmmo.common.enums.TileBehavior
import de.fiereu.openmmo.common.enums.Weather
import de.fiereu.openmmo.net.game.packets.MapData
import java.util.Base64

class MapDef(
    val regionId: Byte,
    val bankId: Byte,
    val mapId: Byte,
    val width: Int = 20,
    val height: Int = 15,
    val paletteIdx1: Int = 12,
    val paletteIdx2: Int = 14,
    val borderWidth: Int = 2,
    val borderHeight: Int = 2,
    val unknownShort: Int = 0,
    val unknownByte: Int = 0,
    val borderTiles: List<Tile2D> = listOf(Tile2D(8, 0), Tile2D(8, 0), Tile2D(8, 0), Tile2D(8, 0)),
    val lighting: Lighting = Lighting.REGULAR,
    val weather: Weather = Weather.REGULAR_WEATHER,
    val mapType: MapType = MapType.CITY,
    val encounterType: EncounterType = EncounterType.RANDOM,
    val wildEncounters: List<WildEncounterTable> = emptyList(),
    val connections: List<MapData.GbaConnection> = emptyList(),
    val warps: List<WarpTile> = emptyList(),
    val npcs: List<NpcDef> = emptyList(),
    val bgEvents: List<BgEventDef> = emptyList(),
    private val blockData: String = "",
    private val behaviorData: String = "",
) {

  val tiles: List<Tile2D> by lazy { decodeBlockData(blockData, behaviorData) }

  fun tileAt(x: Int, y: Int): Tile2D? =
      if (x in 0 until width && y in 0 until height) tiles.getOrNull(y * width + x) else null

  /** The wild table for [method] on this map, or null when the map has no such encounters. */
  fun encounterTable(method: EncounterMethod): WildEncounterTable? =
      wildEncounters.firstOrNull { it.method == method }
}

private fun decodeBlockData(blockData: String, behaviorData: String): List<Tile2D> {
  if (blockData.isEmpty()) return emptyList()
  val bytes = Base64.getDecoder().decode(blockData)
  // One behavior byte per tile, generated alongside the block data. Empty on maps we could not
  // resolve behaviors for, in which case every tile stays NORMAL.
  val behaviors =
      if (behaviorData.isEmpty()) ByteArray(0) else Base64.getDecoder().decode(behaviorData)
  val behaviorValues = TileBehavior.entries
  return List(bytes.size / 2) { i ->
    val raw = (bytes[i * 2].toInt() and 0xFF) or ((bytes[i * 2 + 1].toInt() and 0xFF) shl 8)
    val behavior = behaviors.getOrNull(i)?.toInt()?.let { behaviorValues.getOrNull(it) }
    Tile2D(
        material = (raw and 0x3FF).toShort(),
        collision = ((raw shr 10) and 0x3F).toByte(),
        behavior = behavior ?: TileBehavior.NORMAL,
    )
  }
}
