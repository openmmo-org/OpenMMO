package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class EntityAppearanceInfo(
    val name: String,
    val gender: Byte,
    val formId: Int,
    val kind: Byte,
    val palettePack: Byte,
    val slots: List<Short>,
)

data class GroupListFrame(
    val typeId: Short,
    val packed: Short,
    val byteA: Byte,
    val byteB: Byte,
    val packed2: Short,
)

data class GroupListFrameSet(
    val listType: Byte?,
    val frames: List<GroupListFrame>,
)

data class EntityGroupMember(
    val entityId: Long,
    val appearance: EntityAppearanceInfo,
    val frames: GroupListFrameSet,
)

data class EntityGroupSnapshotPacket(
    val present: Boolean,
    val leaderId: Long?,
    val members: List<EntityGroupMember>?,
)

private val EntityAppearanceInfoCodec: Codec<EntityAppearanceInfo> =
    object : PacketCodec<EntityAppearanceInfo>() {
        override fun CodecScope<EntityAppearanceInfo>.body(): EntityAppearanceInfo {
            val name = field(Utf16LeNullTerminated) { it.name }
            val gender = field(S8) { it.gender }
            val formId = field(S32LE) { it.formId }
            val kind = field(S8) { it.kind }
            val palettePack = field(S8) { it.palettePack }
            val slots = field(S16LE.repeat(4)) { it.slots }
            return EntityAppearanceInfo(name, gender, formId, kind, palettePack, slots)
        }
    }

private val GroupListFrameCodec: Codec<GroupListFrame> =
    object : PacketCodec<GroupListFrame>() {
        override fun CodecScope<GroupListFrame>.body(): GroupListFrame {
            val typeId = field(S16LE) { it.typeId }
            val packed = field(S16LE) { it.packed }
            val byteA = field(S8) { it.byteA }
            val byteB = field(S8) { it.byteB }
            val packed2 = field(S16LE) { it.packed2 }
            return GroupListFrame(typeId, packed, byteA, byteB, packed2)
        }
    }

private val GroupListFrameSetCodec: Codec<GroupListFrameSet> =
    object : PacketCodec<GroupListFrameSet>() {
        override fun CodecScope<GroupListFrameSet>.body(): GroupListFrameSet {
            val count = field(U8) { it.frames.size }
            val listType = if (count >= 1) field(S8) { it.listType ?: 0 } else null
            val frames = (0 until count).map { i -> field(GroupListFrameCodec) { it.frames[i] } }
            return GroupListFrameSet(listType, frames)
        }
    }

private val EntityGroupMemberCodec: Codec<EntityGroupMember> =
    object : PacketCodec<EntityGroupMember>() {
        override fun CodecScope<EntityGroupMember>.body(): EntityGroupMember {
            val entityId = field(S64LE) { it.entityId }
            val appearance = field(EntityAppearanceInfoCodec) { it.appearance }
            val frames = field(GroupListFrameSetCodec) { it.frames }
            return EntityGroupMember(entityId, appearance, frames)
        }
    }

object EntityGroupSnapshotPacketCodec : PacketCodec<EntityGroupSnapshotPacket>() {
    override fun CodecScope<EntityGroupSnapshotPacket>.body(): EntityGroupSnapshotPacket {
        val present = field(U8) { if (it.present) 1 else 0 } == 1
        val leaderId = if (present) field(S64LE) { it.leaderId!! } else null
        val members =
            if (present) field(EntityGroupMemberCodec.listPrefixed(U8)) { it.members!! } else null
        return EntityGroupSnapshotPacket(present, leaderId, members)
    }
}
