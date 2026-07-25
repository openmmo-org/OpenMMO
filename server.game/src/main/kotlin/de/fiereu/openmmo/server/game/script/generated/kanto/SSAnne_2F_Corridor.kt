package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_2F_Corridor
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SSAnne_2F_Corridor_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_2F_Corridor.ThisShipIsLuxuryLiner)
}

internal val SSAnne_2F_CorridorScripts: Map<String, Script> =
    mapOf(
        "SSAnne_2F_Corridor_EventScript_Sailor" to SSAnne_2F_Corridor_EventScript_Sailor,
    )
