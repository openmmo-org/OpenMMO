package de.fiereu.openmmo.codegen.move

import java.io.File

class MoveParser(private val rootDir: File) {

  fun parseAll(): List<ParsedMove> {
    val ids = readMoveIds()
    val names = readMoveNames()
    val entries = readBattleMoves()

    return ids.entries
        .sortedBy { it.value }
        .mapNotNull { (constant, id) ->
          val fields = entries[constant] ?: return@mapNotNull null
          ParsedMove(
              id = id,
              name = names[constant] ?: constant.removePrefix("MOVE_"),
              effect = effectRef(fields.getValue("effect")),
              power = fields.getValue("power").toInt(),
              type = typeRef(fields.getValue("type")),
              accuracy = fields.getValue("accuracy").toInt(),
              pp = fields.getValue("pp").toInt(),
              secondaryEffectChance = fields.getValue("secondaryEffectChance").toInt(),
              target = targetRef(fields.getValue("target")),
              priority = fields.getValue("priority").toInt(),
              flags = flagRefs(fields.getValue("flags")),
          )
        }
  }

  private fun readMoveIds(): Map<String, Int> {
    val file = File(rootDir, "include/constants/moves.h")
    require(file.exists()) {
      "Decomp not initialized at $rootDir (missing ${file.path}). " +
          "Run: git submodule update --init --recursive"
    }
    val regex = Regex("""^#define\s+(MOVE_\w+)\s+(\d+)\s*$""")
    return file
        .readLines()
        .mapNotNull { regex.find(it.trim()) }
        .associate { it.groupValues[1] to it.groupValues[2].toInt() }
  }

  private fun readMoveNames(): Map<String, String> {
    val file = File(rootDir, "src/data/text/move_names.h")
    require(file.exists()) { "Missing move names table at ${file.path}" }
    val regex = Regex("""\[(MOVE_\w+)\]\s*=\s*_\("([^"]*)"\)""")
    return regex.findAll(file.readText()).associate { it.groupValues[1] to it.groupValues[2] }
  }

  private fun readBattleMoves(): Map<String, Map<String, String>> {
    val file = File(rootDir, "src/data/battle_moves.h")
    require(file.exists()) { "Missing battle move data at ${file.path}" }
    val entry = Regex("""\[(MOVE_\w+)\]\s*=\s*\{(.*?)\}""", RegexOption.DOT_MATCHES_ALL)
    val field = Regex("""\.(\w+)\s*=\s*([^,\n]+?)\s*,""")
    return entry.findAll(file.readText()).associate { match ->
      val constant = match.groupValues[1]
      val fields =
          field.findAll(match.groupValues[2]).associate {
            it.groupValues[1] to it.groupValues[2].trim()
          }
      constant to fields
    }
  }
}
