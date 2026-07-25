package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Underwater_SeafloorCavern
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Underwater_SeafloorCavern_EventScript_CheckStolenSub : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Underwater_SeafloorCavern.SubExplorer1)
}

internal val Underwater_SeafloorCavernScripts: Map<String, Script> =
    mapOf(
        "Underwater_SeafloorCavern_EventScript_CheckStolenSub" to
            Underwater_SeafloorCavern_EventScript_CheckStolenSub,
    )
