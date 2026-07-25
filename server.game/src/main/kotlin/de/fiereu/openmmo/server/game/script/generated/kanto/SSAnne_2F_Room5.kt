package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_2F_Room5
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SSAnne_2F_Room5_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_2F_Room5.HaveYouGoneToSafariZone)
}

internal object SSAnne_2F_Room5_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_2F_Room5.WeThinkSafariZoneIsAwesome)
}

internal val SSAnne_2F_Room5Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_2F_Room5_EventScript_Gentleman" to SSAnne_2F_Room5_EventScript_Gentleman,
        "SSAnne_2F_Room5_EventScript_LittleBoy" to SSAnne_2F_Room5_EventScript_LittleBoy,
    )
