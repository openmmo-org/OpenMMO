package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class EntityMovePpPacket(
    val entityId: Long,
    val moveSlot: Byte,
    val ppValue: Byte,
)

object EntityMovePpPacketCodec : PacketCodec<EntityMovePpPacket>() {
  override fun CodecScope<EntityMovePpPacket>.body(): EntityMovePpPacket {
    val entityId = field(S64LE) { it.entityId }
    val moveSlot = field(S8) { it.moveSlot }
    val ppValue = field(S8) { it.ppValue }
    return EntityMovePpPacket(entityId, moveSlot, ppValue)
  }
}
