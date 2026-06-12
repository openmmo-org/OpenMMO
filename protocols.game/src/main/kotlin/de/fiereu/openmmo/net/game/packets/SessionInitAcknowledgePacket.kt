package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class SessionInitAcknowledgePacket(val placeholder: Unit = Unit)

object SessionInitAcknowledgePacketCodec : PacketCodec<SessionInitAcknowledgePacket>() {
    override fun CodecScope<SessionInitAcknowledgePacket>.body(): SessionInitAcknowledgePacket {
        return SessionInitAcknowledgePacket()
    }
}
