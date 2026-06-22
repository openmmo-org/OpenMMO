package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class RenderScreenPacket(val renderScreen: Boolean)

object RenderScreenPacketCodec : PacketCodec<RenderScreenPacket>() {
    override fun CodecScope<RenderScreenPacket>.body(): RenderScreenPacket {
        val renderScreen = field(Bool) { it.renderScreen }
        return RenderScreenPacket(renderScreen)
    }
}
