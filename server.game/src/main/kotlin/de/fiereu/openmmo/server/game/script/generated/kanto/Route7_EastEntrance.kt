package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route7_EastEntrance
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route7_EastEntrance_EventScript_Guard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route7_EastEntrance.HiHowsItGoing)
}

internal val Route7_EastEntranceScripts: Map<String, Script> =
    mapOf(
        "Route7_EastEntrance_EventScript_Guard" to Route7_EastEntrance_EventScript_Guard,
    )
