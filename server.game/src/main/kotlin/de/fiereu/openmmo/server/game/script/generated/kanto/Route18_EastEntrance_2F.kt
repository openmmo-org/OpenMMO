package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route18_EastEntrance_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8008, INGAME_TRADE_LICKITUNG
 * call EventScript_GetInGameTradeSpeciesInfo
 * goto_if_set FLAG_DID_MARC_TRADE, Route18_EastEntrance_2F_EventScript_AlreadyTraded
 * msgbox Trade_Text_LookingForMonWannaTradeForMon, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, Route18_EastEntrance_2F_EventScript_DeclineTrade
 * call EventScript_ChooseMonForInGameTrade
 * goto_if_ge VAR_0x8004, PARTY_SIZE, Route18_EastEntrance_2F_EventScript_DeclineTrade
 * call EventScript_GetInGameTradeSpecies
 * goto_if_ne VAR_RESULT, VAR_0x8009, Route18_EastEntrance_2F_EventScript_NotRequestedMon
 * call EventScript_DoInGameTrade
 * msgbox Trade_Text_HeyThanks
 * setflag FLAG_DID_MARC_TRADE
 * release
 * end
 * ```
 */
internal object Route18_EastEntrance_2F_EventScript_Haden : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route18_EastEntrance_2F_EventScript_Haden")
}

internal object Route18_EastEntrance_2F_EventScript_LeftBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route18_EastEntrance_2F.PalletTownInWest)
}

internal object Route18_EastEntrance_2F_EventScript_RightBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route18_EastEntrance_2F.PeopleSwimming)
}

internal val Route18_EastEntrance_2FScripts: Map<String, Script> =
    mapOf(
        "Route18_EastEntrance_2F_EventScript_Haden" to Route18_EastEntrance_2F_EventScript_Haden,
        "Route18_EastEntrance_2F_EventScript_LeftBinoculars" to
            Route18_EastEntrance_2F_EventScript_LeftBinoculars,
        "Route18_EastEntrance_2F_EventScript_RightBinoculars" to
            Route18_EastEntrance_2F_EventScript_RightBinoculars,
    )
