package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route5
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route5_EventScript_UndergroundPathSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route5.UndergroundPathSign)
}

internal val Route5Scripts: Map<String, Script> =
    mapOf(
        "Route5_EventScript_UndergroundPathSign" to Route5_EventScript_UndergroundPathSign,
    )
