package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class PartyMemberLeavePacket(
    val memberId: Long,
)

object PartyMemberLeavePacketCodec : PacketCodec<PartyMemberLeavePacket>() {
  override fun CodecScope<PartyMemberLeavePacket>.body(): PartyMemberLeavePacket {
    val memberId = field(S64LE) { it.memberId }
    return PartyMemberLeavePacket(memberId)
  }
}
