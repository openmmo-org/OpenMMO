package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleStatCountersPacket(
    val entityId: Long,
    val baseCounter: Int,
    val counter1: Int?,
    val counter2: Int?,
    val counter3: Int?,
    val counter4: Int?,
    val counter5: Int?,
    val counter6: Int?,
)

object BattleStatCountersPacketCodec : PacketCodec<BattleStatCountersPacket>() {
    override fun CodecScope<BattleStatCountersPacket>.body(): BattleStatCountersPacket {
        val entityId = field(S64LE, BattleStatCountersPacket::entityId)
        val baseCounter = field(S32LE, BattleStatCountersPacket::baseCounter)
        val flags =
            field(U8) {
                (if (it.counter1 != null) 1 else 0) or
                        (if (it.counter2 != null) 2 else 0) or
                        (if (it.counter3 != null) 4 else 0) or
                        (if (it.counter4 != null) 8 else 0) or
                        (if (it.counter5 != null) 16 else 0) or
                        (if (it.counter6 != null) 32 else 0)
            }
        val counter1 = if (flags and 1 != 0) field(S32LE) { it.counter1!! } else null
        val counter2 = if (flags and 2 != 0) field(S32LE) { it.counter2!! } else null
        val counter3 = if (flags and 4 != 0) field(S32LE) { it.counter3!! } else null
        val counter4 = if (flags and 8 != 0) field(S32LE) { it.counter4!! } else null
        val counter5 = if (flags and 16 != 0) field(S32LE) { it.counter5!! } else null
        val counter6 = if (flags and 32 != 0) field(S32LE) { it.counter6!! } else null
        return BattleStatCountersPacket(
            entityId,
            baseCounter,
            counter1,
            counter2,
            counter3,
            counter4,
            counter5,
            counter6,
        )
    }
}
