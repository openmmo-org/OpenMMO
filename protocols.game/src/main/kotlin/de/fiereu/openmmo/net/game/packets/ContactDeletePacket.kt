package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class ContactDeletePacket(
    val entityId: Long,
)

object ContactDeletePacketCodec : PacketCodec<ContactDeletePacket>() {
  override fun CodecScope<ContactDeletePacket>.body(): ContactDeletePacket {
    val entityId = field(S64LE) { it.entityId }
    return ContactDeletePacket(entityId)
  }
}
