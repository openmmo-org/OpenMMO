package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class MonsterStatusConditionPacket(val targetId: Long, val statusCondition: Byte)

object MonsterStatusConditionPacketCodec : PacketCodec<MonsterStatusConditionPacket>() {
    override fun CodecScope<MonsterStatusConditionPacket>.body(): MonsterStatusConditionPacket {
        val targetId = field(S64LE) { it.targetId }
        val statusCondition = field(S8) { it.statusCondition }
        return MonsterStatusConditionPacket(targetId, statusCondition)
    }
}
