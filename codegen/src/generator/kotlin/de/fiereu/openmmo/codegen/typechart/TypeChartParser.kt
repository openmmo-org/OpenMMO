package de.fiereu.openmmo.codegen.typechart

import de.fiereu.openmmo.codegen.move.typeRef
import java.io.File

class TypeChartParser(private val rootDir: File) {

  fun parseAll(): List<ParsedMatchup> {
    val file = File(rootDir, "src/battle_main.c")
    require(file.exists()) {
      "Decomp not initialized at $rootDir (missing ${file.path}). " +
          "Run: git submodule update --init --recursive"
    }
    val table =
        TABLE_REGEX.find(file.readText())
            ?: error("Missing gTypeEffectiveness table in ${file.path}")
    return ROW_REGEX.findAll(table.groupValues[1])
        .filter { it.groupValues[1] !in CONTROL_ROWS && it.groupValues[2] !in CONTROL_ROWS }
        .map {
          ParsedMatchup(
              attacker = typeRef("TYPE_${it.groupValues[1]}"),
              defender = typeRef("TYPE_${it.groupValues[2]}"),
              multiplier = MULTIPLIERS.getValue(it.groupValues[3]),
          )
        }
        .toList()
  }

  companion object {
    private val TABLE_REGEX =
        Regex("""gTypeEffectiveness\[\d+]\s*=\s*\{(.*?)};""", RegexOption.DOT_MATCHES_ALL)
    private val ROW_REGEX = Regex("""TYPE_(\w+),\s*TYPE_(\w+),\s*TYPE_MUL_(\w+)""")

    // The Foresight sentinel only separates the ghost immunities that move disables. Foresight
    // is not implemented, so the rows behind it are kept as plain immunities.
    private val CONTROL_ROWS = setOf("FORESIGHT", "ENDTABLE")
    private val MULTIPLIERS = mapOf("NO_EFFECT" to 0, "NOT_EFFECTIVE" to 5, "SUPER_EFFECTIVE" to 20)
  }
}
