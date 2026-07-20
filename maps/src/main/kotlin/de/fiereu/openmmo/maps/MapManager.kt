package de.fiereu.openmmo.maps

import de.fiereu.openmmo.maps.generated.GeneratedMaps
import de.fiereu.openmmo.net.game.packets.LoadMapPacket
import de.fiereu.openmmo.net.game.packets.MapData
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapManager @Inject constructor() {

  private val maps = ConcurrentHashMap<Long, MapDef>()

  init {
    GeneratedMaps.loadInto(this)
  }

  fun register(map: MapDef) {
    maps[key(map.regionId, map.bankId, map.mapId)] = map
  }

  fun getMap(regionId: Byte, bankId: Byte, mapId: Byte): MapDef? =
      maps[key(regionId, bankId, mapId)]

  fun getMap(regionId: Int, bankId: Int, mapId: Int): MapDef? =
      getMap(regionId.toByte(), bankId.toByte(), mapId.toByte())

  fun size(): Int = maps.size

  fun createLoadMapPacket(
      map: MapDef,
      reloadPlayer: Boolean = false,
      deleteCache: Boolean = false,
  ): LoadMapPacket =
      LoadMapPacket(
          reloadPlayer = reloadPlayer,
          deleteCache = deleteCache,
          regionId = map.regionId.toInt() and 0xFF,
          bankId = map.bankId.toInt() and 0xFF,
          mapId = map.mapId.toInt() and 0xFF,
          mapData =
              MapData.GbaMapData(
                  width = map.width,
                  height = map.height,
                  paletteIdx1 = map.paletteIdx1,
                  paletteIdx2 = map.paletteIdx2,
                  borderWidth = map.borderWidth,
                  borderHeight = map.borderHeight,
                  unknownShort = map.unknownShort,
                  unknownByte = map.unknownByte,
                  borderTiles = map.borderTiles,
                  lighting = map.lighting,
                  weather = map.weather,
                  mapType = map.mapType,
                  encounterType = map.encounterType,
                  connections = map.connections,
              ),
      )

  private fun key(regionId: Byte, bankId: Byte, mapId: Byte): Long =
      ((regionId.toLong() and 0xFF) shl 16) or
          ((bankId.toLong() and 0xFF) shl 8) or
          (mapId.toLong() and 0xFF)
}
