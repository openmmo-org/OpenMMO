package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_2F_Room3
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SSAnne_2F_Room3_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_2F_Room3.SeenMonsFerryPeople)
}

internal object SSAnne_2F_Room3_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_2F_Room3.SomeTreesCanBeCutDown)
}

internal val SSAnne_2F_Room3Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_2F_Room3_EventScript_Gentleman" to SSAnne_2F_Room3_EventScript_Gentleman,
        "SSAnne_2F_Room3_EventScript_OldMan" to SSAnne_2F_Room3_EventScript_OldMan,
    )
