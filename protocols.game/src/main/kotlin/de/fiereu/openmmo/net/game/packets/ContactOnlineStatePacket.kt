package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U8

data class ContactOnlineStatePacket(
    val contactId: Long,
    val online: Boolean,
)

object ContactOnlineStatePacketCodec : PacketCodec<ContactOnlineStatePacket>() {
  override fun CodecScope<ContactOnlineStatePacket>.body(): ContactOnlineStatePacket {
    val contactId = field(S64LE) { it.contactId }
    val online = field(U8) { if (it.online) 1 else 0 } == 1
    return ContactOnlineStatePacket(contactId, online)
  }
}
