package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class TourneyRegistrationPacket(
    val tournamentId: Byte,
    val isActive: Boolean,
    val tabIndex: Short,
)

object TourneyRegistrationPacketCodec : PacketCodec<TourneyRegistrationPacket>() {
    override fun CodecScope<TourneyRegistrationPacket>.body(): TourneyRegistrationPacket {
        val tournamentId = field(S8) { it.tournamentId }
        val isActive = field(Bool) { it.isActive }
        val tabIndex = field(S16LE) { it.tabIndex }
        return TourneyRegistrationPacket(tournamentId, isActive, tabIndex)
    }
}
