package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.enums.PokemonNature
import de.fiereu.openmmo.common.enums.PokemonStat
import de.fiereu.openmmo.pokemon.SpeciesDef

data class ComputedStats(
    val hp: Int,
    val atk: Int,
    val def: Int,
    val spAtk: Int,
    val spDef: Int,
    val spd: Int,
)

/** Gen 3 stat formulas over base stats, IVs, EVs, level, and nature. */
object StatCalculator {

  fun maxHp(base: Int, iv: Int, ev: Int, level: Int): Int =
      (2 * base + iv + ev / 4) * level / 100 + level + 10

  fun stat(
      base: Int,
      iv: Int,
      ev: Int,
      level: Int,
      nature: PokemonNature,
      stat: PokemonStat,
  ): Int {
    val raw = (2 * base + iv + ev / 4) * level / 100 + 5
    return when {
      nature.increasedStat == nature.decreasedStat -> raw
      nature.increasedStat == stat -> raw * 110 / 100
      nature.decreasedStat == stat -> raw * 90 / 100
      else -> raw
    }
  }

  fun computeAll(species: SpeciesDef, pokemon: Pokemon): ComputedStats {
    val level = pokemon.level.toInt()
    val nature = pokemon.nature
    val ivs = pokemon.iVs
    val evs = pokemon.eVs
    return ComputedStats(
        hp = maxHp(species.baseHp, ivs.hp, evs.hp, level),
        atk = stat(species.baseAttack, ivs.atk, evs.atk, level, nature, PokemonStat.ATTACK),
        def = stat(species.baseDefense, ivs.def, evs.def, level, nature, PokemonStat.DEFENSE),
        spAtk =
            stat(species.baseSpAttack, ivs.spAtk, evs.spAtk, level, nature, PokemonStat.SP_ATTACK),
        spDef =
            stat(
                species.baseSpDefense, ivs.spDef, evs.spDef, level, nature, PokemonStat.SP_DEFENSE),
        spd = stat(species.baseSpeed, ivs.spd, evs.spd, level, nature, PokemonStat.SPEED),
    )
  }
}
