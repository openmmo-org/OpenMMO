package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RARE_CANDY
 * end
 * ```
 */
internal object FiveIsland_LostCave_Room14_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_LostCave_Room14_EventScript_ItemRareCandy")
}

internal val FiveIsland_LostCave_Room14Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_LostCave_Room14_EventScript_ItemRareCandy" to
            FiveIsland_LostCave_Room14_EventScript_ItemRareCandy,
    )
