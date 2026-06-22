package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class GuildDisbandTogglePacket(
    val initiate: Boolean,
    val ownerEntityId: Long,
)

object GuildDisbandTogglePacketCodec : PacketCodec<GuildDisbandTogglePacket>() {
    override fun CodecScope<GuildDisbandTogglePacket>.body(): GuildDisbandTogglePacket {
        val initiate = field(Bool) { it.initiate }
        val ownerEntityId = field(S64LE) { it.ownerEntityId }
        return GuildDisbandTogglePacket(initiate, ownerEntityId)
    }
}
