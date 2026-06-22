package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class BattleChatMessagePacket(val channelOrSlot: Byte, val message: String)

object BattleChatMessagePacketCodec : PacketCodec<BattleChatMessagePacket>() {
    override fun CodecScope<BattleChatMessagePacket>.body(): BattleChatMessagePacket {
        val channelOrSlot = field(S8) { it.channelOrSlot }
        val message = field(Utf16LeNullTerminated) { it.message }
        return BattleChatMessagePacket(channelOrSlot, message)
    }
}
