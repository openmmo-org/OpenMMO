package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class TeamPreviewRevealPickPacket(
    val entityId: Long,
)

object TeamPreviewRevealPickPacketCodec : PacketCodec<TeamPreviewRevealPickPacket>() {
    override fun CodecScope<TeamPreviewRevealPickPacket>.body(): TeamPreviewRevealPickPacket {
        val entityId = field(S64LE) { it.entityId }
        return TeamPreviewRevealPickPacket(entityId)
    }
}
