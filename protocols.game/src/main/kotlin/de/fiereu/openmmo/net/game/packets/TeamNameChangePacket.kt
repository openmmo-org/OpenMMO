package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class TeamNameChangePacket(
    val checkOnly: Boolean,
    val teamId: Short,
    val teamName: String,
    val teamTag: String,
)

object TeamNameChangePacketCodec : PacketCodec<TeamNameChangePacket>() {
  override fun CodecScope<TeamNameChangePacket>.body(): TeamNameChangePacket {
    val checkOnly = field(Bool) { it.checkOnly }
    val teamId = field(S16LE) { it.teamId }
    val teamName = field(Utf16LeNullTerminated) { it.teamName }
    val teamTag = field(Utf16LeNullTerminated) { it.teamTag }
    return TeamNameChangePacket(checkOnly, teamId, teamName, teamTag)
  }
}
