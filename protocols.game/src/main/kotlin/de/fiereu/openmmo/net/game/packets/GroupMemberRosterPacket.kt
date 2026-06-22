package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GroupMemberAppearance(
    val name: String,
    val gender: Byte,
    val formId: Int,
    val kind: Byte,
    val palettePack: Byte,
    val slots: List<Short>,
)

private val GroupMemberAppearanceCodec: Codec<GroupMemberAppearance> =
    object : PacketCodec<GroupMemberAppearance>() {
      override fun CodecScope<GroupMemberAppearance>.body(): GroupMemberAppearance {
        val name = field(Utf16LeNullTerminated) { it.name }
        val gender = field(S8) { it.gender }
        val formId = field(S32LE) { it.formId }
        val kind = field(S8) { it.kind }
        val palettePack = field(S8) { it.palettePack }
        val slots = field(S16LE.repeat(4)) { it.slots }
        return GroupMemberAppearance(name, gender, formId, kind, palettePack, slots)
      }
    }

data class GroupMemberTrackedEntity(
    val entityId: Long,
    val appearance: GroupMemberAppearance,
)

private val GroupMemberTrackedEntityCodec: Codec<GroupMemberTrackedEntity> =
    object : PacketCodec<GroupMemberTrackedEntity>() {
      override fun CodecScope<GroupMemberTrackedEntity>.body(): GroupMemberTrackedEntity {
        val entityId = field(S64LE) { it.entityId }
        val appearance = field(GroupMemberAppearanceCodec) { it.appearance }
        return GroupMemberTrackedEntity(entityId, appearance)
      }
    }

data class GroupRosterMember(
    val entityId: Long,
    val name: String,
    val guildName: String,
    val trackedEntities: List<GroupMemberTrackedEntity>,
)

private val GroupRosterMemberCodec: Codec<GroupRosterMember> =
    object : PacketCodec<GroupRosterMember>() {
      override fun CodecScope<GroupRosterMember>.body(): GroupRosterMember {
        val entityId = field(S64LE) { it.entityId }
        val name = field(Utf16LeNullTerminated) { it.name }
        val guildName = field(Utf16LeNullTerminated) { it.guildName }
        val trackedEntities =
            field(GroupMemberTrackedEntityCodec.listPrefixed(U8)) { it.trackedEntities }
        return GroupRosterMember(entityId, name, guildName, trackedEntities)
      }
    }

data class GroupMemberRosterPacket(
    val reset: Boolean,
    val updateState: Boolean,
    val state: Byte,
    val members: List<GroupRosterMember>,
)

object GroupMemberRosterPacketCodec : PacketCodec<GroupMemberRosterPacket>() {
  override fun CodecScope<GroupMemberRosterPacket>.body(): GroupMemberRosterPacket {
    val reset = field(U8) { if (it.reset) 1 else 0 } == 1
    val updateState = field(U8) { if (it.updateState) 1 else 0 } == 1
    val state = field(S8) { it.state }
    val members = field(GroupRosterMemberCodec.listPrefixed(U8)) { it.members }
    return GroupMemberRosterPacket(reset, updateState, state, members)
  }
}
