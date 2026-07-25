package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_1F_Room1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SSAnne_1F_Room1_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_1F_Room1.ImAGlobalPoliceAgent)
}

internal val SSAnne_1F_Room1Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_1F_Room1_EventScript_Gentleman" to SSAnne_1F_Room1_EventScript_Gentleman,
    )
