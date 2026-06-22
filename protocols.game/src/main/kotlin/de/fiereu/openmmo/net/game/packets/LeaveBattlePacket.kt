package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class LeaveBattlePacket

object LeaveBattlePacketCodec : PacketCodec<LeaveBattlePacket>() {
  override fun CodecScope<LeaveBattlePacket>.body(): LeaveBattlePacket {
    return LeaveBattlePacket()
  }
}
