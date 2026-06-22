package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.net.game.codecs.PokemonCodec

data class SocialListEntryAddPacket(
    val pokemon: Pokemon,
)

object SocialListEntryAddPacketCodec : PacketCodec<SocialListEntryAddPacket>() {
    override fun CodecScope<SocialListEntryAddPacket>.body(): SocialListEntryAddPacket {
        val pokemon = field(PokemonCodec) { it.pokemon }
        return SocialListEntryAddPacket(pokemon)
    }
}
