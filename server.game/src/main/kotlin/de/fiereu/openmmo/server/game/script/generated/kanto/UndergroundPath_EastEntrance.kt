package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.UndergroundPath_EastEntrance
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object UndergroundPath_EastEntrance_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(UndergroundPath_EastEntrance.DoYouGoToCeladonDeptStore)
}

internal val UndergroundPath_EastEntranceScripts: Map<String, Script> =
    mapOf(
        "UndergroundPath_EastEntrance_EventScript_Woman" to
            UndergroundPath_EastEntrance_EventScript_Woman,
    )
