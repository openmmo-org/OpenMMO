package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class MatchmakingLanguagePrefsPacket(val languageIds: List<Byte>)

object MatchmakingLanguagePrefsPacketCodec : PacketCodec<MatchmakingLanguagePrefsPacket>() {
    override fun CodecScope<MatchmakingLanguagePrefsPacket>.body(): MatchmakingLanguagePrefsPacket {
        val languageIds = field(S8.listPrefixed(U8)) { it.languageIds }
        return MatchmakingLanguagePrefsPacket(languageIds)
    }
}
