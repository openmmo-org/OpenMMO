package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class GuildMemberKickPacket(
    val targetEntityId: Long,
)

object GuildMemberKickPacketCodec : PacketCodec<GuildMemberKickPacket>() {
  override fun CodecScope<GuildMemberKickPacket>.body(): GuildMemberKickPacket {
    val targetEntityId = field(S64LE) { it.targetEntityId }
    return GuildMemberKickPacket(targetEntityId)
  }
}
