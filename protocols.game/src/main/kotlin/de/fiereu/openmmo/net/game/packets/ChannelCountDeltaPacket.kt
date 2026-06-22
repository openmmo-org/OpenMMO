package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class ChannelCountEntry(
    val channelId: Short,
    val flag: Boolean,
)

data class ChannelCountDeltaPacket(
    val category: Byte,
    val entries: List<ChannelCountEntry>,
    val delta: Byte,
)

private val ChannelCountEntryCodec: Codec<ChannelCountEntry> =
    object : PacketCodec<ChannelCountEntry>() {
      override fun CodecScope<ChannelCountEntry>.body(): ChannelCountEntry {
        val channelId = field(S16LE) { it.channelId }
        val flag = field(Bool) { it.flag }
        return ChannelCountEntry(channelId, flag)
      }
    }

object ChannelCountDeltaPacketCodec : PacketCodec<ChannelCountDeltaPacket>() {
  override fun CodecScope<ChannelCountDeltaPacket>.body(): ChannelCountDeltaPacket {
    val category = field(S8) { it.category }
    val count = field(U8) { it.entries.size }
    val entries = (0 until count).map { i -> field(ChannelCountEntryCodec) { it.entries[i] } }
    val delta = field(S8) { it.delta }
    return ChannelCountDeltaPacket(category, entries, delta)
  }
}
