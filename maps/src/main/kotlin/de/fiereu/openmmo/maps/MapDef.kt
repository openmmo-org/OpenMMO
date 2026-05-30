package de.fiereu.openmmo.maps

import de.fiereu.openmmo.common.Tile2D
import de.fiereu.openmmo.common.enums.EncounterType
import de.fiereu.openmmo.common.enums.Lighting
import de.fiereu.openmmo.common.enums.MapType
import de.fiereu.openmmo.common.enums.Weather
import de.fiereu.openmmo.net.game.packets.MapData

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
)
