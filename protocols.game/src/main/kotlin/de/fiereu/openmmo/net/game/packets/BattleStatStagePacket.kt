package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleStatStagePacket(
    val entityId: Long,
    val stat: Byte,
    val value: Short,
    val stage: Byte,
    val flags: Byte,
)

object BattleStatStagePacketCodec : PacketCodec<BattleStatStagePacket>() {
    override fun CodecScope<BattleStatStagePacket>.body(): BattleStatStagePacket {
        val entityId = field(S64LE) { it.entityId }
        val stat = field(S8) { it.stat }
        val value = field(S16LE) { it.value }
        val stage = field(S8) { it.stage }
        val flags = field(S8) { it.flags }
        return BattleStatStagePacket(entityId, stat, value, stage, flags)
    }
}
