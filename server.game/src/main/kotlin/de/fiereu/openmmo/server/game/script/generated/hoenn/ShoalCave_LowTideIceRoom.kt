package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM_HAIL
 * end
 * ```
 */
internal object ShoalCave_LowTideIceRoom_EventScript_ItemTMHail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideIceRoom_EventScript_ItemTMHail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NEVER_MELT_ICE
 * end
 * ```
 */
internal object ShoalCave_LowTideIceRoom_EventScript_ItemNeverMeltIce : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideIceRoom_EventScript_ItemNeverMeltIce")
}

internal val ShoalCave_LowTideIceRoomScripts: Map<String, Script> =
    mapOf(
        "ShoalCave_LowTideIceRoom_EventScript_ItemTMHail" to
            ShoalCave_LowTideIceRoom_EventScript_ItemTMHail,
        "ShoalCave_LowTideIceRoom_EventScript_ItemNeverMeltIce" to
            ShoalCave_LowTideIceRoom_EventScript_ItemNeverMeltIce,
    )
