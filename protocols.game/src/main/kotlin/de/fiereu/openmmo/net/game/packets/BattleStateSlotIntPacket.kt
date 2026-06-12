package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S8

data class BattleStateSlotIntPacket(
    val index: Byte,
    val value: Int,
)

object BattleStateSlotIntPacketCodec : PacketCodec<BattleStateSlotIntPacket>() {
    override fun CodecScope<BattleStateSlotIntPacket>.body(): BattleStateSlotIntPacket {
        val index = field(S8) { it.index }
        val value = field(S32LE) { it.value }
        return BattleStateSlotIntPacket(index, value)
    }
}
