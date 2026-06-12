package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class BattleSlotFlagEventPacket(
    val slot: Byte,
    val flag: Boolean,
    val immediate: Boolean,
)

object BattleSlotFlagEventPacketCodec : PacketCodec<BattleSlotFlagEventPacket>() {
    override fun CodecScope<BattleSlotFlagEventPacket>.body(): BattleSlotFlagEventPacket {
        val slot = field(S8) { it.slot }
        val flag = field(Bool) { it.flag }
        val immediate = field(Bool) { it.immediate }
        return BattleSlotFlagEventPacket(slot, flag, immediate)
    }
}
