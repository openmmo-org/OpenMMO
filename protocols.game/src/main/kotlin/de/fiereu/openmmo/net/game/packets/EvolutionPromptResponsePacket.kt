package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class EvolutionPromptResponsePacket(val pokemonEntityId: Long, val accepted: Boolean)

object EvolutionPromptResponsePacketCodec : PacketCodec<EvolutionPromptResponsePacket>() {
    override fun CodecScope<EvolutionPromptResponsePacket>.body(): EvolutionPromptResponsePacket {
        val pokemonEntityId = field(S64LE) { it.pokemonEntityId }
        val accepted = field(Bool) { it.accepted }
        return EvolutionPromptResponsePacket(pokemonEntityId, accepted)
    }
}
