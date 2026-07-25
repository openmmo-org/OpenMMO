package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM_DRAGON_CLAW
 * end
 * ```
 */
internal object MeteorFalls_B1F_2R_EventScript_ItemTMDragonClaw : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MeteorFalls_B1F_2R_EventScript_ItemTMDragonClaw")
}

internal val MeteorFalls_B1F_2RScripts: Map<String, Script> =
    mapOf(
        "MeteorFalls_B1F_2R_EventScript_ItemTMDragonClaw" to
            MeteorFalls_B1F_2R_EventScript_ItemTMDragonClaw,
    )
