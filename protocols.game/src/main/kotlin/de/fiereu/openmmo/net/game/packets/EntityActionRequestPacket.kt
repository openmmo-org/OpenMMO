package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S64LE

data class EntityActionRequestPacket(
    val targetEntityId: Long,
    val actionType: Short,
    val sourceEntityId: Long,
    val entityId: Long,
)

object EntityActionRequestPacketCodec : PacketCodec<EntityActionRequestPacket>() {
  override fun CodecScope<EntityActionRequestPacket>.body(): EntityActionRequestPacket {
    val targetEntityId = field(S64LE) { it.targetEntityId }
    val actionType = field(S16LE) { it.actionType }
    val sourceEntityId = field(S64LE) { it.sourceEntityId }
    val entityId = field(S64LE) { it.entityId }
    return EntityActionRequestPacket(targetEntityId, actionType, sourceEntityId, entityId)
  }
}
