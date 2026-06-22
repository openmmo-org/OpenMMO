package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.net.game.codecs.PokemonCodec

data class TradeListEntryPacket(
    val pokemon: Pokemon,
)

object TradeListEntryPacketCodec : PacketCodec<TradeListEntryPacket>() {
    override fun CodecScope<TradeListEntryPacket>.body(): TradeListEntryPacket {
        val pokemon = field(PokemonCodec) { it.pokemon }
        return TradeListEntryPacket(pokemon)
    }
}
