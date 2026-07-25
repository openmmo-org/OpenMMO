package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Misc
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object DiglettsCave_NorthEntrance_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Misc.DiglettsCave_NorthEntrance_RockTunnelPitchBlack)
}

internal val DiglettsCave_NorthEntranceScripts: Map<String, Script> =
    mapOf(
        "DiglettsCave_NorthEntrance_EventScript_Hiker" to
            DiglettsCave_NorthEntrance_EventScript_Hiker,
    )
