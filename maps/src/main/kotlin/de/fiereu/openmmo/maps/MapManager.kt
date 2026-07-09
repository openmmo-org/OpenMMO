package de.fiereu.openmmo.maps

import de.fiereu.openmmo.common.enums.MapType
import de.fiereu.openmmo.net.game.packets.LoadMapPacket
import de.fiereu.openmmo.net.game.packets.MapData
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapManager @Inject constructor() {

  private val maps = ConcurrentHashMap<Long, MapDef>()

  init {
    MapDefaults.loadInto(this)
  }

  fun register(map: MapDef) {
    maps[key(map.regionId, map.bankId, map.mapId)] = map
  }

  fun getMap(regionId: Byte, bankId: Byte, mapId: Byte): MapDef? =
      maps[key(regionId, bankId, mapId)]

  fun getMap(regionId: Int, bankId: Int, mapId: Int): MapDef? =
      getMap(regionId.toByte(), bankId.toByte(), mapId.toByte())

  fun size(): Int = maps.size

  // romType/branch choice: docs/protocol/loadmap-spec.md. All 3 golden 0x10 samples we have are
  // romType=2 (SpecialMapData); one is explicitly an INSIDE map, matching the bedroom our
  // characters spawn in. Heuristic (n=1 indoor sample) per Kimi-Decode-Economy: INSIDE/
  // SECRET_BASE maps emit SpecialMapData, everything else keeps the GbaMapData shape our server
  // already emitted (now byte-corrected). Revisit if more indoor/outdoor captures land.
  fun createLoadMapPacket(
      map: MapDef,
      reloadPlayer: Boolean = false,
      deleteCache: Boolean = false,
  ): LoadMapPacket {
    val isSpecial = map.mapType == MapType.INSIDE || map.mapType == MapType.SECRET_BASE
    return LoadMapPacket(
        reloadPlayer = reloadPlayer,
        deleteCache = deleteCache,
        romType = if (isSpecial) 2 else 1,
        bankId = map.bankId.toInt() and 0xFF,
        mapId = map.mapId.toInt() and 0xFF,
        // Wire regionId (offset 4) is a distinct, finer-grained field from MapDef.regionId (our
        // own "which parsed world" lookup key, always 1 for Hoenn) -- our prior packets sent 0
        // here (by accident, via a hardcoded reserved byte) with no observed issue, so keep it.
        regionId = 0,
        mapData =
            if (isSpecial) {
              MapData.SpecialMapData(
                  rF1 = 0,
                  borderConnections = emptyList(),
                  lighting = map.lighting,
                  weather = map.weather,
                  mapType = map.mapType,
              )
            } else {
              MapData.GbaMapData(
                  width = map.width,
                  height = map.height,
                  arg1 = map.paletteIdx1,
                  arg2 = map.paletteIdx2,
                  e30 = map.borderWidth,
                  zr0 = map.borderHeight,
                  musicId = map.unknownShort,
                  unknownByte = map.unknownByte,
                  lighting = map.lighting,
                  weather = map.weather,
                  mapType = map.mapType,
                  encounterType = map.encounterType,
                  tiles = map.borderTiles,
                  connections = map.connections,
              )
            },
    )
  }

  private fun key(regionId: Byte, bankId: Byte, mapId: Byte): Long =
      ((regionId.toLong() and 0xFF) shl 16) or
          ((bankId.toLong() and 0xFF) shl 8) or
          (mapId.toLong() and 0xFF)
}
