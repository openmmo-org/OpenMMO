package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class PartyInfoRequestPacket

object PartyInfoRequestPacketCodec : PacketCodec<PartyInfoRequestPacket>() {
    override fun CodecScope<PartyInfoRequestPacket>.body(): PartyInfoRequestPacket {
        return PartyInfoRequestPacket()
    }
}
