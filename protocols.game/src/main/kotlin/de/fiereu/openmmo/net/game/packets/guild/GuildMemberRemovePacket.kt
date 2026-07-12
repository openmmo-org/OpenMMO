package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class GuildMemberRemovePacket(
    val memberId: Long,
)

object GuildMemberRemovePacketCodec : PacketCodec<GuildMemberRemovePacket>() {
  override fun CodecScope<GuildMemberRemovePacket>.body(): GuildMemberRemovePacket {
    field(S64LE) { 0L }
    val memberId = field(S64LE, GuildMemberRemovePacket::memberId)
    return GuildMemberRemovePacket(memberId)
  }
}
