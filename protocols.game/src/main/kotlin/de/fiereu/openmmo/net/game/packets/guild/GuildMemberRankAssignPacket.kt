package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U8

data class GuildMemberRankAssignPacket(
    val memberEntityId: Long,
    val rankOrdinal: Int,
)

object GuildMemberRankAssignPacketCodec : PacketCodec<GuildMemberRankAssignPacket>() {
  override fun CodecScope<GuildMemberRankAssignPacket>.body(): GuildMemberRankAssignPacket {
    val memberEntityId = field(S64LE) { it.memberEntityId }
    val rankOrdinal = field(U8) { it.rankOrdinal }
    return GuildMemberRankAssignPacket(memberEntityId, rankOrdinal)
  }
}
