package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class ChatLocalePreferencesPacket(
    val languageId: Byte,
    val chatChannelMask: Short,
    val languageFilterMask: Short,
)

object ChatLocalePreferencesPacketCodec : PacketCodec<ChatLocalePreferencesPacket>() {
    override fun CodecScope<ChatLocalePreferencesPacket>.body(): ChatLocalePreferencesPacket {
        val languageId = field(S8) { it.languageId }
        val chatChannelMask = field(S16LE) { it.chatChannelMask }
        val languageFilterMask = field(S16LE) { it.languageFilterMask }
        return ChatLocalePreferencesPacket(languageId, chatChannelMask, languageFilterMask)
    }
}
