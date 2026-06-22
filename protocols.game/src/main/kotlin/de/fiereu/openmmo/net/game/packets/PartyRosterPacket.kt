package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class PartyMember(
    val player: Long,
    val name: String,
    val secondaryName: String,
    val value: Int,
)

data class PartyRosterPacket(
    val mergeMode: Int,
    val members: List<PartyMember>,
)

private val PartyMemberCodec: Codec<PartyMember> =
    object : PacketCodec<PartyMember>() {
      override fun CodecScope<PartyMember>.body(): PartyMember {
        val player = field(S64LE) { it.player }
        val name = field(Utf16LeNullTerminated) { it.name }
        val secondaryName = field(Utf16LeNullTerminated) { it.secondaryName }
        val value = field(S32LE) { it.value }
        return PartyMember(player, name, secondaryName, value)
      }
    }

object PartyRosterPacketCodec : PacketCodec<PartyRosterPacket>() {
  override fun CodecScope<PartyRosterPacket>.body(): PartyRosterPacket {
    val mergeMode = field(U8) { it.mergeMode }
    val members = field(PartyMemberCodec.listPrefixed(U8)) { it.members }
    return PartyRosterPacket(mergeMode, members)
  }
}
