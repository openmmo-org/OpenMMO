package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SEA_INCENSE
 * end
 * ```
 */
internal object FiveIsland_LostCave_Room12_EventScript_ItemSeaIncense : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_LostCave_Room12_EventScript_ItemSeaIncense")
}

internal val FiveIsland_LostCave_Room12Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_LostCave_Room12_EventScript_ItemSeaIncense" to
            FiveIsland_LostCave_Room12_EventScript_ItemSeaIncense,
    )
