package de.fiereu.openmmo.codegen.trainer

/** A monster on a trainer's team. Empty [moveIds] means the level up moveset is used. */
data class ParsedTrainerMon(
    val dexId: Int,
    val level: Int,
    val iv: Int,
    val heldItem: Int,
    val moveIds: List<Int>,
)

data class ParsedTrainer(
    val id: Int,
    val name: String,
    val trainerClass: Int,
    val doubleBattle: Boolean,
    val prizeRate: Int,
    val party: List<ParsedTrainerMon>,
)
