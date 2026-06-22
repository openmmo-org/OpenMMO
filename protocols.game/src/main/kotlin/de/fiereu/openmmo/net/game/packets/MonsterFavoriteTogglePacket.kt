package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.Bool
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class MonsterFavoriteTogglePacket(val pokemonEntityId: Long, val favorite: Boolean)

object MonsterFavoriteTogglePacketCodec : PacketCodec<MonsterFavoriteTogglePacket>() {
  override fun CodecScope<MonsterFavoriteTogglePacket>.body(): MonsterFavoriteTogglePacket {
    val pokemonEntityId = field(S64LE) { it.pokemonEntityId }
    val favorite = field(Bool) { it.favorite }
    return MonsterFavoriteTogglePacket(pokemonEntityId, favorite)
  }
}
