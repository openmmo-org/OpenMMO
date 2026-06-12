package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class GuildDisbandToggleRequestPacket(
    val initiate: Boolean,
    val ownerEntityId: Long,
)

object GuildDisbandToggleRequestPacketCodec : PacketCodec<GuildDisbandToggleRequestPacket>() {
    override fun CodecScope<GuildDisbandToggleRequestPacket>.body(): GuildDisbandToggleRequestPacket {
        val initiate = field(Bool) { it.initiate }
        val ownerEntityId = field(S64LE) { it.ownerEntityId }
        return GuildDisbandToggleRequestPacket(initiate, ownerEntityId)
    }
}
