package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SlateportCity_NameRatersHouse_Text_PleasedToRateMonNickname, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, SlateportCity_NameRatersHouse_EventScript_ChooseMonToRate
 * goto_if_eq VAR_RESULT, NO, SlateportCity_NameRatersHouse_EventScript_DeclineNameRate
 * end
 * ```
 */
internal object SlateportCity_NameRatersHouse_EventScript_NameRater : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_NameRatersHouse_EventScript_NameRater")
}

internal val SlateportCity_NameRatersHouseScripts: Map<String, Script> =
    mapOf(
        "SlateportCity_NameRatersHouse_EventScript_NameRater" to
            SlateportCity_NameRatersHouse_EventScript_NameRater,
    )
