package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class EntityGroupMemberRemovePacket(
    val removedId: Long,
    val leaderId: Long,
)

object EntityGroupMemberRemovePacketCodec : PacketCodec<EntityGroupMemberRemovePacket>() {
    override fun CodecScope<EntityGroupMemberRemovePacket>.body(): EntityGroupMemberRemovePacket {
        val removedId = field(S64LE) { it.removedId }
        val leaderId = field(S64LE) { it.leaderId }
        return EntityGroupMemberRemovePacket(removedId, leaderId)
    }
}
