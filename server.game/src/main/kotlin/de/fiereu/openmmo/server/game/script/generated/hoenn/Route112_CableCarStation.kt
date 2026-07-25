package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route112_CableCarStation_Text_CableCarReadyGetOn, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, Route112_CableCarStation_EventScript_RideCableCar
 * goto_if_eq VAR_RESULT, NO, Route112_CableCarStation_EventScript_DeclineRide
 * end
 * ```
 */
internal object Route112_CableCarStation_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route112_CableCarStation_EventScript_Attendant")
}

internal val Route112_CableCarStationScripts: Map<String, Script> =
    mapOf(
        "Route112_CableCarStation_EventScript_Attendant" to
            Route112_CableCarStation_EventScript_Attendant,
    )
