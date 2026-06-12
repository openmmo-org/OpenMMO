package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class TournamentRegistrationPacket(
    val register: Boolean,
)

object TournamentRegistrationPacketCodec : PacketCodec<TournamentRegistrationPacket>() {
    override fun CodecScope<TournamentRegistrationPacket>.body(): TournamentRegistrationPacket {
        val register = field(Bool) { it.register }
        return TournamentRegistrationPacket(register)
    }
}
