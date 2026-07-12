package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class GuildInvitePacket(
    val targetName: String,
)

object GuildInvitePacketCodec : PacketCodec<GuildInvitePacket>() {
  override fun CodecScope<GuildInvitePacket>.body(): GuildInvitePacket {
    val targetName = field(Utf16LeNullTerminated) { it.targetName }
    return GuildInvitePacket(targetName)
  }
}
