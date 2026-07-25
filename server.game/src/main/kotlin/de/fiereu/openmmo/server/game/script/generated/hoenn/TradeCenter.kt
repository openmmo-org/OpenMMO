package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * special Script_FacePlayer
 * msgbox TradeCenter_Text_TakeSeatStartTrade, MSGBOX_DEFAULT
 * special Script_ClearHeldMovement
 * closemessage
 * end
 * ```
 */
internal object TradeCenter_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port TradeCenter_EventScript_Attendant")
}

internal val TradeCenterScripts: Map<String, Script> =
    mapOf(
        "TradeCenter_EventScript_Attendant" to TradeCenter_EventScript_Attendant,
    )
