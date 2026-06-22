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

data class GuildRosterAddPacket(
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

object GuildRosterAddPacketCodec : PacketCodec<GuildRosterAddPacket>() {
  override fun CodecScope<GuildRosterAddPacket>.body(): GuildRosterAddPacket {
    val rank = field(S8, GuildRosterAddPacket::rank)
    val entityId = field(S64LE, GuildRosterAddPacket::entityId)
    val value = field(S32LE, GuildRosterAddPacket::value)
    val appearance = field(GuildMemberAppearanceCodec, GuildRosterAddPacket::appearance)
    val leader = field(Bool, GuildRosterAddPacket::leader)
    return GuildRosterAddPacket(rank, entityId, value, appearance, leader)
  }
}
