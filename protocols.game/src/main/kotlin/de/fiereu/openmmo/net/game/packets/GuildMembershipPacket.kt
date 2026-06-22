package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GuildProfileData(
    val guildId: Long,
    val name: String,
    val tag: String,
    val foundedAt: Int,
    val message: String,
    val updatedAt: Int,
    val value1: Short,
    val value2: Short,
    val value3: Short,
    val value4: Short,
    val value5: Short,
    val unk1: Int,
    val rankCount: Int,
    val unk2: Int,
    val unk3: Int,
    val flag: Byte,
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
        val foundedAt = field(S32LE, GuildProfileData::foundedAt)
        val message = field(Utf16LeNullTerminated, GuildProfileData::message)
        val updatedAt = field(S32LE, GuildProfileData::updatedAt)
        val value1 = field(S16LE, GuildProfileData::value1)
        val value2 = field(S16LE, GuildProfileData::value2)
        val value3 = field(S16LE, GuildProfileData::value3)
        val value4 = field(S16LE, GuildProfileData::value4)
        val value5 = field(S16LE, GuildProfileData::value5)
        val unk1 = field(S32LE, GuildProfileData::unk1)
        val rankCount = field(S32LE, GuildProfileData::rankCount)
        val unk2 = field(S32LE, GuildProfileData::unk2)
        val unk3 = field(S32LE, GuildProfileData::unk3)
        val flag = field(S8, GuildProfileData::flag)
        return GuildProfileData(
            guildId,
            name,
            tag,
            foundedAt,
            message,
            updatedAt,
            value1,
            value2,
            value3,
            value4,
            value5,
            unk1,
            rankCount,
            unk2,
            unk3,
            flag,
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
