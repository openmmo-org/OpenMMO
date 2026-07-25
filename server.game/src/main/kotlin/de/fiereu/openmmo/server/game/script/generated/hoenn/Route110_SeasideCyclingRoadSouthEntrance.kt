package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route110_SeasideCyclingRoadSouthEntrance
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route110_SeasideCyclingRoadSouthEntrance_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route110_SeasideCyclingRoadSouthEntrance.GoAllOutOnCyclingRoad)
}

internal val Route110_SeasideCyclingRoadSouthEntranceScripts: Map<String, Script> =
    mapOf(
        "Route110_SeasideCyclingRoadSouthEntrance_EventScript_Clerk" to
            Route110_SeasideCyclingRoadSouthEntrance_EventScript_Clerk,
    )
