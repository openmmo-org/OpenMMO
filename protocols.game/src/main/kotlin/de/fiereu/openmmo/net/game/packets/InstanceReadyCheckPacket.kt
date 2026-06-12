package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class InstanceReadyCheckPacket(val isReady: Boolean)

object InstanceReadyCheckPacketCodec : PacketCodec<InstanceReadyCheckPacket>() {
    override fun CodecScope<InstanceReadyCheckPacket>.body(): InstanceReadyCheckPacket {
        val isReady = field(Bool) { it.isReady }
        return InstanceReadyCheckPacket(isReady)
    }
}
