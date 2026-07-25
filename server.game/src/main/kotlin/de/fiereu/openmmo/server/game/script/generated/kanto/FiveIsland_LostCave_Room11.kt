package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_LAX_INCENSE
 * end
 * ```
 */
internal object FiveIsland_LostCave_Room11_EventScript_ItemLaxIncense : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_LostCave_Room11_EventScript_ItemLaxIncense")
}

internal val FiveIsland_LostCave_Room11Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_LostCave_Room11_EventScript_ItemLaxIncense" to
            FiveIsland_LostCave_Room11_EventScript_ItemLaxIncense,
    )
