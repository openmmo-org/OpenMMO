package de.fiereu.openmmo.codegen.move

fun typeRef(token: String): String {
  val name = token.removePrefix("TYPE_")
  val mapped = if (name == "MYSTERY") "QUESTIONQUESTIONQUESTION" else name
  return "PokemonType.$mapped"
}

fun effectRef(token: String): String = "MoveEffect.${token.removePrefix("EFFECT_")}"

fun targetRef(token: String): String = "MoveTarget.${token.removePrefix("MOVE_TARGET_")}"

fun flagRefs(expr: String): List<String> =
    expr
        .split("|")
        .map { it.trim() }
        .filter { it.isNotEmpty() && it != "0" }
        .map { "MoveFlag.${it.removePrefix("FLAG_")}" }
