package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class MapLoadedAckPacket

object MapLoadedAckPacketCodec : PacketCodec<MapLoadedAckPacket>() {
    override fun CodecScope<MapLoadedAckPacket>.body(): MapLoadedAckPacket {
        return MapLoadedAckPacket()
    }
}
