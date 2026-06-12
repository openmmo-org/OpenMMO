package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class MapReadySignalPacket(val placeholder: Unit = Unit)

object MapReadySignalPacketCodec : PacketCodec<MapReadySignalPacket>() {
    override fun CodecScope<MapReadySignalPacket>.body(): MapReadySignalPacket {
        return MapReadySignalPacket()
    }
}
