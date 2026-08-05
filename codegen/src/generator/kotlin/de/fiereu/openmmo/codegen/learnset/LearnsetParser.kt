package de.fiereu.openmmo.codegen.learnset

import de.fiereu.openmmo.codegen.defineTable
import de.fiereu.openmmo.codegen.pokemon.NationalDex
import de.fiereu.openmmo.codegen.pokemon.SpeciesParser
import java.io.File

class LearnsetParser(private val rootDir: File) {

  fun parseAll(): List<ParsedLearnset> {
    val speciesIds = readDefineTable("include/constants/species.h", "SPECIES_")
    val nationalDex = NationalDex.read(rootDir)
    val moveIds = readDefineTable("include/constants/moves.h", "MOVE_")
    val tables = readTables()
    // The decomp keeps learnset pointers for placeholder species with no stats, drop those.
    val known = SpeciesParser(rootDir).parseAll().mapTo(HashSet()) { it.id }

    return readPointers()
        .mapNotNull { (constant, symbol) ->
          // Same as the species data, the client keys by national dex.
          val dexId = speciesIds[constant]?.let { nationalDex[it] } ?: return@mapNotNull null
          if (dexId !in known) return@mapNotNull null
          val entries = tables[symbol] ?: return@mapNotNull null
          val moves =
              entries.mapNotNull { (level, move) ->
                moveIds[move]?.let { ParsedLevelUpMove(level, it) }
              }
          if (moves.isEmpty()) null else ParsedLearnset(dexId, moves)
        }
        .sortedBy { it.dexId }
  }

  // static const u16 s<Name>LevelUpLearnset[] = { LEVEL_UP_MOVE(lvl, MOVE_X), ... };
  private fun readTables(): Map<String, List<Pair<Int, String>>> {
    val file = File(rootDir, "src/data/pokemon/level_up_learnsets.h")
    require(file.exists()) {
      "Decomp not initialized at $rootDir (missing ${file.path}). " +
          "Run: git submodule update --init --recursive"
    }
    val table =
        Regex(
            """static\s+const\s+u16\s+(\w+)\s*\[\s*]\s*=\s*\{(.*?)}""",
            RegexOption.DOT_MATCHES_ALL,
        )
    val move = Regex("""LEVEL_UP_MOVE\(\s*(\d+)\s*,\s*(MOVE_\w+)\s*\)""")
    return table.findAll(file.readText()).associate { match ->
      match.groupValues[1] to
          move
              .findAll(match.groupValues[2])
              .map { it.groupValues[1].toInt() to it.groupValues[2] }
              .toList()
    }
  }

  private fun readPointers(): Map<String, String> {
    val file = File(rootDir, "src/data/pokemon/level_up_learnset_pointers.h")
    require(file.exists()) { "Missing learnset pointer table at ${file.path}" }
    val regex = Regex("""\[(SPECIES_\w+)]\s*=\s*(\w+)""")
    return regex.findAll(file.readText()).associate { it.groupValues[1] to it.groupValues[2] }
  }

  private fun readDefineTable(path: String, prefix: String): Map<String, Int> {
    val file = File(rootDir, path)
    require(file.exists()) { "Missing $prefix table at ${file.path}" }
    return defineTable(file, prefix)
  }
}
