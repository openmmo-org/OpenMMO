package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GuildRosterMember(
    val rank: Byte,
    val entityId: Long,
    val value: Int,
    val appearance: GuildMemberAppearance,
    val leader: Boolean,
)

data class GuildRosterBulkPacket(
    val replace: Boolean,
    val members: List<GuildRosterMember>,
)

private object GuildRosterMemberCodec : PacketCodec<GuildRosterMember>() {
  override fun CodecScope<GuildRosterMember>.body(): GuildRosterMember {
    val rank = field(S8, GuildRosterMember::rank)
    val entityId = field(S64LE, GuildRosterMember::entityId)
    val value = field(S32LE, GuildRosterMember::value)
    val appearance = field(GuildMemberAppearanceCodec, GuildRosterMember::appearance)
    val leader = field(Bool, GuildRosterMember::leader)
    return GuildRosterMember(rank, entityId, value, appearance, leader)
  }
}

private val GuildRosterMemberListPrefixedU8: Codec<List<GuildRosterMember>> =
    object : Codec<List<GuildRosterMember>> {
      override fun read(buf: ReadBuffer): List<GuildRosterMember> {
        val n = U8.read(buf)
        return List(n) { GuildRosterMemberCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<GuildRosterMember>) {
        U8.write(buf, value.size)
        value.forEach { GuildRosterMemberCodec.write(buf, it) }
      }
    }

object GuildRosterBulkPacketCodec : PacketCodec<GuildRosterBulkPacket>() {
  override fun CodecScope<GuildRosterBulkPacket>.body(): GuildRosterBulkPacket {
    val replace = field(Bool, GuildRosterBulkPacket::replace)
    val members = field(GuildRosterMemberListPrefixedU8, GuildRosterBulkPacket::members)
    return GuildRosterBulkPacket(replace, members)
  }
}
