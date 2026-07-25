package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM_EARTHQUAKE
 * end
 * ```
 */
internal object SeafloorCavern_Room9_EventScript_ItemTMEarthquake : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafloorCavern_Room9_EventScript_ItemTMEarthquake")
}

internal val SeafloorCavern_Room9Scripts: Map<String, Script> =
    mapOf(
        "SeafloorCavern_Room9_EventScript_ItemTMEarthquake" to
            SeafloorCavern_Room9_EventScript_ItemTMEarthquake,
    )
