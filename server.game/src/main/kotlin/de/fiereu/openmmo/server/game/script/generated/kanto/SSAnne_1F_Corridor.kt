package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_1F_Corridor
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SSAnne_1F_Corridor_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_1F_Corridor.PassengersAreRestless)
}

internal object SSAnne_1F_Corridor_EventScript_WorkerM : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_1F_Corridor.LeStrongSilentType)
}

internal val SSAnne_1F_CorridorScripts: Map<String, Script> =
    mapOf(
        "SSAnne_1F_Corridor_EventScript_Sailor" to SSAnne_1F_Corridor_EventScript_Sailor,
        "SSAnne_1F_Corridor_EventScript_WorkerM" to SSAnne_1F_Corridor_EventScript_WorkerM,
    )
