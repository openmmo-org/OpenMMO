package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GuildProfileData(
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

data class GuildMembershipPacket(
    val inGuild: Boolean,
    val profile: GuildProfileData?,
)

private object GuildProfileDataCodec : PacketCodec<GuildProfileData>() {
  override fun CodecScope<GuildProfileData>.body(): GuildProfileData {
    val guildId = field(S64LE, GuildProfileData::guildId)
    val name = field(Utf16LeNullTerminated, GuildProfileData::name)
    val tag = field(Utf16LeNullTerminated, GuildProfileData::tag)
    val memberCount = field(S32LE, GuildProfileData::memberCount)
    val description = field(Utf16LeNullTerminated, GuildProfileData::description)
    val motd = field(S32LE, GuildProfileData::motd)
    val value1 = field(S16LE, GuildProfileData::value1)
    val value2 = field(S16LE, GuildProfileData::value2)
    val value3 = field(S16LE, GuildProfileData::value3)
    val value4 = field(S16LE, GuildProfileData::value4)
    val value5 = field(S16LE, GuildProfileData::value5)
    val value6 = field(S32LE, GuildProfileData::value6)
    val rankCount = field(S8) { it.rankNames.size.toByte() }
    val rankNames =
        List(rankCount.toInt()) { i -> field(Utf16LeNullTerminated) { it.rankNames[i] } }
    return GuildProfileData(
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

object GuildMembershipPacketCodec : PacketCodec<GuildMembershipPacket>() {
  override fun CodecScope<GuildMembershipPacket>.body(): GuildMembershipPacket {
    val inGuild = field(Bool, GuildMembershipPacket::inGuild)
    val profile = if (inGuild) field(GuildProfileDataCodec) { it.profile!! } else null
    return GuildMembershipPacket(inGuild, profile)
  }
}
