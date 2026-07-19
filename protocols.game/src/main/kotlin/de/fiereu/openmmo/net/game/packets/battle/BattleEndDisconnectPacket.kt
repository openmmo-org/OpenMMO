package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class BattleEndDisconnectPacket(
    val reason: Byte,
)

object BattleEndDisconnectPacketCodec : PacketCodec<BattleEndDisconnectPacket>() {
  override fun CodecScope<BattleEndDisconnectPacket>.body(): BattleEndDisconnectPacket {
    val reason = field(S8) { it.reason }
    return BattleEndDisconnectPacket(reason)
  }
}
