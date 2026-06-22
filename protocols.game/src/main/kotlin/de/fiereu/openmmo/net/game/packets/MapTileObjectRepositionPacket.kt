package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class MapTileObjectRepositionPacket(
    val chunkX: Byte,
    val chunkY: Byte,
    val chunkZ: Byte,
    val tileX: Short,
    val tileY: Short,
    val tileZ: Byte,
    val objectIndex: Byte,
)

object MapTileObjectRepositionPacketCodec : PacketCodec<MapTileObjectRepositionPacket>() {
  override fun CodecScope<MapTileObjectRepositionPacket>.body(): MapTileObjectRepositionPacket {
    val chunkX = field(S8) { it.chunkX }
    val chunkY = field(S8) { it.chunkY }
    val chunkZ = field(S8) { it.chunkZ }
    val tileX = field(S16LE) { it.tileX }
    val tileY = field(S16LE) { it.tileY }
    val tileZ = field(S8) { it.tileZ }
    val objectIndex = field(S8) { it.objectIndex }
    return MapTileObjectRepositionPacket(chunkX, chunkY, chunkZ, tileX, tileY, tileZ, objectIndex)
  }
}
