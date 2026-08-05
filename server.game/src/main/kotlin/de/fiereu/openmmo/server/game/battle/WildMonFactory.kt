package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.common.MAX_MOVE_SLOTS
import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.moves.MoveRegistry
import de.fiereu.openmmo.pokemon.LearnsetRegistry
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
    private val learnsets: LearnsetRegistry,
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
    // Species without a learnset fall back to Tackle so the monster can still attack.
    val moveIds = learnsets.initialMoveset(dexId, level).ifEmpty { listOf(TACKLE_ID) }
    val moveset =
        moveIds.map { PokemonMove(it.toShort(), (moves.get(it)?.pp ?: FALLBACK_PP).toByte()) } +
            List(MAX_MOVE_SLOTS - moveIds.size) { PokemonMove(0, 0) }
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
            moves = moveset,
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
