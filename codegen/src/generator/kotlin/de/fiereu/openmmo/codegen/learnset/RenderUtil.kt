package de.fiereu.openmmo.codegen.learnset

object RenderUtil {

  fun learnset(l: ParsedLearnset): String {
    val moves = l.moves.joinToString(", ") { "LevelUpMove(${it.level}, ${it.moveId})" }
    return "reg.register(${l.dexId}, listOf($moves))"
  }
}
