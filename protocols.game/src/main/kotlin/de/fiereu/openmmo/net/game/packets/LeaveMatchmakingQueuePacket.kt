package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class LeaveMatchmakingQueuePacket

object LeaveMatchmakingQueuePacketCodec : PacketCodec<LeaveMatchmakingQueuePacket>() {
  override fun CodecScope<LeaveMatchmakingQueuePacket>.body(): LeaveMatchmakingQueuePacket {
    return LeaveMatchmakingQueuePacket()
  }
}
