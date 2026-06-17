package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class AddFriendPacket(val username: String)

object AddFriendPacketCodec : PacketCodec<AddFriendPacket>() {
    override fun CodecScope<AddFriendPacket>.body(): AddFriendPacket {
        val username = field(Utf16LeNullTerminated) { it.username }
        return AddFriendPacket(username)
    }
}
