package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class TeamFoundPacket(
    val teamName: String,
    val teamTag: String,
)

object TeamFoundPacketCodec : PacketCodec<TeamFoundPacket>() {
  override fun CodecScope<TeamFoundPacket>.body(): TeamFoundPacket {
    val teamName = field(Utf16LeNullTerminated) { it.teamName }
    val teamTag = field(Utf16LeNullTerminated) { it.teamTag }
    return TeamFoundPacket(teamName, teamTag)
  }
}
