package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class GuildDepartPacket

object GuildDepartPacketCodec : PacketCodec<GuildDepartPacket>() {
  override fun CodecScope<GuildDepartPacket>.body(): GuildDepartPacket {
    return GuildDepartPacket()
  }
}
