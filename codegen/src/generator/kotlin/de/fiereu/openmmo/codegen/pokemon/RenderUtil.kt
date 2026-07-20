package de.fiereu.openmmo.codegen.pokemon

object RenderUtil {

  fun species(s: ParsedSpecies): String =
      "SpeciesDef(id = ${s.id}, name = ${escapeString(s.name)}," +
          " baseHp = ${s.baseHp}, baseAttack = ${s.baseAttack}, baseDefense = ${s.baseDefense}," +
          " baseSpeed = ${s.baseSpeed}, baseSpAttack = ${s.baseSpAttack}," +
          " baseSpDefense = ${s.baseSpDefense}, type1 = ${s.type1}, type2 = ${s.type2}," +
          " catchRate = ${s.catchRate}, expYield = ${s.expYield}," +
          " evYieldHp = ${s.evYieldHp}, evYieldAttack = ${s.evYieldAttack}," +
          " evYieldDefense = ${s.evYieldDefense}, evYieldSpeed = ${s.evYieldSpeed}," +
          " evYieldSpAttack = ${s.evYieldSpAttack}, evYieldSpDefense = ${s.evYieldSpDefense}," +
          " itemCommon = ${s.itemCommon}, itemRare = ${s.itemRare}," +
          " genderRatio = ${s.genderRatio}, eggCycles = ${s.eggCycles}," +
          " friendship = ${s.friendship}, growthRate = ${s.growthRate}," +
          " eggGroup1 = ${s.eggGroup1}, eggGroup2 = ${s.eggGroup2}," +
          " ability1 = ${s.ability1}, ability2 = ${s.ability2}," +
          " safariZoneFleeRate = ${s.safariZoneFleeRate}, bodyColor = ${s.bodyColor}," +
          " noFlip = ${s.noFlip})"

  private fun escapeString(s: String): String =
      "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\""
}
