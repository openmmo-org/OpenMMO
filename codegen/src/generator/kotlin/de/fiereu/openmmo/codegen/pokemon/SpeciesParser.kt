package de.fiereu.openmmo.codegen.pokemon

import java.io.File

class SpeciesParser(private val rootDir: File) {

  fun parseAll(): List<ParsedSpecies> {
    val ids = readDefineTable("include/constants/species.h", "SPECIES_")
    val names = readSpeciesNames()
    val items = readDefineTable("include/constants/items.h", "ITEM_")
    val entries = readSpeciesInfo()

    return entries
        .mapNotNull { (constant, f) ->
          val id = ids[constant] ?: return@mapNotNull null
          if (!f.containsKey("baseHP")) return@mapNotNull null
          val types = braceList(f.getValue("types"))
          val eggGroups = braceList(f.getValue("eggGroups"))
          val abilities = braceList(f.getValue("abilities"))
          ParsedSpecies(
              id = id,
              name = names[constant] ?: constant.removePrefix("SPECIES_"),
              baseHp = intOf(f.getValue("baseHP")),
              baseAttack = intOf(f.getValue("baseAttack")),
              baseDefense = intOf(f.getValue("baseDefense")),
              baseSpeed = intOf(f.getValue("baseSpeed")),
              baseSpAttack = intOf(f.getValue("baseSpAttack")),
              baseSpDefense = intOf(f.getValue("baseSpDefense")),
              type1 = typeRef(types[0]),
              type2 = typeRef(types.getOrElse(1) { types[0] }),
              catchRate = intOf(f.getValue("catchRate")),
              expYield = intOf(f.getValue("expYield")),
              evYieldHp = intOf(f.getValue("evYield_HP")),
              evYieldAttack = intOf(f.getValue("evYield_Attack")),
              evYieldDefense = intOf(f.getValue("evYield_Defense")),
              evYieldSpeed = intOf(f.getValue("evYield_Speed")),
              evYieldSpAttack = intOf(f.getValue("evYield_SpAttack")),
              evYieldSpDefense = intOf(f.getValue("evYield_SpDefense")),
              itemCommon = items[f.getValue("itemCommon")] ?: 0,
              itemRare = items[f.getValue("itemRare")] ?: 0,
              genderRatio = genderRatio(f.getValue("genderRatio")),
              eggCycles = intOf(f.getValue("eggCycles")),
              friendship = intOf(f.getValue("friendship")),
              growthRate = growthRef(f.getValue("growthRate")),
              eggGroup1 = eggGroupRef(eggGroups[0]),
              eggGroup2 = eggGroupRef(eggGroups.getOrElse(1) { eggGroups[0] }),
              ability1 = abilityRef(abilities[0]),
              ability2 = abilityRef(abilities.getOrElse(1) { "ABILITY_NONE" }),
              safariZoneFleeRate = intOf(f.getValue("safariZoneFleeRate")),
              bodyColor = bodyColorRef(f.getValue("bodyColor")),
              noFlip = f["noFlip"] == "TRUE",
          )
        }
        .sortedBy { it.id }
  }

  private fun readSpeciesInfo(): Map<String, Map<String, String>> {
    val file = File(rootDir, "src/data/pokemon/species_info.h")
    require(file.exists()) {
      "Decomp not initialized at $rootDir (missing ${file.path}). " +
          "Run: git submodule update --init --recursive"
    }
    val text = file.readText()
    val entryStart = Regex("""\[(SPECIES_\w+)\]\s*=""")
    val field = Regex("""\.(\w+)\s*=\s*(\{[^}]*\}|[^,\n]+?)\s*,""")
    val matches = entryStart.findAll(text).toList()
    return matches
        .mapIndexed { i, m ->
          val end = if (i + 1 < matches.size) matches[i + 1].range.first else text.length
          val body = text.substring(m.range.last + 1, end)
          m.groupValues[1] to
              field.findAll(body).associate { it.groupValues[1] to it.groupValues[2].trim() }
        }
        .toMap()
  }

  private fun readSpeciesNames(): Map<String, String> {
    val file = File(rootDir, "src/data/text/species_names.h")
    require(file.exists()) { "Missing species names table at ${file.path}" }
    val regex = Regex("""\[(SPECIES_\w+)\]\s*=\s*_\("([^"]*)"\)""")
    return regex.findAll(file.readText()).associate { it.groupValues[1] to it.groupValues[2] }
  }

  private fun readDefineTable(path: String, prefix: String): Map<String, Int> {
    val file = File(rootDir, path)
    require(file.exists()) { "Missing $prefix table at ${file.path}" }
    val regex = Regex("""^#define\s+($prefix\w+)\s+(\d+)\s*$""")
    return file
        .readLines()
        .mapNotNull { regex.find(it.trim()) }
        .associate { it.groupValues[1] to it.groupValues[2].toInt() }
  }

  private fun braceList(value: String): List<String> =
      value
          .trim()
          .removePrefix("{")
          .removeSuffix("}")
          .split(",")
          .map { it.trim() }
          .filter { it.isNotEmpty() }

  private fun intOf(token: String): Int =
      when (token) {
        "STANDARD_FRIENDSHIP" -> 70
        "TRUE" -> 1
        "FALSE" -> 0
        else -> token.toIntOrNull() ?: token.removePrefix("0x").toIntOrNull(16) ?: 0
      }

  private fun genderRatio(token: String): Int =
      when {
        token == "MON_MALE" -> 0
        token == "MON_FEMALE" -> 254
        token == "MON_GENDERLESS" -> 255
        token.startsWith("PERCENT_FEMALE(") ->
            minOf(
                254,
                (token.removePrefix("PERCENT_FEMALE(").removeSuffix(")").trim().toDouble() * 255 /
                        100)
                    .toInt())
        else -> intOf(token)
      }
}
