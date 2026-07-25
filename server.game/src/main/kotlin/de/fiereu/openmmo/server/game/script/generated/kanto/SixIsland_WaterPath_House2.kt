package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SixIsland_WaterPath_House2
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SixIsland_WaterPath_House2_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SixIsland_WaterPath_House2.MyNeighborMeasuredMe)
}

internal val SixIsland_WaterPath_House2Scripts: Map<String, Script> =
    mapOf(
        "SixIsland_WaterPath_House2_EventScript_Man" to SixIsland_WaterPath_House2_EventScript_Man,
    )
