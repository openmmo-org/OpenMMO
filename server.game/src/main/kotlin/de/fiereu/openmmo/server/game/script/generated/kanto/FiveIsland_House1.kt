package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FiveIsland_House1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FiveIsland_House1_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FiveIsland_House1.HusbandWentOffFishing)
}

internal val FiveIsland_House1Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_House1_EventScript_Woman" to FiveIsland_House1_EventScript_Woman,
    )
