package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SilphCo_1F_EventScript_Receptionist : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SilphCo_1F.WelcomePresidentInBoardroom)
}

internal object SilphCo_1F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_1F.FloorSign)
}

internal val SilphCo_1FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_1F_EventScript_Receptionist" to SilphCo_1F_EventScript_Receptionist,
        "SilphCo_1F_EventScript_FloorSign" to SilphCo_1F_EventScript_FloorSign,
    )
