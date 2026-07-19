package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class BattleSequencePacket

object BattleSequencePacketCodec : PacketCodec<BattleSequencePacket>() {
  override fun CodecScope<BattleSequencePacket>.body(): BattleSequencePacket {
    return BattleSequencePacket()
  }
}
