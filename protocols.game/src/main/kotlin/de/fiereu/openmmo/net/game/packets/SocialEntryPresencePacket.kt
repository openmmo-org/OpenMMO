package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SocialEntryPresencePacket(
    val playerId: Long,
    val status: Short,
    val online: Boolean,
)

object SocialEntryPresencePacketCodec : PacketCodec<SocialEntryPresencePacket>() {
    override fun CodecScope<SocialEntryPresencePacket>.body(): SocialEntryPresencePacket {
        val playerId = field(S64LE) { it.playerId }
        val status = field(S16LE) { it.status }
        val online = field(Bool) { it.online }
        return SocialEntryPresencePacket(playerId, status, online)
    }
}
