package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class BattleReadyPacket(val ready: Boolean)

object BattleReadyPacketCodec : PacketCodec<BattleReadyPacket>() {
  override fun CodecScope<BattleReadyPacket>.body(): BattleReadyPacket {
    val ready = field(Bool) { it.ready }
    return BattleReadyPacket(ready)
  }
}
