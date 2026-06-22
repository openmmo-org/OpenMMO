package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class PartyMemberJoinPacket(
    val player: Long,
    val name: String,
    val secondaryName: String,
    val value: Int,
)

object PartyMemberJoinPacketCodec : PacketCodec<PartyMemberJoinPacket>() {
  override fun CodecScope<PartyMemberJoinPacket>.body(): PartyMemberJoinPacket {
    val player = field(S64LE) { it.player }
    val name = field(Utf16LeNullTerminated) { it.name }
    val secondaryName = field(Utf16LeNullTerminated) { it.secondaryName }
    val value = field(S32LE) { it.value }
    return PartyMemberJoinPacket(player, name, secondaryName, value)
  }
}
