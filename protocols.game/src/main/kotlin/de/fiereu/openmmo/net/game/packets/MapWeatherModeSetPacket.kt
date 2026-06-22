package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.U8

data class MapWeatherModeSetPacket(
    val mode: Byte,
    val enabled: Boolean,
)

object MapWeatherModeSetPacketCodec : PacketCodec<MapWeatherModeSetPacket>() {
  override fun CodecScope<MapWeatherModeSetPacket>.body(): MapWeatherModeSetPacket {
    val mode = field(S8) { it.mode }
    val enabled = field(U8) { if (it.enabled) 1 else 0 } == 1
    return MapWeatherModeSetPacket(mode, enabled)
  }
}
