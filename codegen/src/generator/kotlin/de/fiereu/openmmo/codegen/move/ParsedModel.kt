package de.fiereu.openmmo.codegen.move

data class ParsedMove(
    val id: Int,
    val name: String,
    val effect: String,
    val power: Int,
    val type: String,
    val accuracy: Int,
    val pp: Int,
    val secondaryEffectChance: Int,
    val target: String,
    val priority: Int,
    val flags: List<String>,
)
