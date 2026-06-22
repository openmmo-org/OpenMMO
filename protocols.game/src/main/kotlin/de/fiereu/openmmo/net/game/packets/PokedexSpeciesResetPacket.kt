package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class PokedexSpeciesResetPacket(
    val speciesIds: List<Short>,
)

object PokedexSpeciesResetPacketCodec : PacketCodec<PokedexSpeciesResetPacket>() {
  override fun CodecScope<PokedexSpeciesResetPacket>.body(): PokedexSpeciesResetPacket {
    val speciesIds = field(S16LE.listPrefixed(U16LE)) { it.speciesIds }
    return PokedexSpeciesResetPacket(speciesIds)
  }
}
