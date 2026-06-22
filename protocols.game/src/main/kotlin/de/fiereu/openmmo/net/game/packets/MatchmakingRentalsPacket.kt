package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.net.game.codecs.PokemonCodec

data class MatchmakingRentalPreview(
    val speciesId: Short,
    val level: Short,
)

data class MatchmakingRentalsPacket(
    val mode: Byte,
    val param1: Byte,
    val param2: Byte,
    val param3: Byte,
    val previews: List<MatchmakingRentalPreview>?,
    val pokemon: List<Pokemon>?,
)

private object MatchmakingRentalPreviewCodec : PacketCodec<MatchmakingRentalPreview>() {
  override fun CodecScope<MatchmakingRentalPreview>.body(): MatchmakingRentalPreview {
    val speciesId = field(S16LE, MatchmakingRentalPreview::speciesId)
    val level = field(S16LE, MatchmakingRentalPreview::level)
    return MatchmakingRentalPreview(speciesId, level)
  }
}

private val MatchmakingRentalPreviewListPrefixedU8: Codec<List<MatchmakingRentalPreview>> =
    object : Codec<List<MatchmakingRentalPreview>> {
      override fun read(buf: ReadBuffer): List<MatchmakingRentalPreview> {
        val n = U8.read(buf)
        return List(n) { MatchmakingRentalPreviewCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<MatchmakingRentalPreview>) {
        U8.write(buf, value.size)
        value.forEach { MatchmakingRentalPreviewCodec.write(buf, it) }
      }
    }

private val PokemonListPrefixedU8: Codec<List<Pokemon>> =
    object : Codec<List<Pokemon>> {
      override fun read(buf: ReadBuffer): List<Pokemon> {
        val n = U8.read(buf)
        return List(n) { PokemonCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<Pokemon>) {
        U8.write(buf, value.size)
        value.forEach { PokemonCodec.write(buf, it) }
      }
    }

object MatchmakingRentalsPacketCodec : PacketCodec<MatchmakingRentalsPacket>() {
  override fun CodecScope<MatchmakingRentalsPacket>.body(): MatchmakingRentalsPacket {
    val mode = field(S8, MatchmakingRentalsPacket::mode)
    val param1 = field(S8, MatchmakingRentalsPacket::param1)
    val param2 = field(S8, MatchmakingRentalsPacket::param2)
    val param3 = field(S8, MatchmakingRentalsPacket::param3)
    val previews =
        if (mode.toInt() == 2)
            field(MatchmakingRentalPreviewListPrefixedU8) { it.previews ?: emptyList() }
        else null
    val pokemon =
        if (mode.toInt() == 0) field(PokemonListPrefixedU8) { it.pokemon ?: emptyList() } else null
    return MatchmakingRentalsPacket(mode, param1, param2, param3, previews, pokemon)
  }
}
