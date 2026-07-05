package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class GuildMemberExpelPacket(
    val targetEntityId: Long,
)

object GuildMemberExpelPacketCodec : PacketCodec<GuildMemberExpelPacket>() {
  override fun CodecScope<GuildMemberExpelPacket>.body(): GuildMemberExpelPacket {
    val targetEntityId = field(S64LE) { it.targetEntityId }
    return GuildMemberExpelPacket(targetEntityId)
  }
}
