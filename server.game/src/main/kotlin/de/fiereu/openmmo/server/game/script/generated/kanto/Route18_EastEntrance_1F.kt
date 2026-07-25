package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route18_EastEntrance_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route18_EastEntrance_1F_EventScript_Guard : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route18_EastEntrance_1F.CyclingRoadAllUphillFromHere)
}

internal val Route18_EastEntrance_1FScripts: Map<String, Script> =
    mapOf(
        "Route18_EastEntrance_1F_EventScript_Guard" to Route18_EastEntrance_1F_EventScript_Guard,
    )
