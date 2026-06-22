package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class ModerationActionConfirmPacket(
    val targetEntityId: Long,
)

object ModerationActionConfirmPacketCodec : PacketCodec<ModerationActionConfirmPacket>() {
    override fun CodecScope<ModerationActionConfirmPacket>.body(): ModerationActionConfirmPacket {
        val targetEntityId = field(S64LE) { it.targetEntityId }
        return ModerationActionConfirmPacket(targetEntityId)
    }
}
