package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE

data class PokedexSpeciesUnlockPacket(
    val speciesId: Short,
)

object PokedexSpeciesUnlockPacketCodec : PacketCodec<PokedexSpeciesUnlockPacket>() {
  override fun CodecScope<PokedexSpeciesUnlockPacket>.body(): PokedexSpeciesUnlockPacket {
    val speciesId = field(S16LE) { it.speciesId }
    return PokedexSpeciesUnlockPacket(speciesId)
  }
}
