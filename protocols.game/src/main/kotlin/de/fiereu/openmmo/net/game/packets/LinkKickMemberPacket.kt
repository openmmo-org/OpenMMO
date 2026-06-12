package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class LinkKickMemberPacket(
    val targetEntityId: Long,
)

object LinkKickMemberPacketCodec : PacketCodec<LinkKickMemberPacket>() {
    override fun CodecScope<LinkKickMemberPacket>.body(): LinkKickMemberPacket {
        val targetEntityId = field(S64LE) { it.targetEntityId }
        return LinkKickMemberPacket(targetEntityId)
    }
}
