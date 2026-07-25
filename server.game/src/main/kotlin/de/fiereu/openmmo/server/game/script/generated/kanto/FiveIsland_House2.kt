package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FiveIsland_House2
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FiveIsland_House2_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FiveIsland_House2.MeadowBelongedToFamily)
}

internal val FiveIsland_House2Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_House2_EventScript_OldMan" to FiveIsland_House2_EventScript_OldMan,
    )
