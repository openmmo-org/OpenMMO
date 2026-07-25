package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route7
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route7_EventScript_UndergroundPathSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route7.UndergroundPathSign)
}

internal val Route7Scripts: Map<String, Script> =
    mapOf(
        "Route7_EventScript_UndergroundPathSign" to Route7_EventScript_UndergroundPathSign,
    )
