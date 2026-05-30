package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class RequestPlayerPacket

object RequestPlayerPacketCodec : PacketCodec<RequestPlayerPacket>() {
  override fun CodecScope<RequestPlayerPacket>.body() = RequestPlayerPacket()
}
