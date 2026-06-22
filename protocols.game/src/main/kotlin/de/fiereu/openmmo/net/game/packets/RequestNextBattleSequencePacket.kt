package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class RequestNextBattleSequencePacket

object RequestNextBattleSequencePacketCodec : PacketCodec<RequestNextBattleSequencePacket>() {
  override fun CodecScope<RequestNextBattleSequencePacket>.body(): RequestNextBattleSequencePacket {
    return RequestNextBattleSequencePacket()
  }
}
