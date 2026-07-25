package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route2_ViridianForest_SouthEntrance
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route2_ViridianForest_SouthEntrance_EventScript_Woman1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route2_ViridianForest_SouthEntrance.ForestIsMaze)
}

internal object Route2_ViridianForest_SouthEntrance_EventScript_Woman2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route2_ViridianForest_SouthEntrance.RattataHasWickedBite)
}

internal val Route2_ViridianForest_SouthEntranceScripts: Map<String, Script> =
    mapOf(
        "Route2_ViridianForest_SouthEntrance_EventScript_Woman1" to
            Route2_ViridianForest_SouthEntrance_EventScript_Woman1,
        "Route2_ViridianForest_SouthEntrance_EventScript_Woman2" to
            Route2_ViridianForest_SouthEntrance_EventScript_Woman2,
    )
