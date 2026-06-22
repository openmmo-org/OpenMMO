package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GuildMemberAppearance(
    val name: String,
    val gender: Byte,
    val formId: Int,
    val kind: Byte,
    val palettePack: Byte,
    val slots: List<Short>,
)

data class GuildMemberAddPacket(
    val rank: Byte,
    val entityId: Long,
    val value: Int,
    val appearance: GuildMemberAppearance,
    val leader: Boolean,
)

internal object GuildMemberAppearanceCodec : PacketCodec<GuildMemberAppearance>() {
    override fun CodecScope<GuildMemberAppearance>.body(): GuildMemberAppearance {
        val name = field(Utf16LeNullTerminated, GuildMemberAppearance::name)
        val gender = field(S8, GuildMemberAppearance::gender)
        val formId = field(S32LE, GuildMemberAppearance::formId)
        val kind = field(S8, GuildMemberAppearance::kind)
        val palettePack = field(S8, GuildMemberAppearance::palettePack)
        val slots = field(S16LE.repeat(4), GuildMemberAppearance::slots)
        return GuildMemberAppearance(name, gender, formId, kind, palettePack, slots)
    }
}

object GuildMemberAddPacketCodec : PacketCodec<GuildMemberAddPacket>() {
    override fun CodecScope<GuildMemberAddPacket>.body(): GuildMemberAddPacket {
        val rank = field(S8, GuildMemberAddPacket::rank)
        val entityId = field(S64LE, GuildMemberAddPacket::entityId)
        val value = field(S32LE, GuildMemberAddPacket::value)
        val appearance = field(GuildMemberAppearanceCodec, GuildMemberAddPacket::appearance)
        val leader = field(Bool, GuildMemberAddPacket::leader)
        return GuildMemberAddPacket(rank, entityId, value, appearance, leader)
    }
}
