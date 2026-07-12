package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.Utf16LeNullTerminated

data class GuildRankLabelUpdatePacket(
    val rankOrdinal: Int,
    val rankLabel: String,
)

object GuildRankLabelUpdatePacketCodec : PacketCodec<GuildRankLabelUpdatePacket>() {
  override fun CodecScope<GuildRankLabelUpdatePacket>.body(): GuildRankLabelUpdatePacket {
    val rankOrdinal = field(U8) { it.rankOrdinal }
    val rankLabel = field(Utf16LeNullTerminated) { it.rankLabel }
    return GuildRankLabelUpdatePacket(rankOrdinal, rankLabel)
  }
}
