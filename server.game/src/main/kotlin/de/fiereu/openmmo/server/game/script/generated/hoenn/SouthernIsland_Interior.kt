package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8008, 12  @ Player's Y coordinate. Not read
 * goto SouthernIsland_Interior_EventScript_Lati
 * end
 * ```
 */
internal object SouthernIsland_Interior_EventScript_TryLatiEncounter : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SouthernIsland_Interior_EventScript_TryLatiEncounter")
}

internal val SouthernIsland_InteriorScripts: Map<String, Script> =
    mapOf(
        "SouthernIsland_Interior_EventScript_TryLatiEncounter" to
            SouthernIsland_Interior_EventScript_TryLatiEncounter,
    )
