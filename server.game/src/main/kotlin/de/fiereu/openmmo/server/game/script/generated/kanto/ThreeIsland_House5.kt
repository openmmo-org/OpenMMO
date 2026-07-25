package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RESCUED_LOSTELLE, ThreeIsland_House5_EventScript_LittleGirlLostelleFound
 * msgbox ThreeIsland_House5_Text_ImNotLostelle
 * release
 * end
 * ```
 */
internal object ThreeIsland_House5_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ThreeIsland_House5_EventScript_LittleGirl")
}

internal val ThreeIsland_House5Scripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_House5_EventScript_LittleGirl" to ThreeIsland_House5_EventScript_LittleGirl,
    )
