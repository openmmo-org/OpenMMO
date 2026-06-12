package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class MapTransitionPacket {
    override fun equals(other: Any?): Boolean = other is MapTransitionPacket

    override fun hashCode(): Int = MapTransitionPacket::class.hashCode()
}

object MapTransitionPacketCodec : PacketCodec<MapTransitionPacket>() {
    override fun CodecScope<MapTransitionPacket>.body() = MapTransitionPacket()
}
