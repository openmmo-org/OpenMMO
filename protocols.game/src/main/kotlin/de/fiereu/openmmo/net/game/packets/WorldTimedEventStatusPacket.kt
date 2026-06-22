package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class WorldTimedEventStatusPacket(
    val active: Boolean,
    val startTime: Long?,
    val endTime: Long?,
    val value: Short?,
    val shininessType: Byte?,
)

object WorldTimedEventStatusPacketCodec : PacketCodec<WorldTimedEventStatusPacket>() {
  override fun CodecScope<WorldTimedEventStatusPacket>.body(): WorldTimedEventStatusPacket {
    val active = field(U8) { if (it.active) 1 else 0 } == 1
    val startTime = if (active) field(S64LE) { it.startTime!! } else null
    val endTime = if (active) field(S64LE) { it.endTime!! } else null
    val value = if (active) field(S16LE) { it.value!! } else null
    val shininessType = if (active) field(S8) { it.shininessType!! } else null
    return WorldTimedEventStatusPacket(active, startTime, endTime, value, shininessType)
  }
}
