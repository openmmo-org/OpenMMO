package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class BlockPlayerPacket(val username: String, val reason: String)

object BlockPlayerPacketCodec : PacketCodec<BlockPlayerPacket>() {
  override fun CodecScope<BlockPlayerPacket>.body(): BlockPlayerPacket {
    val username = field(Utf16LeNullTerminated) { it.username }
    val reason = field(Utf16LeNullTerminated) { it.reason }
    return BlockPlayerPacket(username, reason)
  }
}
