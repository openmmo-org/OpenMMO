package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.pokemon.SpeciesRegistry
import de.fiereu.openmmo.server.game.storage.EntityIdService
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

private const val TACKLE_ID = 33
private const val FALLBACK_PP = 35

/** Rolls a wild monster: random nature seed, random IVs, computed stats, full hp. */
@Singleton
class WildMonFactory
@Inject
constructor(
    private val species: SpeciesRegistry,
    private val moves: MoveRegistry,
    private val entityIds: EntityIdService,
) {

  fun create(dexId: Int, level: Int, rng: BattleRng): Pokemon? {
    val def = species.get(dexId) ?: return null
    val ivs =
        IVs().apply {
          hp = rng.ivRoll()
          atk = rng.ivRoll()
          this.def = rng.ivRoll()
          spAtk = rng.ivRoll()
          spDef = rng.ivRoll()
          spd = rng.ivRoll()
        }
    // TODO Give wild monsters their real level up moveset
    //  Parse level_up_learnsets.h in a new codegen generator, use it here for wild movesets and
    //  for learning moves on level up, and capture the client's move learn prompt packets.
    val tacklePp = (moves.get(TACKLE_ID)?.pp ?: FALLBACK_PP).toByte()
    val mon =
        Pokemon(
            id = entityIds.newMonsterId(),
            ownerId = 0,
            container = PokemonContainer.PARTY,
            containerSlot = 0,
            dexId = dexId,
            seed = rng.natureSeed(),
            ot = "",
            nickname = "",
            level = level.toByte(),
            hp = 0,
            xp = ExpCurves.totalXpFor(def.growthRate, level),
            eVs = EVs(),
            iVs = ivs,
            moves =
                listOf(
                    PokemonMove(TACKLE_ID.toShort(), tacklePp),
                    PokemonMove(0, 0),
                    PokemonMove(0, 0),
                    PokemonMove(0, 0),
                ),
            isShiny = false,
            hasHiddenAbility = false,
            isAlpha = false,
            isSecret = false,
            isFatefulEncounter = false,
            isRaidEncounter = false,
            caughtAt = LocalDateTime.now(),
        )
    return mon.copy(hp = StatCalculator.computeAll(def, mon).hp.toShort())
  }
}
