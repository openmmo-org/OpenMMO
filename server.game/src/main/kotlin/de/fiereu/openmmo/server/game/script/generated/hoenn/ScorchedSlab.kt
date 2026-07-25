package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM_SUNNY_DAY
 * end
 * ```
 */
internal object ScorchedSlab_EventScript_ItemTMSunnyDay : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ScorchedSlab_EventScript_ItemTMSunnyDay")
}

internal val ScorchedSlabScripts: Map<String, Script> =
    mapOf(
        "ScorchedSlab_EventScript_ItemTMSunnyDay" to ScorchedSlab_EventScript_ItemTMSunnyDay,
    )
