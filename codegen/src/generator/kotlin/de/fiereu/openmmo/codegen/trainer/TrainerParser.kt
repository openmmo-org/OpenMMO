package de.fiereu.openmmo.codegen.trainer

import de.fiereu.openmmo.codegen.defineTable
import de.fiereu.openmmo.codegen.pokemon.NationalDex
import de.fiereu.openmmo.codegen.pokemon.SpeciesParser
import de.fiereu.openmmo.common.enums.MAX_IV
import java.io.File

class TrainerParser(private val rootDir: File) {

  private val trainerIds = defines("include/constants/opponents.h", "TRAINER_")
  private val classIds = defines("include/constants/trainers.h", "TRAINER_CLASS_")
  private val speciesIds = defines("include/constants/species.h", "SPECIES_")
  private val moveIds = defines("include/constants/moves.h", "MOVE_")
  private val itemIds = defines("include/constants/items.h", "ITEM_")
  private val nationalDex = NationalDex.read(rootDir)
  private val knownDexIds = SpeciesParser(rootDir).parseAll().mapTo(HashSet()) { it.id }

  fun parseAll(): List<ParsedTrainer> {
    val parties = readParties()
    return readTrainers()
        .mapNotNull { entry ->
          val id = trainerIds[entry.constant]
          if (id == null) {
            println("[trainer] skipped ${entry.constant}, it has no id in opponents.h")
            return@mapNotNull null
          }
          val party = parties[entry.partySymbol]?.mapNotNull(::toMon).orEmpty()
          // A trainer whose party stopped parsing would otherwise vanish without a trace.
          if (party.isEmpty()) {
            println(
                "[trainer] skipped ${entry.constant}, ${entry.partySymbol} has no usable members")
            return@mapNotNull null
          }
          ParsedTrainer(
              id = id,
              name = entry.name,
              trainerClass = classIds[entry.trainerClass] ?: 0,
              doubleBattle = entry.doubleBattle,
              party = party,
          )
        }
        .sortedBy { it.id }
  }

  private fun toMon(fields: Map<String, String>): ParsedTrainerMon? {
    val dexId = speciesIds[fields["species"]]?.let { nationalDex[it] } ?: return null
    if (dexId !in knownDexIds) return null
    val level = fields["lvl"]?.toIntOrNull() ?: return null
    val moves =
        fields["moves"]
            ?.let { MOVE.findAll(it).mapNotNull { m -> moveIds[m.value] }.toList() }
            ?.filter { it != 0 }
            .orEmpty()
    // The decomp stores a 0 to 255 difficulty here and scales it to a real IV when it builds the
    // party, so scale it once here rather than leaving a 255 that looks like an IV but is not.
    val difficulty = fields["iv"]?.toIntOrNull() ?: 0
    return ParsedTrainerMon(
        dexId = dexId,
        level = level,
        iv = difficulty * MAX_IV / 255,
        heldItem = itemIds[fields["heldItem"]] ?: 0,
        moveIds = moves,
    )
  }

  private data class TrainerEntry(
      val constant: String,
      val name: String,
      val trainerClass: String,
      val doubleBattle: Boolean,
      val partySymbol: String,
  )

  // [TRAINER_X] = { .trainerClass = ..., .trainerName = _("NAME"), .party = MACRO(sParty_X), }
  private fun readTrainers(): List<TrainerEntry> =
      ENTRY.findAll(read("src/data/trainers.h"))
          .mapNotNull { match ->
            val body = match.groupValues[2]
            val party = PARTY.find(body)?.groupValues?.get(1) ?: return@mapNotNull null
            TrainerEntry(
                constant = match.groupValues[1],
                name = NAME.find(body)?.groupValues?.get(1).orEmpty(),
                trainerClass = CLASS.find(body)?.groupValues?.get(1).orEmpty(),
                doubleBattle = body.contains(".doubleBattle = TRUE"),
                partySymbol = party,
            )
          }
          .toList()

  /**
   * The party arrays keyed by symbol. The RS placeholder teams are written as bare macro names, so
   * the macro bodies are read first and pasted in before the members are split out.
   */
  private fun readParties(): Map<String, List<Map<String, String>>> {
    val text = read("src/data/trainer_parties.h")
    val macros =
        MACRO.findAll(text).associate { it.groupValues[1] to it.groupValues[2].replace("\\", " ") }
    return ARRAY.findAll(text).associate { match ->
      val body =
          macros.entries.fold(match.groupValues[2]) { acc, (name, b) -> acc.replace(name, b) }
      match.groupValues[1] to MEMBER.findAll(body).map { fields(it.groupValues[1]) }.toList()
    }
  }

  private fun fields(body: String): Map<String, String> =
      FIELD.findAll(body).associate { it.groupValues[1] to it.groupValues[2].trim() }

  private fun defines(path: String, prefix: String): Map<String, Int> =
      defineTable(File(rootDir, path), prefix)

  private fun read(path: String): String {
    val file = File(rootDir, path)
    require(file.exists()) {
      "Decomp not initialized at $rootDir (missing ${file.path}). " +
          "Run: git submodule update --init --recursive"
    }
    return file.readText()
  }

  private companion object {
    val ENTRY = Regex("""\[(TRAINER_\w+)]\s*=\s*\{(.*?)\n\s*},""", RegexOption.DOT_MATCHES_ALL)
    val NAME = Regex("""\.trainerName\s*=\s*_\("([^"]*)"\)""")
    val CLASS = Regex("""\.trainerClass\s*=\s*(TRAINER_CLASS_\w+)""")
    val PARTY = Regex("""\.party\s*=\s*\w+\(\s*(\w+)\s*\)""")
    val MACRO = Regex("""#define\s+(DUMMY_\w+)\s+((?:.*\\\r?\n)*.*)""")
    val ARRAY =
        Regex(
            """static\s+const\s+struct\s+TrainerMon\w+\s+(\w+)\s*\[\s*]\s*=\s*\{(.*?)};""",
            RegexOption.DOT_MATCHES_ALL,
        )
    // One member, allowing the single nested brace list that .moves brings.
    val MEMBER = Regex("""\{((?:[^{}]|\{[^{}]*})*)}""", RegexOption.DOT_MATCHES_ALL)
    val FIELD = Regex("""\.(\w+)\s*=\s*(\{[^}]*}|[^,}]+)""")
    val MOVE = Regex("""MOVE_\w+""")
  }
}
