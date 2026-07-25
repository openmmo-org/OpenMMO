package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.UndergroundPath_SouthEntrance
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object UndergroundPath_SouthEntrance_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(UndergroundPath_SouthEntrance.PeopleLoseThingsInTheDarkness)
}

internal val UndergroundPath_SouthEntranceScripts: Map<String, Script> =
    mapOf(
        "UndergroundPath_SouthEntrance_EventScript_Woman" to
            UndergroundPath_SouthEntrance_EventScript_Woman,
    )
