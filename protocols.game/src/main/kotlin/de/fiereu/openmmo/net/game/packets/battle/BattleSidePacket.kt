package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class BattleSidePacket(
    val side: Byte,
)

object BattleSidePacketCodec : PacketCodec<BattleSidePacket>() {
  override fun CodecScope<BattleSidePacket>.body(): BattleSidePacket {
    val side = field(S8) { it.side }
    return BattleSidePacket(side)
  }
}
