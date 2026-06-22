package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S64LE

data class TeamPreviewSidePickPacket(
    val pokemonEntityId: Long,
    val side: Short,
)

object TeamPreviewSidePickPacketCodec : PacketCodec<TeamPreviewSidePickPacket>() {
  override fun CodecScope<TeamPreviewSidePickPacket>.body(): TeamPreviewSidePickPacket {
    val pokemonEntityId = field(S64LE) { it.pokemonEntityId }
    val side = field(S16LE) { it.side }
    return TeamPreviewSidePickPacket(pokemonEntityId, side)
  }
}
