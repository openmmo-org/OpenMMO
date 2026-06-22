package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class MapTileAttributeSetPacket(
    val blockX: Byte,
    val blockY: Byte,
    val blockZ: Byte,
    val attribute: Byte,
)

object MapTileAttributeSetPacketCodec : PacketCodec<MapTileAttributeSetPacket>() {
  override fun CodecScope<MapTileAttributeSetPacket>.body(): MapTileAttributeSetPacket {
    val blockX = field(S8) { it.blockX }
    val blockY = field(S8) { it.blockY }
    val blockZ = field(S8) { it.blockZ }
    val attribute = field(S8) { it.attribute }
    return MapTileAttributeSetPacket(blockX, blockY, blockZ, attribute)
  }
}
