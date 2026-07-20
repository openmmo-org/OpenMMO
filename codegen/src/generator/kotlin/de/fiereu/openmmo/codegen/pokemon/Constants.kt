package de.fiereu.openmmo.codegen.pokemon

fun typeRef(token: String): String {
  val name = token.removePrefix("TYPE_")
  val mapped = if (name == "MYSTERY") "QUESTIONQUESTIONQUESTION" else name
  return "PokemonType.$mapped"
}

fun abilityRef(token: String): String = "Ability.${token.removePrefix("ABILITY_")}"

fun growthRef(token: String): String = "GrowthRate.${token.removePrefix("GROWTH_")}"

fun eggGroupRef(token: String): String = "EggGroup.${token.removePrefix("EGG_GROUP_")}"

fun bodyColorRef(token: String): String = "BodyColor.${token.removePrefix("BODY_COLOR_")}"
