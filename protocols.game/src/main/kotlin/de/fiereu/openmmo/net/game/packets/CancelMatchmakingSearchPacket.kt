package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class CancelMatchmakingSearchPacket

object CancelMatchmakingSearchPacketCodec : PacketCodec<CancelMatchmakingSearchPacket>() {
  override fun CodecScope<CancelMatchmakingSearchPacket>.body(): CancelMatchmakingSearchPacket {
    return CancelMatchmakingSearchPacket()
  }
}
