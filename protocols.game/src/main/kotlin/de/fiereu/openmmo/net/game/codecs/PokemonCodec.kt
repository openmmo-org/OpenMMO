package de.fiereu.openmmo.net.game.codecs

import de.fiereu.bytecodec.*
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.*

private val PokemonUnknownBytes: Codec<ByteArray> = bytesPrefixed(U8)

object PokemonCodec : PacketCodec<Pokemon>() {
  override fun CodecScope<Pokemon>.body(): Pokemon {
    val id = field(S64LE, Pokemon::id)
    field(S8) { 0 }
    field(S64LE) { 0L }
    field(S64LE) { 0L }
    val container = PokemonContainer.entries[field(U8) { it.container.ordinal }]
    val containerSlot = field(S16LE, Pokemon::containerSlot)
    val dexId = field(U16LE, Pokemon::dexId)
    val seed = field(S32LE, Pokemon::seed)
    field(S64LE) { 0L }
    val ot = field(Utf16LeNullTerminated, Pokemon::ot)
    val nickname = field(Utf16LeNullTerminated, Pokemon::nickname)
    field(S8) { 0 }
    field(S8) { 0 }
    val level = field(S8, Pokemon::level)
    val currentHp = field(S16LE, Pokemon::hp)
    field(S16LE) { 0 }
    val xp = field(S32LE, Pokemon::xp)
    field(S8) { 0 }
    field(S16LE) { 0 }
    val moveIds = List(4) { i -> field(S16LE) { it.moves[i].id } }
    val movePps = List(4) { i -> field(S8) { it.moves[i].pp } }
    val moves = List(4) { PokemonMove(moveIds[it], movePps[it]) }
    repeat(4) { field(S16LE) { 0 } }
    val eVs =
        EVs().apply {
          hp = field(U8) { it.eVs.hp }
          atk = field(U8) { it.eVs.atk }
          def = field(U8) { it.eVs.def }
          spd = field(U8) { it.eVs.spd }
          spAtk = field(U8) { it.eVs.spAtk }
          spDef = field(U8) { it.eVs.spDef }
        }
    repeat(11) { field(S8) { 0 } }
    val iVs = decompressIVs(field(S32LE) { it.iVs.compress() })
    field(S8) { 0 }
    field(S64LE) { 0L }
    val rarityMask =
        field(U16LE) {
          var m = 0
          if (it.isShiny) m = m or (1 shl PokemonRarity.SHINY.ordinal)
          if (it.hasHiddenAbility) m = m or (1 shl PokemonRarity.HIDDEN_ABILITY.ordinal)
          if (it.isAlpha) m = m or (1 shl PokemonRarity.ALPHA.ordinal)
          if (it.isSecret) m = m or (1 shl PokemonRarity.SECRET.ordinal)
          if (it.isFatefulEncounter) m = m or (1 shl PokemonRarity.FATEFUL_ENCOUNTER.ordinal)
          if (it.isRaidEncounter) m = m or (1 shl PokemonRarity.RAID_ENCOUNTER.ordinal)
          m
        }
    val isShiny = (rarityMask and (1 shl PokemonRarity.SHINY.ordinal)) != 0
    val hasHiddenAbility = (rarityMask and (1 shl PokemonRarity.HIDDEN_ABILITY.ordinal)) != 0
    val isAlpha = (rarityMask and (1 shl PokemonRarity.ALPHA.ordinal)) != 0
    val isSecret = (rarityMask and (1 shl PokemonRarity.SECRET.ordinal)) != 0
    val isFatefulEncounter = (rarityMask and (1 shl PokemonRarity.FATEFUL_ENCOUNTER.ordinal)) != 0
    val isRaidEncounter = (rarityMask and (1 shl PokemonRarity.RAID_ENCOUNTER.ordinal)) != 0
    val caughtAt = field(EpochSecondsS32LE, Pokemon::caughtAt)
    field(S16LE) { 0 }
    field(S8) { 0 }
    field(S8) { 0 }
    field(PokemonUnknownBytes) { ByteArray(0) }
    repeat(12) { field(S8) { 0 } }
    return Pokemon(
        id,
        container,
        containerSlot,
        dexId,
        seed,
        ot,
        nickname,
        level,
        currentHp,
        xp,
        eVs,
        iVs,
        moves,
        isShiny,
        hasHiddenAbility,
        isAlpha,
        isSecret,
        isFatefulEncounter,
        isRaidEncounter,
        caughtAt,
    )
  }
}
