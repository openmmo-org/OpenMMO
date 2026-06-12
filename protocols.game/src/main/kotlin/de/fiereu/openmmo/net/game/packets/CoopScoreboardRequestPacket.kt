package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class CoopScoreboardRequestPacket

object CoopScoreboardRequestPacketCodec : PacketCodec<CoopScoreboardRequestPacket>() {
    override fun CodecScope<CoopScoreboardRequestPacket>.body(): CoopScoreboardRequestPacket {
        return CoopScoreboardRequestPacket()
    }
}
