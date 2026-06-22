package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GuildActivityLogEntry(
    val type: Int,
    val actor: String,
    val target: String,
    val timestamp: Int,
)

data class GuildActivityLogPacket(
    val totalCount: Short,
    val entries: List<GuildActivityLogEntry>,
)

private object GuildActivityLogEntryCodec : PacketCodec<GuildActivityLogEntry>() {
  override fun CodecScope<GuildActivityLogEntry>.body(): GuildActivityLogEntry {
    val type = field(U8, GuildActivityLogEntry::type)
    val actor = field(Utf16LeNullTerminated, GuildActivityLogEntry::actor)
    val target = field(Utf16LeNullTerminated, GuildActivityLogEntry::target)
    val timestamp = field(S32LE, GuildActivityLogEntry::timestamp)
    return GuildActivityLogEntry(type, actor, target, timestamp)
  }
}

private val GuildActivityLogEntryListPrefixedU8: Codec<List<GuildActivityLogEntry>> =
    object : Codec<List<GuildActivityLogEntry>> {
      override fun read(buf: ReadBuffer): List<GuildActivityLogEntry> {
        val n = U8.read(buf)
        return List(n) { GuildActivityLogEntryCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<GuildActivityLogEntry>) {
        U8.write(buf, value.size)
        value.forEach { GuildActivityLogEntryCodec.write(buf, it) }
      }
    }

object GuildActivityLogPacketCodec : PacketCodec<GuildActivityLogPacket>() {
  override fun CodecScope<GuildActivityLogPacket>.body(): GuildActivityLogPacket {
    val totalCount = field(S16LE, GuildActivityLogPacket::totalCount)
    val entries = field(GuildActivityLogEntryListPrefixedU8, GuildActivityLogPacket::entries)
    return GuildActivityLogPacket(totalCount, entries)
  }
}
