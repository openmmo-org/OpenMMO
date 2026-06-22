package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class EntityFaceTurnPacket(
    val entityId: Long,
    val facing: Byte,
)

object EntityFaceTurnPacketCodec : PacketCodec<EntityFaceTurnPacket>() {
  override fun CodecScope<EntityFaceTurnPacket>.body(): EntityFaceTurnPacket {
    val entityId = field(S64LE) { it.entityId }
    val facing = field(S8) { it.facing }
    return EntityFaceTurnPacket(entityId, facing)
  }
}
