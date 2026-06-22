package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class TrackedEntitySlot(
    val count: Short,
    val entityId: Long,
)

data class TrackedEntitySlotsPacket(
    val slots: List<TrackedEntitySlot>,
)

private val TrackedEntitySlotCodec: Codec<TrackedEntitySlot> =
    object : PacketCodec<TrackedEntitySlot>() {
      override fun CodecScope<TrackedEntitySlot>.body(): TrackedEntitySlot {
        val count = field(S16LE) { it.count }
        val entityId = field(S64LE) { it.entityId }
        return TrackedEntitySlot(count, entityId)
      }
    }

object TrackedEntitySlotsPacketCodec : PacketCodec<TrackedEntitySlotsPacket>() {
  override fun CodecScope<TrackedEntitySlotsPacket>.body(): TrackedEntitySlotsPacket {
    val slots = field(TrackedEntitySlotCodec.listPrefixed(U8)) { it.slots }
    return TrackedEntitySlotsPacket(slots)
  }
}
