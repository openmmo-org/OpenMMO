package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM_ICE_BEAM
 * end
 * ```
 */
internal object AbandonedShip_Room_B1F_EventScript_ItemTMIceBeam : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_Room_B1F_EventScript_ItemTMIceBeam")
}

internal val AbandonedShip_Room_B1FScripts: Map<String, Script> =
    mapOf(
        "AbandonedShip_Room_B1F_EventScript_ItemTMIceBeam" to
            AbandonedShip_Room_B1F_EventScript_ItemTMIceBeam,
    )
