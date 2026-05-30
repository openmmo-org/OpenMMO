package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.MalformedPacketException
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.ReadBuffer
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.Utf16LeNullTerminated
import de.fiereu.bytecodec.WriteBuffer
import de.fiereu.openmmo.common.Tile2D
import de.fiereu.openmmo.common.enums.EncounterType
import de.fiereu.openmmo.common.enums.Lighting
import de.fiereu.openmmo.common.enums.MapType
import de.fiereu.openmmo.common.enums.Weather
import de.fiereu.openmmo.common.utils.gzipDecompress
import de.fiereu.openmmo.common.utils.isNdsRegion
import de.fiereu.openmmo.net.game.codecs.Tile2DCodec

sealed interface MapData {
  data class NdsMapData(val lighting: Lighting, val weather: Weather, val mapType: MapType) :
      MapData

  data class GbaConnection(
      val direction: Int,
      val unknown: Int,
      val targetBank: Int,
      val targetMap: Int,
  )

  data class GbaMapData(
      val width: Int,
      val height: Int,
      val paletteIdx1: Int,
      val paletteIdx2: Int,
      val borderWidth: Int,
      val borderHeight: Int,
      val unknownShort: Int = 0,
      val unknownByte: Int = 0,
      val borderTiles: List<Tile2D>,
      val lighting: Lighting,
      val weather: Weather,
      val mapType: MapType,
      val encounterType: EncounterType,
      val connections: List<GbaConnection> = emptyList(),
  ) : MapData {
    init {
      require(borderWidth * borderHeight == borderTiles.size) {
        "borderTiles size doesnt match dimensions"
      }
      require(borderWidth * borderHeight > 0) { "BorderWidth & Height must be greater than zero" }
    }
  }
}

data class LoadMapPacket(
    val reloadPlayer: Boolean,
    val deleteCache: Boolean,
    val regionId: Int,
    val bankId: Int,
    val mapId: Int,
    val mapData: MapData,
) {
  init {
    when (mapData) {
      is MapData.NdsMapData -> require(isNdsRegion(regionId)) { "MapData is invalid" }
      is MapData.GbaMapData -> require(!isNdsRegion(regionId)) { "MapData is invalid" }
    }
  }
}

private object GbaConnectionCodec : PacketCodec<MapData.GbaConnection>() {
  override fun CodecScope<MapData.GbaConnection>.body() =
      MapData.GbaConnection(
          direction = field(U8, MapData.GbaConnection::direction),
          unknown = field(S32LE, MapData.GbaConnection::unknown),
          targetBank = field(U8, MapData.GbaConnection::targetBank),
          targetMap = field(U8, MapData.GbaConnection::targetMap),
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
    val regionId = field(U8, LoadMapPacket::regionId)
    val bankId = field(U8, LoadMapPacket::bankId)
    val mapId = field(U8, LoadMapPacket::mapId)
    field(S8) { 0 }
    val mapData: MapData =
        if (isNdsRegion(regionId)) {
          field(S16LE) { 0 }
          field(U8) { 0 }
          val lighting =
              Lighting.entries[field(U8) { (it.mapData as MapData.NdsMapData).lighting.ordinal }]
          val weather =
              Weather.entries[field(U8) { (it.mapData as MapData.NdsMapData).weather.ordinal }]
          val mapType =
              MapType.entries[field(U8) { (it.mapData as MapData.NdsMapData).mapType.ordinal }]
          MapData.NdsMapData(lighting, weather, mapType)
        } else {
          val getGba: (LoadMapPacket) -> MapData.GbaMapData = {
            it.mapData as? MapData.GbaMapData
                ?: throw MalformedPacketException("expected GbaMapData for region $regionId")
          }
          val width = field(S32LE) { getGba(it).width }
          val height = field(S32LE) { getGba(it).height }
          val paletteIdx1 = field(S32LE) { getGba(it).paletteIdx1 }
          val paletteIdx2 = field(S32LE) { getGba(it).paletteIdx2 }
          val borderWidth = field(U8) { getGba(it).borderWidth }
          val borderHeight = field(U8) { getGba(it).borderHeight }
          val unknownShort = field(S16LE) { getGba(it).unknownShort.toShort() }.toInt()
          val unknownByte = field(U8) { getGba(it).unknownByte }
          val lighting = Lighting.entries[field(U8) { getGba(it).lighting.ordinal }]
          val weather = Weather.entries[field(U8) { getGba(it).weather.ordinal }]
          val mapType = MapType.entries[field(U8) { getGba(it).mapType.ordinal }]
          val encounterType = EncounterType.entries[field(U8) { getGba(it).encounterType.ordinal }]
          val borderTiles =
              List(borderWidth * borderHeight) {
                field(Tile2DCodec) { entry -> getGba(entry).borderTiles[it] }
              }
          val hasCompressed = field(Bool) { false }
          if (hasCompressed) {
            val size = field(S32LE) { 0 }
            val compressed = field(FixedReadBytes(size)) { ByteArray(0) }
            compressed.gzipDecompress()
          }
          val connections = field(GbaConnectionListPrefixedU8) { getGba(it).connections }
          val hasTrailer = field(Bool) { false }
          if (hasTrailer) {
            field(S64LE) { 0L }
            field(Utf16LeNullTerminated) { "" }
          }
          MapData.GbaMapData(
              width = width,
              height = height,
              paletteIdx1 = paletteIdx1,
              paletteIdx2 = paletteIdx2,
              borderWidth = borderWidth,
              borderHeight = borderHeight,
              unknownShort = unknownShort,
              unknownByte = unknownByte,
              borderTiles = borderTiles,
              lighting = lighting,
              weather = weather,
              mapType = mapType,
              encounterType = encounterType,
              connections = connections,
          )
        }
    return LoadMapPacket(reloadPlayer, deleteCache, regionId, bankId, mapId, mapData)
  }
}

private fun FixedReadBytes(n: Int): Codec<ByteArray> =
    object : Codec<ByteArray> {
      override fun read(buf: ReadBuffer): ByteArray {
        val arr = ByteArray(n)
        if (n > 0) buf.readBytes(arr)
        return arr
      }

      override fun write(buf: WriteBuffer, value: ByteArray) {
        error("FixedReadBytes is read-only")
      }
    }
