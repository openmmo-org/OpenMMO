package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class EntityGroupMemberAddPacket(
    val member: EntityGroupMember,
)

private val AddAppearanceCodec: Codec<EntityAppearanceInfo> =
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

private val AddFrameCodec: Codec<GroupListFrame> =
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

private val AddFrameSetCodec: Codec<GroupListFrameSet> =
    object : PacketCodec<GroupListFrameSet>() {
        override fun CodecScope<GroupListFrameSet>.body(): GroupListFrameSet {
            val count = field(U8) { it.frames.size }
            val listType = if (count >= 1) field(S8) { it.listType ?: 0 } else null
            val frames = (0 until count).map { i -> field(AddFrameCodec) { it.frames[i] } }
            return GroupListFrameSet(listType, frames)
        }
    }

private val AddMemberCodec: Codec<EntityGroupMember> =
    object : PacketCodec<EntityGroupMember>() {
        override fun CodecScope<EntityGroupMember>.body(): EntityGroupMember {
            val entityId = field(S64LE) { it.entityId }
            val appearance = field(AddAppearanceCodec) { it.appearance }
            val frames = field(AddFrameSetCodec) { it.frames }
            return EntityGroupMember(entityId, appearance, frames)
        }
    }

object EntityGroupMemberAddPacketCodec : PacketCodec<EntityGroupMemberAddPacket>() {
    override fun CodecScope<EntityGroupMemberAddPacket>.body(): EntityGroupMemberAddPacket {
        val member = field(AddMemberCodec) { it.member }
        return EntityGroupMemberAddPacket(member)
    }
}
