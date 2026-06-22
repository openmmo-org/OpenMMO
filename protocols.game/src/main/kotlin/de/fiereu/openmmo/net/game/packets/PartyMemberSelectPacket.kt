package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class PartyMemberSelectPacket(val selectionType: Byte, val entityId: Long)

object PartyMemberSelectPacketCodec : PacketCodec<PartyMemberSelectPacket>() {
  override fun CodecScope<PartyMemberSelectPacket>.body(): PartyMemberSelectPacket {
    val selectionType = field(S8) { it.selectionType }
    val entityId = field(S64LE) { it.entityId }
    return PartyMemberSelectPacket(selectionType, entityId)
  }
}
