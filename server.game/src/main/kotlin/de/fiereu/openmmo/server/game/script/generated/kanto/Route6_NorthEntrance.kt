package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route6_NorthEntrance
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route6_NorthEntrance_EventScript_Guard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route6_NorthEntrance.HiHowsItGoing)
}

internal val Route6_NorthEntranceScripts: Map<String, Script> =
    mapOf(
        "Route6_NorthEntrance_EventScript_Guard" to Route6_NorthEntrance_EventScript_Guard,
    )
