package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class PokemonFlagsUpdatePacket(val entityId: Long, val flags: Byte)

object PokemonFlagsUpdatePacketCodec : PacketCodec<PokemonFlagsUpdatePacket>() {
    override fun CodecScope<PokemonFlagsUpdatePacket>.body(): PokemonFlagsUpdatePacket {
        val entityId = field(S64LE) { it.entityId }
        val flags = field(S8) { it.flags }
        return PokemonFlagsUpdatePacket(entityId, flags)
    }
}
