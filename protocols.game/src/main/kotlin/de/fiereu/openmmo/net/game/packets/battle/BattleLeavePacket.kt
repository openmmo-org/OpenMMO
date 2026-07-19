package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class BattleLeavePacket

object BattleLeavePacketCodec : PacketCodec<BattleLeavePacket>() {
  override fun CodecScope<BattleLeavePacket>.body(): BattleLeavePacket {
    return BattleLeavePacket()
  }
}
