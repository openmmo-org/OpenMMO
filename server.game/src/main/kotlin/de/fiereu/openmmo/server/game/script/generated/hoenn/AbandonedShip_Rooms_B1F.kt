package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.AbandonedShip_Rooms_B1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object AbandonedShip_Rooms_B1F_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(AbandonedShip_Rooms_B1F.GettingQueasy)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ESCAPE_ROPE
 * end
 * ```
 */
internal object AbandonedShip_Rooms_B1F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_Rooms_B1F_EventScript_ItemEscapeRope")
}

internal val AbandonedShip_Rooms_B1FScripts: Map<String, Script> =
    mapOf(
        "AbandonedShip_Rooms_B1F_EventScript_FatMan" to AbandonedShip_Rooms_B1F_EventScript_FatMan,
        "AbandonedShip_Rooms_B1F_EventScript_ItemEscapeRope" to
            AbandonedShip_Rooms_B1F_EventScript_ItemEscapeRope,
    )
