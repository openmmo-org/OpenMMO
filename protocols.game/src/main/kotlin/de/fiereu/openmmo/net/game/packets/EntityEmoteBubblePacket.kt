package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class EntityEmoteBubblePacket(
    val entityId: Long,
    val icon: Byte,
    val param: Short?,
)

object EntityEmoteBubblePacketCodec : PacketCodec<EntityEmoteBubblePacket>() {
  override fun CodecScope<EntityEmoteBubblePacket>.body(): EntityEmoteBubblePacket {
    val entityId = field(S64LE) { it.entityId }
    val icon = field(S8) { it.icon }
    val param =
        if (icon != (-1).toByte()) {
          val p = field(S16LE) { it.param!! }
          field(S8) { 0 }
          field(S8) { 0 }
          p
        } else null
    return EntityEmoteBubblePacket(entityId, icon, param)
  }
}
