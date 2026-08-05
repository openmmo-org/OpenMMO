package de.fiereu.openmmo.codegen.learnset

data class ParsedLearnset(val dexId: Int, val moves: List<ParsedLevelUpMove>)

data class ParsedLevelUpMove(val level: Int, val moveId: Int)
