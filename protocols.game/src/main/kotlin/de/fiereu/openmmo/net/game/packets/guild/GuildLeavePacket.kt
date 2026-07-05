package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec

class GuildLeavePacket

object GuildLeavePacketCodec : PacketCodec<GuildLeavePacket>() {
  override fun CodecScope<GuildLeavePacket>.body(): GuildLeavePacket {
    return GuildLeavePacket()
  }
}
