package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class RemoveFriendPacket(val username: String)

object RemoveFriendPacketCodec : PacketCodec<RemoveFriendPacket>() {
  override fun CodecScope<RemoveFriendPacket>.body(): RemoveFriendPacket {
    val username = field(Utf16LeNullTerminated) { it.username }
    return RemoveFriendPacket(username)
  }
}
