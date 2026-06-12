package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S32LE

data class RosterCooldownPacket(
    val cooldownOffsetSeconds: Int,
)

object RosterCooldownPacketCodec : PacketCodec<RosterCooldownPacket>() {
    override fun CodecScope<RosterCooldownPacket>.body(): RosterCooldownPacket {
        val cooldownOffsetSeconds = field(S32LE) { it.cooldownOffsetSeconds }
        return RosterCooldownPacket(cooldownOffsetSeconds)
    }
}
