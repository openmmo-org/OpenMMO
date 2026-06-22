package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class SelectSinglePokemonPacket(
    val entityId: Long,
)

object SelectSinglePokemonPacketCodec : PacketCodec<SelectSinglePokemonPacket>() {
  override fun CodecScope<SelectSinglePokemonPacket>.body(): SelectSinglePokemonPacket {
    val entityId = field(S64LE) { it.entityId }
    return SelectSinglePokemonPacket(entityId)
  }
}
