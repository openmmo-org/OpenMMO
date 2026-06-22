package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE

data class GuildRankPermissionUpdatePacket(
    val permCategory1: Short,
    val permCategory2: Short,
    val permCategory3: Short,
    val permCategory4: Short,
    val permCategory5: Short,
)

object GuildRankPermissionUpdatePacketCodec : PacketCodec<GuildRankPermissionUpdatePacket>() {
  override fun CodecScope<GuildRankPermissionUpdatePacket>.body(): GuildRankPermissionUpdatePacket {
    val permCategory1 = field(S16LE) { it.permCategory1 }
    val permCategory2 = field(S16LE) { it.permCategory2 }
    val permCategory3 = field(S16LE) { it.permCategory3 }
    val permCategory4 = field(S16LE) { it.permCategory4 }
    val permCategory5 = field(S16LE) { it.permCategory5 }
    return GuildRankPermissionUpdatePacket(
        permCategory1,
        permCategory2,
        permCategory3,
        permCategory4,
        permCategory5,
    )
  }
}
