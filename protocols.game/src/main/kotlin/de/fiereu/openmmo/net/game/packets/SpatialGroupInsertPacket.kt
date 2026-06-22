package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SpatialGroupInsertPacket(
    val group: Int,
    val entityId: Long,
    val value: Short,
    val extraCoordPresent: Boolean,
    val extraValue: Short?,
)

object SpatialGroupInsertPacketCodec : PacketCodec<SpatialGroupInsertPacket>() {
  override fun CodecScope<SpatialGroupInsertPacket>.body(): SpatialGroupInsertPacket {
    val group = field(U8) { it.group }
    val entityId = field(S64LE) { it.entityId }
    val value = field(S16LE) { it.value }
    val extraCoordPresent = false
    val extraValue: Short? = if (extraCoordPresent) field(S16LE) { it.extraValue ?: 0 } else null
    return SpatialGroupInsertPacket(group, entityId, value, extraCoordPresent, extraValue)
  }
}
