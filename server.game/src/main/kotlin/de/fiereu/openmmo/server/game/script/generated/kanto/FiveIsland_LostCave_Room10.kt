package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SILK_SCARF
 * end
 * ```
 */
internal object FiveIsland_LostCave_Room10_EventScript_ItemSilkScarf : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_LostCave_Room10_EventScript_ItemSilkScarf")
}

internal val FiveIsland_LostCave_Room10Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_LostCave_Room10_EventScript_ItemSilkScarf" to
            FiveIsland_LostCave_Room10_EventScript_ItemSilkScarf,
    )
