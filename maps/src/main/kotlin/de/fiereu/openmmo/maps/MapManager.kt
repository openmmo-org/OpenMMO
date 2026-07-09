package de.fiereu.openmmo.maps

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
  // romType=2 (SpecialMapData) and one is explicitly an INSIDE map -- but that sample is a
  // DIFFERENT physical map (bank=134) than the ones we serve (e.g. bank=51/map=3), and we have no
  // ground truth for OUR maps' real SpecialMapData field values (rF1, border connections). Tried
  // emitting SpecialMapData for INSIDE/SECRET_BASE maps 2026-07-09 (PR #41) using placeholder
  // values (rF1=0, no connections) copied from the golden sample's shape, not its map-specific
  // content -- regressed indoor spawn entirely: the client's own ROM-mismatch check rejects it
  // ("Possible rom corruption detected"), the client never follows up with a player request, and
  // spawn never completes (confirmed via live capture + game-server.log: our LoadMapPacket sends
  // successfully, no server-side exception, the client just stops). Reverted to always emitting
  // GbaMapData (romType=1) -- the shape our server emitted before #41, which spawns correctly for
  // every map we serve. GbaMapData's byte layout AND the GbaConnection targetRegion fix from #41
  // are kept; only this branch CHOICE is reverted. Emitting SpecialMapData for our own indoor maps
  // needs real per-map ground truth (a golden capture of one of OUR maps) before it's safe to try
  // again.
  fun createLoadMapPacket(
      map: MapDef,
      reloadPlayer: Boolean = false,
      deleteCache: Boolean = false,
  ): LoadMapPacket =
      LoadMapPacket(
          reloadPlayer = reloadPlayer,
          deleteCache = deleteCache,
          romType = 1,
          bankId = map.bankId.toInt() and 0xFF,
          mapId = map.mapId.toInt() and 0xFF,
          // Wire regionId (offset 4) is a distinct, finer-grained field from MapDef.regionId (our
          // own "which parsed world" lookup key, always 1 for Hoenn) -- our prior packets sent 0
          // here (by accident, via a hardcoded reserved byte) with no observed issue, so keep it.
          regionId = 0,
          mapData =
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
              ),
      )

  private fun key(regionId: Byte, bankId: Byte, mapId: Byte): Long =
      ((regionId.toLong() and 0xFF) shl 16) or
          ((bankId.toLong() and 0xFF) shl 8) or
          (mapId.toLong() and 0xFF)
}
