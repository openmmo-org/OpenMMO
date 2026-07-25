package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route16_NorthEntrance_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route16_NorthEntrance_1F_EventScript_Guard : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route16_NorthEntrance_1F.CyclingRoadIsDownhillCourse)
}

internal object Route16_NorthEntrance_1F_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route16_NorthEntrance_1F.HowdYouGetInGoodEffort)
}

internal val Route16_NorthEntrance_1FScripts: Map<String, Script> =
    mapOf(
        "Route16_NorthEntrance_1F_EventScript_Guard" to Route16_NorthEntrance_1F_EventScript_Guard,
        "Route16_NorthEntrance_1F_EventScript_OldMan" to
            Route16_NorthEntrance_1F_EventScript_OldMan,
    )
