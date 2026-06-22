package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class FriendRosterDeltaPacket(
    val action: Byte,
)

object FriendRosterDeltaPacketCodec : PacketCodec<FriendRosterDeltaPacket>() {
    override fun CodecScope<FriendRosterDeltaPacket>.body(): FriendRosterDeltaPacket {
        val action = field(S8) { it.action }
        return FriendRosterDeltaPacket(action)
    }
}
