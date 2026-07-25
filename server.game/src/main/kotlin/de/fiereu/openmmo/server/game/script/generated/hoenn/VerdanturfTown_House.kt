package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.VerdanturfTown_House
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object VerdanturfTown_House_EventScript_Woman1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VerdanturfTown_House.TrainersGatherAtPokemonLeague)
}

internal object VerdanturfTown_House_EventScript_Woman2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(VerdanturfTown_House.DefeatEliteFourInARow)
}

internal val VerdanturfTown_HouseScripts: Map<String, Script> =
    mapOf(
        "VerdanturfTown_House_EventScript_Woman1" to VerdanturfTown_House_EventScript_Woman1,
        "VerdanturfTown_House_EventScript_Woman2" to VerdanturfTown_House_EventScript_Woman2,
    )
