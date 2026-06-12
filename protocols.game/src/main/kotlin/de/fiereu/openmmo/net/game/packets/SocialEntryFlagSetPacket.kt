package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SocialEntryFlagSetPacket(
    val playerId: Long,
    val flag: Byte,
    val value: Short,
)

object SocialEntryFlagSetPacketCodec : PacketCodec<SocialEntryFlagSetPacket>() {
    override fun CodecScope<SocialEntryFlagSetPacket>.body(): SocialEntryFlagSetPacket {
        val playerId = field(S64LE) { it.playerId }
        val flag = field(S8) { it.flag }
        val value = field(S16LE) { it.value }
        return SocialEntryFlagSetPacket(playerId, flag, value)
    }
}
