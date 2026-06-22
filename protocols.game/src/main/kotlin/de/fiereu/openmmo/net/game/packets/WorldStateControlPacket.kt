package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S8

data class WorldStateControlPacket(
    val actionType: Byte,
    val value: Int,
)

object WorldStateControlPacketCodec : PacketCodec<WorldStateControlPacket>() {
    override fun CodecScope<WorldStateControlPacket>.body(): WorldStateControlPacket {
        val actionType = field(S8) { it.actionType }
        val value = field(S32LE) { it.value }
        return WorldStateControlPacket(actionType, value)
    }
}
