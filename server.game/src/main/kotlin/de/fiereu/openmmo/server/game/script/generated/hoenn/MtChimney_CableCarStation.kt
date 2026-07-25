package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox MtChimney_CableCarStation_Text_CableCarReadyGetOn, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, MtChimney_CableCarStation_EventScript_RideCableCar
 * goto_if_eq VAR_RESULT, NO, MtChimney_CableCarStation_EventScript_DeclineRide
 * end
 * ```
 */
internal object MtChimney_CableCarStation_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MtChimney_CableCarStation_EventScript_Attendant")
}

internal val MtChimney_CableCarStationScripts: Map<String, Script> =
    mapOf(
        "MtChimney_CableCarStation_EventScript_Attendant" to
            MtChimney_CableCarStation_EventScript_Attendant,
    )
