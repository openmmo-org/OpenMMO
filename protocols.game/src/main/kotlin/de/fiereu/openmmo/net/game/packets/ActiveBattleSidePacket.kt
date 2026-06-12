package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class ActiveBattleSidePacket(
    val side: Byte,
)

object ActiveBattleSidePacketCodec : PacketCodec<ActiveBattleSidePacket>() {
    override fun CodecScope<ActiveBattleSidePacket>.body(): ActiveBattleSidePacket {
        val side = field(S8) { it.side }
        return ActiveBattleSidePacket(side)
    }
}
