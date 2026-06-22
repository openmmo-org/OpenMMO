package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE

data class DialogResponsePacket(
    val accepted: Boolean,
    val dialogActionId: Short,
)

object DialogResponsePacketCodec : PacketCodec<DialogResponsePacket>() {
    override fun CodecScope<DialogResponsePacket>.body(): DialogResponsePacket {
        val accepted = field(Bool) { it.accepted }
        val dialogActionId = field(S16LE) { it.dialogActionId }
        return DialogResponsePacket(accepted, dialogActionId)
    }
}
