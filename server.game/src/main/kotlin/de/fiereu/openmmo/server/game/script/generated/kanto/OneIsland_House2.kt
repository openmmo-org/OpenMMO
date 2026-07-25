package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.OneIsland_House2
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object OneIsland_House2_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(OneIsland_House2.IWantToStayHereForever)
}

internal val OneIsland_House2Scripts: Map<String, Script> =
    mapOf(
        "OneIsland_House2_EventScript_Lass" to OneIsland_House2_EventScript_Lass,
    )
