package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class GuildMemberPresencePacket(
    val memberId: Long,
    val online: Boolean,
)

object GuildMemberPresencePacketCodec : PacketCodec<GuildMemberPresencePacket>() {
    override fun CodecScope<GuildMemberPresencePacket>.body(): GuildMemberPresencePacket {
        val memberId = field(S64LE, GuildMemberPresencePacket::memberId)
        val online = field(Bool, GuildMemberPresencePacket::online)
        return GuildMemberPresencePacket(memberId, online)
    }
}
