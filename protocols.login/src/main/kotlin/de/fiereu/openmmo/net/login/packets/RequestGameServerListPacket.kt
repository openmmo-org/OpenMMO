package de.fiereu.openmmo.net.login.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class RequestGameServerListPacket

object RequestGameServerListPacketCodec : PacketCodec<RequestGameServerListPacket>() {
  override fun CodecScope<RequestGameServerListPacket>.body() = RequestGameServerListPacket()
}
