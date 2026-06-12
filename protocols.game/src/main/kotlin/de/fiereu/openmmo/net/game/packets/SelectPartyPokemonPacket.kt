package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SelectPartyPokemonPacket(
    val entityIds: List<Long>,
)

object SelectPartyPokemonPacketCodec : PacketCodec<SelectPartyPokemonPacket>() {
    override fun CodecScope<SelectPartyPokemonPacket>.body(): SelectPartyPokemonPacket {
        val entityIds = field(S64LE.listPrefixed(U8)) { it.entityIds }
        return SelectPartyPokemonPacket(entityIds)
    }
}
