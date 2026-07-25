package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SafariZone_Northwest
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SafariZone_Northwest_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SafariZone_Northwest.Man)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM_SOLAR_BEAM
 * end
 * ```
 */
internal object SafariZone_Northwest_EventScript_ItemTMSolarBeam : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SafariZone_Northwest_EventScript_ItemTMSolarBeam")
}

internal val SafariZone_NorthwestScripts: Map<String, Script> =
    mapOf(
        "SafariZone_Northwest_EventScript_Man" to SafariZone_Northwest_EventScript_Man,
        "SafariZone_Northwest_EventScript_ItemTMSolarBeam" to
            SafariZone_Northwest_EventScript_ItemTMSolarBeam,
    )
