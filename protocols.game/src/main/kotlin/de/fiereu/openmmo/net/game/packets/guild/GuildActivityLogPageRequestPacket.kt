package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE

data class GuildActivityLogPageRequestPacket(
    val pageIndex: Short,
)

object GuildActivityLogPageRequestPacketCodec : PacketCodec<GuildActivityLogPageRequestPacket>() {
  override fun CodecScope<GuildActivityLogPageRequestPacket>.body():
      GuildActivityLogPageRequestPacket {
    val pageIndex = field(S16LE) { it.pageIndex }
    return GuildActivityLogPageRequestPacket(pageIndex)
  }
}
