package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ObjectiveProgressEntry(
    val id: Byte,
    val value: Int,
    val count: Short,
)

data class ObjectiveProgressBulkPacket(
    val entries: List<ObjectiveProgressEntry>,
)

private val ObjectiveProgressEntryCodec: Codec<ObjectiveProgressEntry> =
    object : PacketCodec<ObjectiveProgressEntry>() {
      override fun CodecScope<ObjectiveProgressEntry>.body(): ObjectiveProgressEntry {
        val id = field(S8) { it.id }
        val value = field(S32LE) { it.value }
        val count = field(S16LE) { it.count }
        return ObjectiveProgressEntry(id, value, count)
      }
    }

object ObjectiveProgressBulkPacketCodec : PacketCodec<ObjectiveProgressBulkPacket>() {
  override fun CodecScope<ObjectiveProgressBulkPacket>.body(): ObjectiveProgressBulkPacket {
    val entries = field(ObjectiveProgressEntryCodec.listPrefixed(U8)) { it.entries }
    return ObjectiveProgressBulkPacket(entries)
  }
}
