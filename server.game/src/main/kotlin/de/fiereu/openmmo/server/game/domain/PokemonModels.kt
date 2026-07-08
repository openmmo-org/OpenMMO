package de.fiereu.openmmo.server.game.domain

data class OwnedPokemon(
    val uid: String,
    val speciesId: Int,
    val nickname: String? = null,
    val level: Int,
    val exp: Int,
    val nature: Nature,
    val ability: String,
    val gender: Gender,
    val shiny: Boolean,
    val ivs: IVs,
    val evs: StatBlock = StatBlock.ZERO,
    val moves: List<PokemonMoveSlot> = emptyList(),
    val heldItem: String? = null,
    val friendship: Int,
    val otWallet: String,
    val otName: String,
    val pid: UInt,
    val metLevel: Int,
    val metLocation: String,
    val eggSteps: Int? = null,
)

data class PokemonMoveSlot(val moveId: Int, val ppUp: Int, val ppCurrent: Int)

data class Species(
    val id: Int,
    val name: String,
    val abilities: List<String> = emptyList(),
    val eggGroups: List<EggGroup> = emptyList(),
    /** 0..1 fraction male, or -1 for genderless. */
    val genderRatio: Double = -1.0,
    val catchRate: Int = 255,
    val baseExp: Int = 0,
    val growthRate: GrowthRate = GrowthRate.MEDIUM_FAST,
    val evolutions: List<EvolutionEdge> = emptyList(),
)

data class EvolutionEdge(val to: Int, val method: EvolutionMethod)

sealed interface EvolutionMethod {
  data class Level(val level: Int) : EvolutionMethod

  data class Item(val item: String) : EvolutionMethod

  data class Trade(val heldItem: String? = null) : EvolutionMethod

  data class Friendship(val timeOfDay: TimeOfDay? = null) : EvolutionMethod

  data class Move(val move: String) : EvolutionMethod

  data class Location(val mapId: String) : EvolutionMethod

  data class Other(val note: String) : EvolutionMethod
}

enum class TimeOfDay {
  DAY,
  NIGHT
}
