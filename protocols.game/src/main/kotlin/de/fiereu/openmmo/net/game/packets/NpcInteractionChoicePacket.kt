package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class NpcInteractionChoicePacket(
    val targetId: Long,
)

object NpcInteractionChoicePacketCodec : PacketCodec<NpcInteractionChoicePacket>() {
    override fun CodecScope<NpcInteractionChoicePacket>.body(): NpcInteractionChoicePacket {
        val targetId = field(S64LE) { it.targetId }
        return NpcInteractionChoicePacket(targetId)
    }
}
