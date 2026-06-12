package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class ViewScalePacket(
    val viewScale: Byte,
)

object ViewScalePacketCodec : PacketCodec<ViewScalePacket>() {
    override fun CodecScope<ViewScalePacket>.body(): ViewScalePacket {
        val viewScale = field(S8) { it.viewScale }
        return ViewScalePacket(viewScale)
    }
}
