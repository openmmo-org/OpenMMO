package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_REVIVE
 * end
 * ```
 */
internal object FiveIsland_LostCave_Room13_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_LostCave_Room13_EventScript_ItemMaxRevive")
}

internal val FiveIsland_LostCave_Room13Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_LostCave_Room13_EventScript_ItemMaxRevive" to
            FiveIsland_LostCave_Room13_EventScript_ItemMaxRevive,
    )
