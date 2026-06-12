package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class DuelInviteOutcomePacket(
    val packed: Byte,
)

object DuelInviteOutcomePacketCodec : PacketCodec<DuelInviteOutcomePacket>() {
    override fun CodecScope<DuelInviteOutcomePacket>.body(): DuelInviteOutcomePacket {
        val packed = field(S8) { it.packed }
        return DuelInviteOutcomePacket(packed)
    }
}
