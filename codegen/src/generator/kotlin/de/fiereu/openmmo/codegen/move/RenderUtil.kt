package de.fiereu.openmmo.codegen.move

object RenderUtil {

  fun move(m: ParsedMove): String {
    val flags = if (m.flags.isEmpty()) "emptySet()" else m.flags.joinToString(", ", "setOf(", ")")
    return "MoveDef(id = ${m.id}, name = ${escapeString(m.name)}, effect = ${m.effect}," +
        " power = ${m.power}, type = ${m.type}, accuracy = ${m.accuracy}, pp = ${m.pp}," +
        " secondaryEffectChance = ${m.secondaryEffectChance}, target = ${m.target}," +
        " priority = ${m.priority}, flags = $flags)"
  }

  private fun escapeString(s: String): String =
      "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\""
}
