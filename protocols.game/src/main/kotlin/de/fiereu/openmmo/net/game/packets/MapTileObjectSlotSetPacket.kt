package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class MapTileObjectSlotSetPacket(
    val blockX: Byte,
    val blockY: Byte,
    val blockZ: Byte,
    val slot: Byte,
    val present: Boolean,
    val objectX: Short?,
    val objectY: Short?,
    val objectZ: Byte?,
    val relative: Boolean?,
)

object MapTileObjectSlotSetPacketCodec : PacketCodec<MapTileObjectSlotSetPacket>() {
  override fun CodecScope<MapTileObjectSlotSetPacket>.body(): MapTileObjectSlotSetPacket {
    val blockX = field(S8) { it.blockX }
    val blockY = field(S8) { it.blockY }
    val blockZ = field(S8) { it.blockZ }
    val slot = field(S8) { it.slot }
    val present = field(U8) { if (it.present) 1 else 0 } == 1
    val objectX = if (present) field(S16LE) { it.objectX!! } else null
    val objectY = if (present) field(S16LE) { it.objectY!! } else null
    val objectZ = if (present) field(S8) { it.objectZ!! } else null
    val relative = if (present) field(U8) { if (it.relative == true) 1 else 0 } == 1 else null
    return MapTileObjectSlotSetPacket(
        blockX, blockY, blockZ, slot, present, objectX, objectY, objectZ, relative)
  }
}
