package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class JoinMatchmakingQueuePacket

object JoinMatchmakingQueuePacketCodec : PacketCodec<JoinMatchmakingQueuePacket>() {
  override fun CodecScope<JoinMatchmakingQueuePacket>.body(): JoinMatchmakingQueuePacket {
    return JoinMatchmakingQueuePacket()
  }
}
