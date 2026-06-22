package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class ByteArgumentPacket(val value: Byte)

object ByteArgumentPacketCodec : PacketCodec<ByteArgumentPacket>() {
    override fun CodecScope<ByteArgumentPacket>.body(): ByteArgumentPacket {
        val value = field(S8) { it.value }
        return ByteArgumentPacket(value)
    }
}
