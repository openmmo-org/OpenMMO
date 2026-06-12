package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class FriendProfileRequestPacket(val targetEntityId: Long)

object FriendProfileRequestPacketCodec : PacketCodec<FriendProfileRequestPacket>() {
    override fun CodecScope<FriendProfileRequestPacket>.body(): FriendProfileRequestPacket {
        val targetEntityId = field(S64LE) { it.targetEntityId }
        return FriendProfileRequestPacket(targetEntityId)
    }
}
