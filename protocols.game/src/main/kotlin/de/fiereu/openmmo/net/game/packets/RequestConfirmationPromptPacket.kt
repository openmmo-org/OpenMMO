package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class RequestConfirmationPromptPacket(
    val visible: Boolean,
    val entityId: Long?,
    val requestTimeoutSeconds: Int?,
    val responseTimeoutSeconds: Int?,
)

object RequestConfirmationPromptPacketCodec : PacketCodec<RequestConfirmationPromptPacket>() {
    override fun CodecScope<RequestConfirmationPromptPacket>.body(): RequestConfirmationPromptPacket {
        val visible = field(U8) { if (it.visible) 1 else 0 } == 1
        val entityId = if (visible) field(S64LE) { it.entityId!! } else null
        val requestTimeoutSeconds = if (visible) field(U16LE) { it.requestTimeoutSeconds!! } else null
        val responseTimeoutSeconds = if (visible) field(U16LE) { it.responseTimeoutSeconds!! } else null
        return RequestConfirmationPromptPacket(
            visible, entityId, requestTimeoutSeconds, responseTimeoutSeconds
        )
    }
}
