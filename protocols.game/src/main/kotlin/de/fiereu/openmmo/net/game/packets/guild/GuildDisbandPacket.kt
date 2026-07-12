package de.fiereu.openmmo.net.game.packets.guild

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class GuildDisbandPacket(
    val initiate: Boolean,
    val ownerEntityId: Long,
)

object GuildDisbandPacketCodec : PacketCodec<GuildDisbandPacket>() {
  override fun CodecScope<GuildDisbandPacket>.body(): GuildDisbandPacket {
    val initiate = field(Bool) { it.initiate }
    val ownerEntityId = field(S64LE) { it.ownerEntityId }
    return GuildDisbandPacket(initiate, ownerEntityId)
  }
}
