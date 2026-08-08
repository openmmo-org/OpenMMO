package de.fiereu.openmmo.codegen.trainer

object RenderUtil {

  fun trainer(region: String, t: ParsedTrainer): String {
    val party = t.party.joinToString(", ", "listOf(", ")") { mon(it) }
    return "reg.register(Region.${region.uppercase()}, TrainerDef(${t.id}, \"${escape(t.name)}\", " +
        "${t.trainerClass}, ${t.doubleBattle}, $party))"
  }

  private fun mon(m: ParsedTrainerMon): String {
    val moves = m.moveIds.joinToString(", ", "listOf(", ")")
    return "TrainerMon(${m.dexId}, ${m.level}, ${m.iv}, ${m.heldItem}, $moves)"
  }

  private fun escape(name: String) = name.replace("\\", "\\\\").replace("\"", "\\\"")
}
