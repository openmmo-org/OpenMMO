package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class SessionExpiryInitPacket(
    val tileSessionByte: Byte,
    val expirySeconds: Short,
)

object SessionExpiryInitPacketCodec : PacketCodec<SessionExpiryInitPacket>() {
  override fun CodecScope<SessionExpiryInitPacket>.body(): SessionExpiryInitPacket {
    val tileSessionByte = field(S8) { it.tileSessionByte }
    val expirySeconds = field(S16LE) { it.expirySeconds }
    return SessionExpiryInitPacket(tileSessionByte, expirySeconds)
  }
}
