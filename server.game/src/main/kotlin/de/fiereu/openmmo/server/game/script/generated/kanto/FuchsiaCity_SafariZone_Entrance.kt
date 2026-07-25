package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox FuchsiaCity_SafariZone_Entrance_Text_FirstTimeAtSafariZone, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, FuchsiaCity_SafariZone_Entrance_EventScript_ExplainSafariZone
 * msgbox FuchsiaCity_SafariZone_Entrance_Text_SorryYoureARegularHere
 * release
 * end
 * ```
 */
internal object FuchsiaCity_SafariZone_Entrance_EventScript_InfoAttendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FuchsiaCity_SafariZone_Entrance_EventScript_InfoAttendant")
}

internal val FuchsiaCity_SafariZone_EntranceScripts: Map<String, Script> =
    mapOf(
        "FuchsiaCity_SafariZone_Entrance_EventScript_InfoAttendant" to
            FuchsiaCity_SafariZone_Entrance_EventScript_InfoAttendant,
    )
