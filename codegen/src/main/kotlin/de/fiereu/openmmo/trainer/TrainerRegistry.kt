package de.fiereu.openmmo.trainer

import de.fiereu.openmmo.trainer.generated.GeneratedHoennTrainers
import de.fiereu.openmmo.trainer.generated.GeneratedKantoTrainers
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/** A monster on a trainer's team. Empty [moveIds] means the level up moveset is used. */
data class TrainerMon(
    val dexId: Int,
    val level: Int,
    val iv: Int,
    val heldItem: Int,
    val moveIds: List<Int>,
)

data class TrainerDef(
    val id: Int,
    val name: String,
    val trainerClass: Int,
    val doubleBattle: Boolean,
    val party: List<TrainerMon>,
)

/**
 * The trainers from the decomp, keyed by region and trainer id. The two regions number their
 * trainers from zero, so the region namespaces the key the same way story flags are namespaced.
 */
@Singleton
class TrainerRegistry @Inject constructor() {

  private val trainers = ConcurrentHashMap<String, TrainerDef>()

  init {
    GeneratedHoennTrainers.loadInto(this)
    GeneratedKantoTrainers.loadInto(this)
  }

  fun register(region: String, def: TrainerDef) {
    trainers[key(region, def.id)] = def
  }

  fun get(region: String, id: Int): TrainerDef? = trainers[key(region, id)]

  fun size(): Int = trainers.size

  private fun key(region: String, id: Int) = "$region/$id"
}
