package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class RequestCharactersPacket

object RequestCharactersPacketCodec : PacketCodec<RequestCharactersPacket>() {
  override fun CodecScope<RequestCharactersPacket>.body() = RequestCharactersPacket()
}
