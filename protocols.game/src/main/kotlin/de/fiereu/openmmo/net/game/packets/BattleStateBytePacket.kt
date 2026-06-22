package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class BattleStateBytePacket(
    val state: Byte,
)

object BattleStateBytePacketCodec : PacketCodec<BattleStateBytePacket>() {
  override fun CodecScope<BattleStateBytePacket>.body(): BattleStateBytePacket {
    val state = field(S8) { it.state }
    return BattleStateBytePacket(state)
  }
}
