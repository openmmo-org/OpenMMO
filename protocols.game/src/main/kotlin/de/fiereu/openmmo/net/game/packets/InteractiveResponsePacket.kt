package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.U8

data class InteractiveResponsePacket(val id: Int, val unk: Int)

object InteractiveResponsePacketCodec : PacketCodec<InteractiveResponsePacket>() {
    override fun CodecScope<InteractiveResponsePacket>.body(): InteractiveResponsePacket {
        val id = field(U8) { it.id }
        val unk = field(U8) { it.unk }
        return InteractiveResponsePacket(id, unk)
    }
}
