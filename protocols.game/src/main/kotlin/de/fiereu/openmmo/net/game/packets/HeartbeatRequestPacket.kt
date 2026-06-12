package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class HeartbeatRequestPacket(val placeholder: Unit = Unit)

object HeartbeatRequestPacketCodec : PacketCodec<HeartbeatRequestPacket>() {
    override fun CodecScope<HeartbeatRequestPacket>.body(): HeartbeatRequestPacket {
        return HeartbeatRequestPacket()
    }
}
