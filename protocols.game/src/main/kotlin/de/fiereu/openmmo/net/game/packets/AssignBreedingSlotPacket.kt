package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class AssignBreedingSlotPacket(
    val ownPokemonEntityId: Long,
    val partnerPokemonEntityId: Long,
    val slotIndex: Byte,
)

object AssignBreedingSlotPacketCodec : PacketCodec<AssignBreedingSlotPacket>() {
    override fun CodecScope<AssignBreedingSlotPacket>.body(): AssignBreedingSlotPacket {
        val ownPokemonEntityId = field(S64LE) { it.ownPokemonEntityId }
        val partnerPokemonEntityId = field(S64LE) { it.partnerPokemonEntityId }
        val slotIndex = field(S8) { it.slotIndex }
        return AssignBreedingSlotPacket(ownPokemonEntityId, partnerPokemonEntityId, slotIndex)
    }
}
