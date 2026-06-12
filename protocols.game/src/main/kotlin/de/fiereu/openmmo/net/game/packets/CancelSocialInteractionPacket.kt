package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class CancelSocialInteractionPacket

object CancelSocialInteractionPacketCodec : PacketCodec<CancelSocialInteractionPacket>() {
    override fun CodecScope<CancelSocialInteractionPacket>.body(): CancelSocialInteractionPacket {
        return CancelSocialInteractionPacket()
    }
}
