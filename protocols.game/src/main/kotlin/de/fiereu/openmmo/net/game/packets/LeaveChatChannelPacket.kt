package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class LeaveChatChannelPacket

object LeaveChatChannelPacketCodec : PacketCodec<LeaveChatChannelPacket>() {
    override fun CodecScope<LeaveChatChannelPacket>.body(): LeaveChatChannelPacket {
        return LeaveChatChannelPacket()
    }
}
