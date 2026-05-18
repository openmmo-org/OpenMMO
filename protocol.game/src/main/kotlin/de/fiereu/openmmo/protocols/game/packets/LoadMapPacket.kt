package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.common.Tile2D
import de.fiereu.openmmo.common.enums.EncounterType
import de.fiereu.openmmo.common.enums.Lighting
import de.fiereu.openmmo.common.enums.MapType
import de.fiereu.openmmo.common.enums.Weather
import de.fiereu.openmmo.common.utils.gzipCompress
import de.fiereu.openmmo.common.utils.gzipDecompress
import de.fiereu.openmmo.common.utils.isNdsRegion
import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import de.fiereu.openmmo.protocols.game.packets.codecs.readTile2DLE
import de.fiereu.openmmo.protocols.game.packets.codecs.writeTile2DLE
import de.fiereu.openmmo.protocols.readUtf16LE
import de.fiereu.openmmo.protocols.writeUtf16LE
import io.netty.buffer.ByteBuf

data class LoadMapPacket(
    val reloadPlayer: Boolean,
    val deleteCache: Boolean,
    val regionId: Int,
    val bankId: Int,
    val mapId: Int,
    val mapData: MapData
) {
  init {
    when (mapData) {
      is MapData.NdsMapData -> require(isNdsRegion(regionId)) { "MapData is invalid" }
      is MapData.GbaMapData -> require(!isNdsRegion(regionId)) { "MapData is invalid" }
    }
  }
}

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

class LoadMapPacketSerializer : PacketSerializer<LoadMapPacket> {
  override fun serialize(packet: LoadMapPacket, buffer: ByteBuf) {
    buffer.apply {
      var flags = 0
      if (packet.deleteCache) flags = flags or 1
      if (packet.reloadPlayer) flags = flags or 2

      writeByte(flags)

      writeByte(packet.regionId) // Client will stop parsing if the region is not loaded
      writeByte(packet.bankId)
      writeByte(packet.mapId)
      writeByte(0)

      when (packet.mapData) {
        is MapData.NdsMapData -> {
          writeShortLE(0)
          val unk = 0
          writeByte(unk)
          for (i in 0 until unk) {
            writeShortLE(0)
            writeShortLE(0)
          }

          writeByte(packet.mapData.lighting.ordinal)
          writeByte(packet.mapData.weather.ordinal)
          writeByte(packet.mapData.mapType.ordinal)
        }
        is MapData.GbaMapData -> {
          writeIntLE(packet.mapData.width)
          writeIntLE(packet.mapData.height)
          writeIntLE(packet.mapData.paletteIdx1)
          writeIntLE(packet.mapData.paletteIdx2)
          writeByte(packet.mapData.borderWidth)
          writeByte(packet.mapData.borderHeight)
          writeShortLE(packet.mapData.unknownShort)
          writeByte(packet.mapData.unknownByte)
          writeByte(packet.mapData.lighting.ordinal)
          writeByte(packet.mapData.weather.ordinal)
          writeByte(packet.mapData.mapType.ordinal)
          writeByte(packet.mapData.encounterType.ordinal)

          for (tile2D in packet.mapData.borderTiles) {
            writeTile2DLE(tile2D)
          }

          val hasUnknown = false
          writeBoolean(hasUnknown)
          if (hasUnknown) {
            val unknown = ByteArray(0)
            writeIntLE(unknown.size)
            writeBytes(unknown.gzipCompress())
          }

          val connections = packet.mapData.connections
          writeByte(connections.size)
          for (conn in connections) {
            writeByte(conn.direction)
            writeIntLE(conn.unknown)
            writeByte(conn.targetBank)
            writeByte(conn.targetMap)
          }

          val hasUnknown2 = false
          writeBoolean(hasUnknown2)
          if (hasUnknown2) {
            writeLongLE(0)
            writeUtf16LE("")
          }
        }
      }
    }
  }
}

class LoadMapPacketDeserializer : PacketDeserializer<LoadMapPacket> {
  override fun deserialize(buffer: ByteBuf): LoadMapPacket =
      buffer.let {
        val flags = buffer.readByte().toInt()
        val deleteCache = (flags and 1) != 0
        val reloadPlayer = (flags and 2) != 0

        val regionId = buffer.readByte().toInt()
        val bankId = buffer.readByte().toInt()
        val mapId = buffer.readByte().toInt()
        buffer.readByte()

        val mapData =
            if (isNdsRegion(regionId)) {
              // NDS format
              buffer.readShortLE()
              val unk = buffer.readByte().toInt()
              for (i in 0 until unk) {
                buffer.readShortLE()
                buffer.readShortLE()
              }
              val lighting = Lighting.entries[buffer.readByte().toInt()]
              val weather = Weather.entries[buffer.readByte().toInt()]
              val mapType = MapType.entries[buffer.readByte().toInt()]
              MapData.NdsMapData(lighting, weather, mapType)
            } else {
              // GBA format
              val width = buffer.readIntLE()
              val height = buffer.readIntLE()
              val paletteIdx1 = buffer.readIntLE()
              val paletteIdx2 = buffer.readIntLE()
              val borderWidth = buffer.readByte().toInt()
              val borderHeight = buffer.readByte().toInt()
              val unknownShort = buffer.readShortLE().toInt()
              val unknownByte = buffer.readByte().toInt()
              val lighting = Lighting.entries[buffer.readByte().toInt()]
              val weather = Weather.entries[buffer.readByte().toInt()]
              val mapType = MapType.entries[buffer.readByte().toInt()]
              val encounterType = EncounterType.entries[buffer.readByte().toInt()]

              val borderTiles = mutableListOf<Tile2D>()
              for (i in 0 until (borderWidth * borderHeight)) {
                borderTiles.add(buffer.readTile2DLE())
              }

              val hasUnknown = buffer.readBoolean()
              if (hasUnknown) {
                val unknownSize = buffer.readIntLE()
                val unknownCompressed = ByteArray(unknownSize)
                buffer.readBytes(unknownCompressed)
                unknownCompressed.gzipDecompress()
              }

              val connectionCount = buffer.readByte().toInt()
              val connections = mutableListOf<MapData.GbaConnection>()
              for (i in 0 until connectionCount) {
                val direction = buffer.readByte().toInt()
                val unknown = buffer.readIntLE()
                val targetBank = buffer.readByte().toInt()
                val targetMap = buffer.readByte().toInt()
                connections.add(MapData.GbaConnection(direction, unknown, targetBank, targetMap))
              }

              val hasUnknown2 = buffer.readBoolean()
              if (hasUnknown2) {
                buffer.readLongLE()
                buffer.readUtf16LE()
              }

              MapData.GbaMapData(
                  width,
                  height,
                  paletteIdx1,
                  paletteIdx2,
                  borderWidth,
                  borderHeight,
                  unknownShort,
                  unknownByte,
                  borderTiles,
                  lighting,
                  weather,
                  mapType,
                  encounterType,
                  connections = connections)
            }

        LoadMapPacket(reloadPlayer, deleteCache, regionId, bankId, mapId, mapData)
      }
}
