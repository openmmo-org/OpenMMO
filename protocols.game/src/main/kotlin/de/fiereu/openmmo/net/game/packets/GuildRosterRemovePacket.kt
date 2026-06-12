package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class GuildRosterRemovePacket(
    val memberId: Long,
)

object GuildRosterRemovePacketCodec : PacketCodec<GuildRosterRemovePacket>() {
    override fun CodecScope<GuildRosterRemovePacket>.body(): GuildRosterRemovePacket {
        field(S64LE) { 0L }
        val memberId = field(S64LE, GuildRosterRemovePacket::memberId)
        return GuildRosterRemovePacket(memberId)
    }
}
