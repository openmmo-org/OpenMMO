package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route15_WestEntrance_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route15_WestEntrance_1F_EventScript_Guard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route15_WestEntrance_1F.OaksAideCameByHere)
}

internal val Route15_WestEntrance_1FScripts: Map<String, Script> =
    mapOf(
        "Route15_WestEntrance_1F_EventScript_Guard" to Route15_WestEntrance_1F_EventScript_Guard,
    )
