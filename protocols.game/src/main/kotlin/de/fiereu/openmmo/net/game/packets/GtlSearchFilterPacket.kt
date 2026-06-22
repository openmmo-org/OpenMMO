package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class GtlSearchFilterPacket(
    val filterState: Byte,
)

object GtlSearchFilterPacketCodec : PacketCodec<GtlSearchFilterPacket>() {
  override fun CodecScope<GtlSearchFilterPacket>.body(): GtlSearchFilterPacket {
    val filterState = field(S8) { it.filterState }
    return GtlSearchFilterPacket(filterState)
  }
}
