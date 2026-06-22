package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class EntityPanelSelectPacket(
    val entityId: Long,
)

object EntityPanelSelectPacketCodec : PacketCodec<EntityPanelSelectPacket>() {
  override fun CodecScope<EntityPanelSelectPacket>.body(): EntityPanelSelectPacket {
    val entityId = field(S64LE) { it.entityId }
    return EntityPanelSelectPacket(entityId)
  }
}
