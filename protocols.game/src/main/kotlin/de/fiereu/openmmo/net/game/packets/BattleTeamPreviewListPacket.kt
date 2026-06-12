package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattlePreviewMember(
    val entityIdA: Long,
    val entityIdB: Long,
    val entityIdC: Long,
    val speciesByte: Byte,
    val nickname: String,
    val ownerName: String,
    val value: Int,
    val nameA: String,
    val nameB: String,
    val levelByte: Byte,
    val flag: Boolean,
)

private class BattlePreviewMemberCodec(
    private val opponent: Boolean,
    private val withDetails: Boolean,
) : PacketCodec<BattlePreviewMember>() {
    override fun CodecScope<BattlePreviewMember>.body(): BattlePreviewMember {
        val entityIdA = field(S64LE) { it.entityIdA }
        val entityIdB = field(S64LE) { it.entityIdB }
        val entityIdC = field(S64LE) { it.entityIdC }
        val speciesByte = field(S8) { it.speciesByte }
        val nickname = if (!opponent) field(Utf16LeNullTerminated) { it.nickname } else ""
        val ownerName = if (opponent) field(Utf16LeNullTerminated) { it.ownerName } else ""
        val value = field(S32LE) { it.value }
        val nameA = field(Utf16LeNullTerminated) { it.nameA }
        val nameB = if (withDetails) field(Utf16LeNullTerminated) { it.nameB } else ""
        val levelByte = field(S8) { it.levelByte }
        val flag = field(Bool) { it.flag }
        return BattlePreviewMember(
            entityIdA, entityIdB, entityIdC, speciesByte, nickname, ownerName, value, nameA, nameB,
            levelByte, flag
        )
    }
}

data class BattleTeamPreviewListPacket(
    val side: Short,
    val opponent: Boolean,
    val members: List<BattlePreviewMember>,
)

object BattleTeamPreviewListPacketCodec : PacketCodec<BattleTeamPreviewListPacket>() {
    override fun CodecScope<BattleTeamPreviewListPacket>.body(): BattleTeamPreviewListPacket {
        val side = field(S16LE) { it.side }
        val opponent = field(Bool) { it.opponent }
        val memberCodec = BattlePreviewMemberCodec(opponent, false)
        val count = field(S16LE) { it.members.size.toShort() }.toInt()
        val members = (0 until count).map { i -> field(memberCodec) { it.members[i] } }
        return BattleTeamPreviewListPacket(side, opponent, members)
    }
}
