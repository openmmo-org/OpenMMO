package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class GuildMemberRankChangePacket(
    val memberId: Long,
    val rank: Byte,
)

object GuildMemberRankChangePacketCodec : PacketCodec<GuildMemberRankChangePacket>() {
  override fun CodecScope<GuildMemberRankChangePacket>.body(): GuildMemberRankChangePacket {
    field(S64LE) { 0L }
    val memberId = field(S64LE, GuildMemberRankChangePacket::memberId)
    val rank = field(S8, GuildMemberRankChangePacket::rank)
    return GuildMemberRankChangePacket(memberId, rank)
  }
}
