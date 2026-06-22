package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GuildProfileSyncPacket(
    val guildId: Long,
    val name: String,
    val tag: String,
    val memberCount: Int,
    val description: String,
    val motd: Int,
    val value1: Short,
    val value2: Short,
    val value3: Short,
    val value4: Short,
    val value5: Short,
    val value6: Int,
    val rankNames: List<String>,
)

object GuildProfileSyncPacketCodec : PacketCodec<GuildProfileSyncPacket>() {
  override fun CodecScope<GuildProfileSyncPacket>.body(): GuildProfileSyncPacket {
    val guildId = field(S64LE, GuildProfileSyncPacket::guildId)
    val name = field(Utf16LeNullTerminated, GuildProfileSyncPacket::name)
    val tag = field(Utf16LeNullTerminated, GuildProfileSyncPacket::tag)
    val memberCount = field(S32LE, GuildProfileSyncPacket::memberCount)
    val description = field(Utf16LeNullTerminated, GuildProfileSyncPacket::description)
    val motd = field(S32LE, GuildProfileSyncPacket::motd)
    val value1 = field(S16LE, GuildProfileSyncPacket::value1)
    val value2 = field(S16LE, GuildProfileSyncPacket::value2)
    val value3 = field(S16LE, GuildProfileSyncPacket::value3)
    val value4 = field(S16LE, GuildProfileSyncPacket::value4)
    val value5 = field(S16LE, GuildProfileSyncPacket::value5)
    val value6 = field(S32LE, GuildProfileSyncPacket::value6)
    val rankCount = field(S8) { it.rankNames.size.toByte() }
    val rankNames =
        List(rankCount.toInt()) { i -> field(Utf16LeNullTerminated) { it.rankNames[i] } }
    return GuildProfileSyncPacket(
        guildId,
        name,
        tag,
        memberCount,
        description,
        motd,
        value1,
        value2,
        value3,
        value4,
        value5,
        value6,
        rankNames,
    )
  }
}
