package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class UnblockPlayerPacket(val username: String)

object UnblockPlayerPacketCodec : PacketCodec<UnblockPlayerPacket>() {
  override fun CodecScope<UnblockPlayerPacket>.body(): UnblockPlayerPacket {
    val username = field(Utf16LeNullTerminated) { it.username }
    return UnblockPlayerPacket(username)
  }
}
