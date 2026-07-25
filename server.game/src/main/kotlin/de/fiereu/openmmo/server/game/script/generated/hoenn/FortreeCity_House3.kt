package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.FortreeCity_House3
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FortreeCity_House3_EventScript_Maniac : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FortreeCity_House3.MetStevenHadAmazingPokemon)
}

internal object FortreeCity_House3_EventScript_SchoolKidM : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FortreeCity_House3.OhYouHavePokedex)
}

internal val FortreeCity_House3Scripts: Map<String, Script> =
    mapOf(
        "FortreeCity_House3_EventScript_Maniac" to FortreeCity_House3_EventScript_Maniac,
        "FortreeCity_House3_EventScript_SchoolKidM" to FortreeCity_House3_EventScript_SchoolKidM,
    )
