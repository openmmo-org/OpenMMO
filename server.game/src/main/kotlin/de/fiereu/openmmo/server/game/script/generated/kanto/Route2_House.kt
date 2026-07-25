package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route2_House
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route2_House_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route2_House.FaintedMonsCanUseFieldMoves)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8008, INGAME_TRADE_MR_MIME
 * call EventScript_GetInGameTradeSpeciesInfo
 * goto_if_set FLAG_DID_MIMIEN_TRADE, Route2_House_EventScript_AlreadyTraded
 * msgbox Trade_Text_LookingForMonWannaTradeForMon, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, Route2_House_EventScript_DeclineTrade
 * call EventScript_ChooseMonForInGameTrade
 * goto_if_ge VAR_0x8004, PARTY_SIZE, Route2_House_EventScript_DeclineTrade
 * call EventScript_GetInGameTradeSpecies
 * goto_if_ne VAR_RESULT, VAR_0x8009, Route2_House_EventScript_NotRequestedMon
 * call EventScript_DoInGameTrade
 * msgbox Trade_Text_HeyThanks
 * setflag FLAG_DID_MIMIEN_TRADE
 * release
 * end
 * ```
 */
internal object Route2_House_EventScript_Reyley : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route2_House_EventScript_Reyley")
}

internal val Route2_HouseScripts: Map<String, Script> =
    mapOf(
        "Route2_House_EventScript_Scientist" to Route2_House_EventScript_Scientist,
        "Route2_House_EventScript_Reyley" to Route2_House_EventScript_Reyley,
    )
