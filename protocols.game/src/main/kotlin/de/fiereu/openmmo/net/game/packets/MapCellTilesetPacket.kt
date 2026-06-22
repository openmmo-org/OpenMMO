package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class MapCellTilesetPacket(
    val chunkX: Byte,
    val chunkY: Byte,
    val chunkLevel: Byte,
    val tilesetIndex: Short,
)

object MapCellTilesetPacketCodec : PacketCodec<MapCellTilesetPacket>() {
    override fun CodecScope<MapCellTilesetPacket>.body(): MapCellTilesetPacket {
        val chunkX = field(S8) { it.chunkX }
        val chunkY = field(S8) { it.chunkY }
        val chunkLevel = field(S8) { it.chunkLevel }
        val tilesetIndex = field(S16LE) { it.tilesetIndex }
        return MapCellTilesetPacket(chunkX, chunkY, chunkLevel, tilesetIndex)
    }
}
