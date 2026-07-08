package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.CharacterInfo
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.net.game.codecs.*

data class CharacterEntry(
    val characterInfo: CharacterInfo,
    val skinSet: SkinSet,
    val guildId: Int? = null,
    val pokemon: List<Pokemon>,
) {
  init {
    require(pokemon.size <= 6) { "Party size can't be more than 6 pokemon" }
  }
}

data class CharactersListPacket(val characters: List<CharacterEntry>)

private val CharacterInfoAsymmetric: Codec<CharacterInfo> =
    object : Codec<CharacterInfo> {
      override fun read(buf: ReadBuffer): CharacterInfo = CharacterInfoCodecLong.read(buf)

      override fun write(buf: WriteBuffer, value: CharacterInfo) {
        CharacterInfoCodecShort.write(buf, value)
      }
    }

private object CharacterEntryCodec : PacketCodec<CharacterEntry>() {
  override fun CodecScope<CharacterEntry>.body(): CharacterEntry {
    val info = field(CharacterInfoAsymmetric, CharacterEntry::characterInfo)
    val skin = field(DefaultSkinSetCodec, CharacterEntry::skinSet)
    field(SkinSetCodecNoLeading, CharacterEntry::skinSet)
    val hasGuild = field(Bool) { it.guildId != null }
    val guildId =
        if (hasGuild) {
          field(Utf16LeNullTerminated) { "" }
          field(S32LE) { it.guildId!! }
        } else {
          null
        }
    val partySize = field(U8) { it.pokemon.size }
    reserved(byte = 0)
    val party = List(partySize) { field(PokemonCodec) { entry -> entry.pokemon[it] } }
    return CharacterEntry(info, skin, guildId, party)
  }
}

private val CharacterEntryListPrefixedU8: Codec<List<CharacterEntry>> =
    object : Codec<List<CharacterEntry>> {
      override fun read(buf: ReadBuffer): List<CharacterEntry> {
        val n = U8.read(buf)
        return List(n) { CharacterEntryCodec.read(buf) }
      }

      override fun write(buf: WriteBuffer, value: List<CharacterEntry>) {
        U8.write(buf, value.size)
        value.forEach { CharacterEntryCodec.write(buf, it) }
      }
    }

object CharactersListPacketCodec : PacketCodec<CharactersListPacket>() {
  override fun CodecScope<CharactersListPacket>.body() =
      CharactersListPacket(
          characters = field(CharacterEntryListPrefixedU8, CharactersListPacket::characters),
      )
}
