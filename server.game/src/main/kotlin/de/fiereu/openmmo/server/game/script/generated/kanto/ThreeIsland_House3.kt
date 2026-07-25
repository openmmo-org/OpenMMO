package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ThreeIsland_House3
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object ThreeIsland_House3_EventScript_Beauty : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ThreeIsland_House3.WantedToLiveSomewhereQuiet)
}

internal val ThreeIsland_House3Scripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_House3_EventScript_Beauty" to ThreeIsland_House3_EventScript_Beauty,
    )
