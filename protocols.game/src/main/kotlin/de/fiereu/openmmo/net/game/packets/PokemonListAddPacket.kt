package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class PokemonListAddPacket(
    val entityId: Long,
    val slot: Short,
    val listType: Byte,
)

object PokemonListAddPacketCodec : PacketCodec<PokemonListAddPacket>() {
  override fun CodecScope<PokemonListAddPacket>.body(): PokemonListAddPacket {
    val entityId = field(S64LE) { it.entityId }
    val slot = field(S16LE) { it.slot }
    val listType = field(S8) { it.listType }
    return PokemonListAddPacket(entityId, slot, listType)
  }
}
