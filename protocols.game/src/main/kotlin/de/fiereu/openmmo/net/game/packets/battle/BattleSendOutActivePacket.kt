package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

data class BattleSendOutMember(
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

data class BattleSendOutActivePacket(
    val opponent: Boolean,
    val member: BattleSendOutMember?,
)

object BattleSendOutActivePacketCodec : PacketCodec<BattleSendOutActivePacket>() {
  override fun CodecScope<BattleSendOutActivePacket>.body(): BattleSendOutActivePacket {
    val present = field(U8) { if (it.member != null) 1 else 0 }
    if (present != 1) return BattleSendOutActivePacket(false, null)
    val opponent = field(Bool) { it.opponent }
    val entityIdA = field(S64LE) { it.member!!.entityIdA }
    val entityIdB = field(S64LE) { it.member!!.entityIdB }
    val entityIdC = field(S64LE) { it.member!!.entityIdC }
    val speciesByte = field(S8) { it.member!!.speciesByte }
    val nickname = if (!opponent) field(Utf16LeNullTerminated) { it.member!!.nickname } else ""
    val ownerName = if (opponent) field(Utf16LeNullTerminated) { it.member!!.ownerName } else ""
    val value = field(S32LE) { it.member!!.value }
    val nameA = field(Utf16LeNullTerminated) { it.member!!.nameA }
    val nameB = field(Utf16LeNullTerminated) { it.member!!.nameB }
    val levelByte = field(S8) { it.member!!.levelByte }
    val flag = field(Bool) { it.member!!.flag }
    return BattleSendOutActivePacket(
        opponent,
        BattleSendOutMember(
            entityIdA,
            entityIdB,
            entityIdC,
            speciesByte,
            nickname,
            ownerName,
            value,
            nameA,
            nameB,
            levelByte,
            flag))
  }
}
