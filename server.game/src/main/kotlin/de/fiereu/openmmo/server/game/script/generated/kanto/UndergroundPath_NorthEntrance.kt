package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8008, INGAME_TRADE_NIDORAN
 * call EventScript_GetInGameTradeSpeciesInfo
 * goto_if_set FLAG_DID_MS_NIDO_TRADE, UndergroundPath_NorthEntrance_EventScript_AlreadyTraded
 * msgbox Trade_Text_DoYouHaveMonWantToTradeForMon, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, UndergroundPath_NorthEntrance_EventScript_DeclineTrade
 * call EventScript_ChooseMonForInGameTrade
 * goto_if_ge VAR_0x8004, PARTY_SIZE, UndergroundPath_NorthEntrance_EventScript_DeclineTrade
 * call EventScript_GetInGameTradeSpecies
 * goto_if_ne VAR_RESULT, VAR_0x8009, UndergroundPath_NorthEntrance_EventScript_NotRequestedMon
 * call EventScript_DoInGameTrade
 * msgbox Trade_Text_ThanksYoureAPal
 * setflag FLAG_DID_MS_NIDO_TRADE
 * release
 * end
 * ```
 */
internal object UndergroundPath_NorthEntrance_EventScript_Saige : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port UndergroundPath_NorthEntrance_EventScript_Saige")
}

internal val UndergroundPath_NorthEntranceScripts: Map<String, Script> =
    mapOf(
        "UndergroundPath_NorthEntrance_EventScript_Saige" to
            UndergroundPath_NorthEntrance_EventScript_Saige,
    )
