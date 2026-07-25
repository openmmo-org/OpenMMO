package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route8_WestEntrance
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route8_WestEntrance_EventScript_Guard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route8_WestEntrance.HiHowsItGoing)
}

internal val Route8_WestEntranceScripts: Map<String, Script> =
    mapOf(
        "Route8_WestEntrance_EventScript_Guard" to Route8_WestEntrance_EventScript_Guard,
    )
