package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.server.game.domain.OwnedPokemon
import java.time.LocalDateTime

/** Bridges server-authoritative domain Pokemon to the v31914 PokemonContainer wire codec shape. */
object PartyPokemonMapper {
  fun toWirePokemon(
      pokemon: OwnedPokemon,
      container: PokemonContainer,
      slot: Int,
      caughtAt: LocalDateTime = LocalDateTime.now(),
  ): Pokemon =
      Pokemon(
          id = stableWireId(pokemon.uid),
          container = container,
          containerSlot = slot.toShort(),
          dexId = pokemon.speciesId,
          seed = pokemon.pid.toInt(),
          ot = pokemon.otName,
          nickname = pokemon.nickname ?: "Species #${pokemon.speciesId}",
          level = pokemon.level.toByte(),
          hp = estimatedHp(pokemon).toShort(),
          xp = pokemon.exp,
          eVs = pokemon.evs.toWireEvs(),
          iVs = pokemon.ivs.toWireIvs(),
          moves = pokemon.moves.toWireMoves(),
          isShiny = pokemon.shiny,
          hasHiddenAbility = false,
          isAlpha = false,
          isSecret = false,
          isFatefulEncounter = false,
          isRaidEncounter = false,
          caughtAt = caughtAt,
      )

  fun toWireParty(party: List<OwnedPokemon>): List<Pokemon> =
      party.take(6).mapIndexed { index, pokemon ->
        toWirePokemon(pokemon, PokemonContainer.PARTY, index)
      }

  fun toWireContainer(
      pokemon: List<OwnedPokemon>,
      container: PokemonContainer,
  ): List<Pokemon> = pokemon.mapIndexed { index, mon -> toWirePokemon(mon, container, index) }

  private fun stableWireId(uid: String): Long {
    var hash = 1125899906842597L
    for (ch in uid) hash = 31 * hash + ch.code
    return hash and Long.MAX_VALUE
  }

  private fun estimatedHp(pokemon: OwnedPokemon): Int =
      maxOf(1, 10 + pokemon.level * 2 + pokemon.ivs.hp / 2)

  private fun List<de.fiereu.openmmo.server.game.domain.PokemonMoveSlot>.toWireMoves():
      List<PokemonMove> {
    val converted =
        map { PokemonMove(it.moveId.toShort(), it.ppCurrent.toByte()) }.take(4).toMutableList()
    while (converted.size < 4) converted.add(PokemonMove(0, 0))
    return converted
  }

  private fun de.fiereu.openmmo.server.game.domain.StatBlock.toWireEvs(): EVs =
      EVs().also {
        if (hp > 0) it.hp = hp.coerceIn(1, 252)
        if (atk > 0) it.atk = atk.coerceIn(1, 252)
        if (def > 0) it.def = def.coerceIn(1, 252)
        if (spa > 0) it.spAtk = spa.coerceIn(1, 252)
        if (spd > 0) it.spDef = spd.coerceIn(1, 252)
        if (spe > 0) it.spd = spe.coerceIn(1, 252)
      }

  private fun de.fiereu.openmmo.server.game.domain.StatBlock.toWireIvs(): IVs =
      IVs().also {
        it.hp = hp.coerceIn(0, 31)
        it.atk = atk.coerceIn(0, 31)
        it.def = def.coerceIn(0, 31)
        it.spAtk = spa.coerceIn(0, 31)
        it.spDef = spd.coerceIn(0, 31)
        it.spd = spe.coerceIn(0, 31)
      }
}
