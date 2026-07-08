package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.reserved
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.net.game.codecs.PokemonCodec

/**
 * S2C 0x14: single Pokemon add delta.
 *
 * Validated from catch bytes as a leading zero header followed by one full PokemonCodec record.
 * This is distinct from 0x13 PokemonContainerPacket, which carries a bulk container/list.
 */
data class SinglePokemonAddPacket(
    val pokemon: Pokemon,
)

object SinglePokemonAddPacketCodec : PacketCodec<SinglePokemonAddPacket>() {
  override fun CodecScope<SinglePokemonAddPacket>.body(): SinglePokemonAddPacket {
    reserved(byte = 0)
    val pokemon = field(PokemonCodec) { it.pokemon }
    return SinglePokemonAddPacket(pokemon)
  }
}
