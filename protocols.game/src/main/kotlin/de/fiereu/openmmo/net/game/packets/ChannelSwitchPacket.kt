package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class ChannelSwitchPacket(val channelId: Byte, val silent: Boolean)

object ChannelSwitchPacketCodec : PacketCodec<ChannelSwitchPacket>() {
    override fun CodecScope<ChannelSwitchPacket>.body(): ChannelSwitchPacket {
        val channelId = field(S8) { it.channelId }
        val silent = field(Bool) { it.silent }
        return ChannelSwitchPacket(channelId, silent)
    }
}
