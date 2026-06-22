package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class MatchmakingRankingEntry(
    val entityId: Long,
    val rank: Byte,
    val flags: Byte,
    val name: String,
    val value1: Short,
    val species: Short,
    val ribbons: Long,
    val value2: Byte,
    val value3: Int,
    val value4: Byte,
    val flag1: Boolean,
    val flag2: Boolean,
)

data class MatchmakingSideTeamPacket(
    val sessionId: Byte,
    val opponentSide: Boolean,
    val page: Short,
    val slot: Int,
    val team: List<MatchmakingRankingEntry>,
)

private object MatchmakingRankingEntryCodec : PacketCodec<MatchmakingRankingEntry>() {
  override fun CodecScope<MatchmakingRankingEntry>.body(): MatchmakingRankingEntry {
    val entityId = field(S64LE, MatchmakingRankingEntry::entityId)
    val rank = field(S8, MatchmakingRankingEntry::rank)
    val flags = field(S8, MatchmakingRankingEntry::flags)
    val name = field(Utf16LeNullTerminated, MatchmakingRankingEntry::name)
    val value1 = field(S16LE, MatchmakingRankingEntry::value1)
    val species = field(S16LE, MatchmakingRankingEntry::species)
    reserved(byte = 0)
    val ribbons = field(S64LE, MatchmakingRankingEntry::ribbons)
    val value2 = field(S8, MatchmakingRankingEntry::value2)
    val value3 = field(S32LE, MatchmakingRankingEntry::value3)
    val value4 = field(S8, MatchmakingRankingEntry::value4)
    val flag1 = field(Bool, MatchmakingRankingEntry::flag1)
    val flag2 = field(Bool, MatchmakingRankingEntry::flag2)
    reserved(byte = 0)
    return MatchmakingRankingEntry(
        entityId,
        rank,
        flags,
        name,
        value1,
        species,
        ribbons,
        value2,
        value3,
        value4,
        flag1,
        flag2,
    )
  }
}

private val MatchmakingRankingEntryListPrefixedU8: Codec<List<MatchmakingRankingEntry>> =
    object : Codec<List<MatchmakingRankingEntry>> {
      override fun read(buf: ReadBuffer): List<MatchmakingRankingEntry> {
        val n = U8.read(buf)
        return List(n) { MatchmakingRankingEntryCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<MatchmakingRankingEntry>) {
        U8.write(buf, value.size)
        value.forEach { MatchmakingRankingEntryCodec.write(buf, it) }
      }
    }

object MatchmakingSideTeamPacketCodec : PacketCodec<MatchmakingSideTeamPacket>() {
  override fun CodecScope<MatchmakingSideTeamPacket>.body(): MatchmakingSideTeamPacket {
    val sessionId = field(S8, MatchmakingSideTeamPacket::sessionId)
    val opponentSide = field(Bool, MatchmakingSideTeamPacket::opponentSide)
    val page = field(S16LE, MatchmakingSideTeamPacket::page)
    val slot = field(S32LE, MatchmakingSideTeamPacket::slot)
    val team = field(MatchmakingRankingEntryListPrefixedU8, MatchmakingSideTeamPacket::team)
    return MatchmakingSideTeamPacket(sessionId, opponentSide, page, slot, team)
  }
}
