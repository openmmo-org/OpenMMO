package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

/** Opcode 0x2A (s2c). Sets or clears one region-specific GBA story flag. */
data class StoryFlagUpdatePacket(
    val regionId: Byte,
    val flagId: Int,
    val enabled: Boolean,
)

object StoryFlagUpdatePacketCodec : PacketCodec<StoryFlagUpdatePacket>() {
  override fun CodecScope<StoryFlagUpdatePacket>.body(): StoryFlagUpdatePacket {
    val regionId = field(S8) { it.regionId }
    val flagId = field(S16LE) { it.flagId.toShort() }.toInt() and 0xffff
    val enabled = field(S16LE) { if (it.enabled) 1 else 0 }.toInt() != 0
    return StoryFlagUpdatePacket(regionId, flagId, enabled)
  }
}
