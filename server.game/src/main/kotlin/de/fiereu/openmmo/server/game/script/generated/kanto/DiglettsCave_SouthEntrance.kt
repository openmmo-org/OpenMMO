package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.DiglettsCave_SouthEntrance
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object DiglettsCave_SouthEntrance_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(DiglettsCave_SouthEntrance.DiglettDugThisTunnel)
}

internal val DiglettsCave_SouthEntranceScripts: Map<String, Script> =
    mapOf(
        "DiglettsCave_SouthEntrance_EventScript_OldMan" to
            DiglettsCave_SouthEntrance_EventScript_OldMan,
    )
