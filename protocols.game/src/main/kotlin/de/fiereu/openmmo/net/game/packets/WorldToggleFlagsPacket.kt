package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class WorldToggleFlagsEntry(
    val id: Short,
    val state: Boolean,
)

data class WorldToggleFlagsPacket(
    val entries: List<WorldToggleFlagsEntry>,
)

private object WorldToggleFlagsEntryCodec : PacketCodec<WorldToggleFlagsEntry>() {
  override fun CodecScope<WorldToggleFlagsEntry>.body(): WorldToggleFlagsEntry {
    val id = field(S16LE, WorldToggleFlagsEntry::id)
    val state = field(Bool, WorldToggleFlagsEntry::state)
    return WorldToggleFlagsEntry(id, state)
  }
}

private val WorldToggleFlagsEntryListPrefixedU8: Codec<List<WorldToggleFlagsEntry>> =
    object : Codec<List<WorldToggleFlagsEntry>> {
      override fun read(buf: ReadBuffer): List<WorldToggleFlagsEntry> {
        val n = U8.read(buf)
        return List(n) { WorldToggleFlagsEntryCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<WorldToggleFlagsEntry>) {
        U8.write(buf, value.size)
        value.forEach { WorldToggleFlagsEntryCodec.write(buf, it) }
      }
    }

object WorldToggleFlagsPacketCodec : PacketCodec<WorldToggleFlagsPacket>() {
  override fun CodecScope<WorldToggleFlagsPacket>.body(): WorldToggleFlagsPacket {
    val entries = field(WorldToggleFlagsEntryListPrefixedU8, WorldToggleFlagsPacket::entries)
    return WorldToggleFlagsPacket(entries)
  }
}
