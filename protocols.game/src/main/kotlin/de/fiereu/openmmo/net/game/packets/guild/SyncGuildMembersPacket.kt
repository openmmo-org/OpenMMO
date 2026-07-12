package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.*

data class GuildMemberEntry(
    val entityId: Long,
    val rank: Byte,
    val joinedAt: Int,
    val name: String,
    val online: Boolean,
    val lastSeen: Int,
    val appearance: List<Short>,
    val leader: Boolean,
)

data class SyncGuildMembersPacket(
    val replace: Boolean,
    val members: List<GuildMemberEntry>,
)

private object GuildMemberEntryCodec : PacketCodec<GuildMemberEntry>() {
  override fun CodecScope<GuildMemberEntry>.body(): GuildMemberEntry {
    val entityId = field(S64LE, GuildMemberEntry::entityId)
    val rank = field(S8, GuildMemberEntry::rank)
    val joinedAt = field(S32LE, GuildMemberEntry::joinedAt)
    val name = field(Utf16LeNullTerminated, GuildMemberEntry::name)
    val online = field(Bool, GuildMemberEntry::online)
    val lastSeen = field(S32LE, GuildMemberEntry::lastSeen)
    val appearance = field(S16LE.repeat(5), GuildMemberEntry::appearance)
    val leader = field(Bool, GuildMemberEntry::leader)
    return GuildMemberEntry(entityId, rank, joinedAt, name, online, lastSeen, appearance, leader)
  }
}

private val GuildMemberEntryListPrefixedU8: Codec<List<GuildMemberEntry>> =
    object : Codec<List<GuildMemberEntry>> {
      override fun read(buf: ReadBuffer): List<GuildMemberEntry> {
        val n = U8.read(buf)
        return List(n) { GuildMemberEntryCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<GuildMemberEntry>) {
        U8.write(buf, value.size)
        value.forEach { GuildMemberEntryCodec.write(buf, it) }
      }
    }

object SyncGuildMembersPacketCodec : PacketCodec<SyncGuildMembersPacket>() {
  override fun CodecScope<SyncGuildMembersPacket>.body(): SyncGuildMembersPacket {
    val replace = field(Bool, SyncGuildMembersPacket::replace)
    val members = field(GuildMemberEntryListPrefixedU8, SyncGuildMembersPacket::members)
    return SyncGuildMembersPacket(replace, members)
  }
}
