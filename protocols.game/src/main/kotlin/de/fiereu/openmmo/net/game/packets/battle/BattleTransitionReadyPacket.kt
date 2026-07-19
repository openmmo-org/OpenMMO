package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class BattleTransitionReadyPacket

object BattleTransitionReadyPacketCodec : PacketCodec<BattleTransitionReadyPacket>() {
  override fun CodecScope<BattleTransitionReadyPacket>.body(): BattleTransitionReadyPacket {
    return BattleTransitionReadyPacket()
  }
}
