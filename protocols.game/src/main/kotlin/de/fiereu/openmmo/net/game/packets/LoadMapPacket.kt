package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.Tile2D
import de.fiereu.openmmo.common.enums.EncounterType
import de.fiereu.openmmo.common.enums.Lighting
import de.fiereu.openmmo.common.enums.MapType
import de.fiereu.openmmo.common.enums.Weather
import de.fiereu.openmmo.net.game.codecs.Tile2DCodec

/**
 * Root-caused 2026-07-09 (docs/protocol/loadmap-spec.md, Kimi-Decode-Economy): the packet was never
 * byte-exact against the real protocol. It branched on offset 1 as `regionId`, but that byte is
 * actually `romType` -- the real `regionId` is offset 4, previously dropped as a hardcoded reserved
 * byte. Misreading the branch key produced garbage width/height and an out-of-range enum ordinal
 * decoding the real server's packets.
 */
sealed interface MapData {
  /**
   * romType in {2,3,4,10} ("special / no tile data"). Validated byte-exact against all 3 golden
   * 0x10 samples we have -- every one is this branch. The client doesn't need tile data for these
   * (e.g. building interiors it already has bundled); `borderConnections` semantics beyond "key
   * maps to value" aren't decoded yet, but an empty list matches the one golden INSIDE-map sample.
   */
  data class SpecialMapData(
      val rF1: Int,
      val borderConnections: List<BorderConnectionEntry>,
      val lighting: Lighting,
      val weather: Weather,
      val mapType: MapType,
  ) : MapData

  data class BorderConnectionEntry(val key: Int, val value: Int)

  /**
   * romType 0/1 ("normal GBA-style"). NOT validated against any golden sample -- reconstructed from
   * the real client's decompiled parser only (docs/protocol/loadmap-spec.md). Our own server output
   * self-decodes cleanly with this layout, but field semantics (especially `tiles`, `arg1`/`arg2`,
   * and the compressed-secondary-layer path) are best-effort until a real outdoor capture confirms
   * them -- don't build further assumptions on top without re-checking.
   */
  data class GbaMapData(
      val width: Int,
      val height: Int,
      val arg1: Int,
      val arg2: Int,
      val e30: Int,
      val zr0: Int,
      val musicId: Int,
      val unknownByte: Int,
      val lighting: Lighting,
      val weather: Weather,
      val mapType: MapType,
      val encounterType: EncounterType,
      val tiles: List<Tile2D>,
      val compressed: ByteArray? = null,
      val connections: List<GbaConnection> = emptyList(),
      val owner: MapOwner? = null,
  ) : MapData {
    init {
      require(e30 * zr0 == tiles.size) { "tiles size doesn't match e30*zr0" }
    }

    override fun equals(other: Any?): Boolean =
        other is GbaMapData &&
            width == other.width &&
            height == other.height &&
            arg1 == other.arg1 &&
            arg2 == other.arg2 &&
            e30 == other.e30 &&
            zr0 == other.zr0 &&
            musicId == other.musicId &&
            unknownByte == other.unknownByte &&
            lighting == other.lighting &&
            weather == other.weather &&
            mapType == other.mapType &&
            encounterType == other.encounterType &&
            tiles == other.tiles &&
            (compressed?.contentEquals(other.compressed) ?: (other.compressed == null)) &&
            connections == other.connections &&
            owner == other.owner

    override fun hashCode(): Int {
      var result = width
      result = 31 * result + height
      result = 31 * result + arg1
      result = 31 * result + arg2
      result = 31 * result + e30
      result = 31 * result + zr0
      result = 31 * result + musicId
      result = 31 * result + unknownByte
      result = 31 * result + lighting.hashCode()
      result = 31 * result + weather.hashCode()
      result = 31 * result + mapType.hashCode()
      result = 31 * result + encounterType.hashCode()
      result = 31 * result + tiles.hashCode()
      result = 31 * result + (compressed?.contentHashCode() ?: 0)
      result = 31 * result + connections.hashCode()
      result = 31 * result + (owner?.hashCode() ?: 0)
      return result
    }
  }

  data class GbaConnection(
      val direction: Int,
      val offset: Int,
      val targetBank: Int,
      val targetMap: Int,
      val targetRegion: Int,
  )

  data class MapOwner(val ownerId: Long, val ownerName: String)
}

/** romType values that decode as [MapData.SpecialMapData] (docs/protocol/loadmap-spec.md). */
val SPECIAL_ROM_TYPES = setOf(2, 3, 4, 10)

data class LoadMapPacket(
    val reloadPlayer: Boolean,
    val deleteCache: Boolean,
    val romType: Int,
    val bankId: Int,
    val mapId: Int,
    val regionId: Int,
    val mapData: MapData,
) {
  init {
    when (mapData) {
      is MapData.SpecialMapData ->
          require(romType in SPECIAL_ROM_TYPES) { "romType/mapData mismatch" }
      is MapData.GbaMapData -> require(romType !in SPECIAL_ROM_TYPES) { "romType/mapData mismatch" }
    }
  }
}

private val BorderConnectionsPrefixedU8: Codec<List<MapData.BorderConnectionEntry>> =
    object : Codec<List<MapData.BorderConnectionEntry>> {
      override fun read(buf: ReadBuffer): List<MapData.BorderConnectionEntry> {
        val n = U8.read(buf)
        return List(n) { MapData.BorderConnectionEntry(U16LE.read(buf), U16LE.read(buf)) }
      }

      override fun write(buf: WriteBuffer, value: List<MapData.BorderConnectionEntry>) {
        U8.write(buf, value.size)
        value.forEach {
          U16LE.write(buf, it.key)
          U16LE.write(buf, it.value)
        }
      }
    }

private object GbaConnectionCodec : PacketCodec<MapData.GbaConnection>() {
  override fun CodecScope<MapData.GbaConnection>.body() =
      MapData.GbaConnection(
          direction = field(U8, MapData.GbaConnection::direction),
          offset = field(S32LE, MapData.GbaConnection::offset),
          targetBank = field(U8, MapData.GbaConnection::targetBank),
          targetMap = field(U8, MapData.GbaConnection::targetMap),
          targetRegion = field(U8, MapData.GbaConnection::targetRegion),
      )
}

private val GbaConnectionListPrefixedU8: Codec<List<MapData.GbaConnection>> =
    object : Codec<List<MapData.GbaConnection>> {
      override fun read(buf: ReadBuffer): List<MapData.GbaConnection> {
        val n = U8.read(buf)
        return List(n) { GbaConnectionCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<MapData.GbaConnection>) {
        U8.write(buf, value.size)
        value.forEach { GbaConnectionCodec.write(buf, it) }
      }
    }

object LoadMapPacketCodec : PacketCodec<LoadMapPacket>() {
  override fun CodecScope<LoadMapPacket>.body(): LoadMapPacket {
    val flags =
        field(U8) {
          var f = 0
          if (it.deleteCache) f = f or 1
          if (it.reloadPlayer) f = f or 2
          f
        }
    val deleteCache = (flags and 1) != 0
    val reloadPlayer = (flags and 2) != 0
    val romType = field(U8, LoadMapPacket::romType)
    val bankId = field(U8, LoadMapPacket::bankId)
    val mapId = field(U8, LoadMapPacket::mapId)
    val regionId = field(U8, LoadMapPacket::regionId)
    val mapData: MapData =
        if (romType in SPECIAL_ROM_TYPES) {
          val getSpecial: (LoadMapPacket) -> MapData.SpecialMapData = {
            it.mapData as? MapData.SpecialMapData
                ?: throw MalformedPacketException("expected SpecialMapData for romType $romType")
          }
          val rF1 = field(U16LE) { getSpecial(it).rF1 }
          val borderConnections =
              field(BorderConnectionsPrefixedU8) { getSpecial(it).borderConnections }
          val lighting = Lighting.entries[field(U8) { getSpecial(it).lighting.ordinal }]
          val weather = Weather.entries[field(U8) { getSpecial(it).weather.ordinal }]
          val mapType = MapType.entries[field(U8) { getSpecial(it).mapType.ordinal }]
          MapData.SpecialMapData(rF1, borderConnections, lighting, weather, mapType)
        } else {
          val getGba: (LoadMapPacket) -> MapData.GbaMapData = {
            it.mapData as? MapData.GbaMapData
                ?: throw MalformedPacketException("expected GbaMapData for romType $romType")
          }
          val width = field(S32LE) { getGba(it).width }
          val height = field(S32LE) { getGba(it).height }
          val arg1 = field(S32LE) { getGba(it).arg1 }
          val arg2 = field(S32LE) { getGba(it).arg2 }
          val e30 = field(U8) { getGba(it).e30 }
          val zr0 = field(U8) { getGba(it).zr0 }
          val musicId = field(U16LE) { getGba(it).musicId }
          val unknownByte = field(U8) { getGba(it).unknownByte }
          val lighting = Lighting.entries[field(U8) { getGba(it).lighting.ordinal }]
          val weather = Weather.entries[field(U8) { getGba(it).weather.ordinal }]
          val mapType = MapType.entries[field(U8) { getGba(it).mapType.ordinal }]
          val encounterType = EncounterType.entries[field(U8) { getGba(it).encounterType.ordinal }]
          val tiles = List(e30 * zr0) { field(Tile2DCodec) { entry -> getGba(entry).tiles[it] } }
          val hasCompressed = field(Bool) { getGba(it).compressed != null }
          val compressed =
              if (hasCompressed) {
                val size = field(S32LE) { getGba(it).compressed?.size ?: 0 }
                field(FixedBytes(size)) { getGba(it).compressed ?: ByteArray(0) }
              } else {
                null
              }
          val connections = field(GbaConnectionListPrefixedU8) { getGba(it).connections }
          val hasOwner = field(Bool) { getGba(it).owner != null }
          val owner =
              if (hasOwner) {
                val ownerId = field(S64LE) { getGba(it).owner?.ownerId ?: 0L }
                val ownerName = field(Utf16LeNullTerminated) { getGba(it).owner?.ownerName ?: "" }
                MapData.MapOwner(ownerId, ownerName)
              } else {
                null
              }
          MapData.GbaMapData(
              width = width,
              height = height,
              arg1 = arg1,
              arg2 = arg2,
              e30 = e30,
              zr0 = zr0,
              musicId = musicId,
              unknownByte = unknownByte,
              lighting = lighting,
              weather = weather,
              mapType = mapType,
              encounterType = encounterType,
              tiles = tiles,
              compressed = compressed,
              connections = connections,
              owner = owner,
          )
        }
    return LoadMapPacket(reloadPlayer, deleteCache, romType, bankId, mapId, regionId, mapData)
  }
}

private fun FixedBytes(n: Int): Codec<ByteArray> =
    object : Codec<ByteArray> {
      override fun read(buf: ReadBuffer): ByteArray {
        val arr = ByteArray(n)
        if (n > 0) buf.readBytes(arr)
        return arr
      }

      override fun write(buf: WriteBuffer, value: ByteArray) {
        buf.writeBytes(value)
      }
    }
