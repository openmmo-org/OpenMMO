package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

data class PcTogglePacket(
    val shown: Boolean,
)

object PcTogglePacketCodec : PacketCodec<PcTogglePacket>() {
  override fun CodecScope<PcTogglePacket>.body(): PcTogglePacket {
    val shown = field(Bool) { it.shown }
    return PcTogglePacket(shown)
  }
}
