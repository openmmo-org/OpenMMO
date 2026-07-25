package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_3F_Corridor
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SSAnne_3F_Corridor_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_3F_Corridor.CaptainTeachesCutToMons)
}

internal val SSAnne_3F_CorridorScripts: Map<String, Script> =
    mapOf(
        "SSAnne_3F_Corridor_EventScript_Sailor" to SSAnne_3F_Corridor_EventScript_Sailor,
    )
