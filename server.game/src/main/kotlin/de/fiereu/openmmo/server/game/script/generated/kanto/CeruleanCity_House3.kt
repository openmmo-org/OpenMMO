package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeruleanCity_House3
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8008, INGAME_TRADE_JYNX
 * call EventScript_GetInGameTradeSpeciesInfo
 * goto_if_set FLAG_DID_ZYNX_TRADE, CeruleanCity_House3_EventScript_AlreadyTraded
 * msgbox Trade_Text_DoYouHaveMonWouldYouTradeForMon, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, CeruleanCity_House3_EventScript_DeclineTrade
 * call EventScript_ChooseMonForInGameTrade
 * goto_if_ge VAR_0x8004, PARTY_SIZE, CeruleanCity_House3_EventScript_DeclineTrade
 * call EventScript_GetInGameTradeSpecies
 * goto_if_ne VAR_RESULT, VAR_0x8009, CeruleanCity_House3_EventScript_NotRequestedMon
 * call EventScript_DoInGameTrade
 * msgbox Trade_Text_Thanks
 * setflag FLAG_DID_ZYNX_TRADE
 * release
 * end
 * ```
 */
internal object CeruleanCity_House3_EventScript_Dontae : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_House3_EventScript_Dontae")
}

internal object CeruleanCity_House3_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeruleanCity_House3.PleaseTradeWithMyHusband)
}

internal val CeruleanCity_House3Scripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_House3_EventScript_Dontae" to CeruleanCity_House3_EventScript_Dontae,
        "CeruleanCity_House3_EventScript_OldWoman" to CeruleanCity_House3_EventScript_OldWoman,
    )
