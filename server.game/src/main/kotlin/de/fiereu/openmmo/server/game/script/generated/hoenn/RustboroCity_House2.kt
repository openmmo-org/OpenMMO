package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_House2
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object RustboroCity_House2_EventScript_PokefanF : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RustboroCity_House2.TrainerSchoolExcellent)
}

internal object RustboroCity_House2_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RustboroCity_House2.RoxanneKnowsALot)
}

internal val RustboroCity_House2Scripts: Map<String, Script> =
    mapOf(
        "RustboroCity_House2_EventScript_PokefanF" to RustboroCity_House2_EventScript_PokefanF,
        "RustboroCity_House2_EventScript_LittleGirl" to RustboroCity_House2_EventScript_LittleGirl,
    )
