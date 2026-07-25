package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.FortreeCity_House1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_FORTREE_NPC_TRADE_COMPLETED, FortreeCity_House1_EventScript_TradeCompleted
 * setvar VAR_0x8008, INGAME_TRADE_PLUSLE
 * copyvar VAR_0x8004, VAR_0x8008
 * specialvar VAR_RESULT, GetInGameTradeSpeciesInfo
 * copyvar VAR_0x8009, VAR_RESULT
 * msgbox FortreeCity_House1_Text_YouWillTradeWontYou, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, FortreeCity_House1_EventScript_DeclineTrade
 * special ChoosePartyMon
 * copyvar VAR_0x800A, VAR_0x8004
 * goto_if_eq VAR_0x8004, PARTY_NOTHING_CHOSEN, FortreeCity_House1_EventScript_DeclineTrade
 * copyvar VAR_0x8005, VAR_0x800A
 * specialvar VAR_RESULT, GetTradeSpecies
 * copyvar VAR_0x800B, VAR_RESULT
 * goto_if_ne VAR_RESULT, VAR_0x8009, FortreeCity_House1_EventScript_NotRequestedMon
 * copyvar VAR_0x8004, VAR_0x8008
 * copyvar VAR_0x8005, VAR_0x800A
 * special CreateInGameTradePokemon
 * special DoInGameTradeScene
 * bufferspeciesname STR_VAR_1, VAR_0x8009
 * msgbox FortreeCity_House1_Text_MonYouTakeCare, MSGBOX_DEFAULT
 * setflag FLAG_FORTREE_NPC_TRADE_COMPLETED
 * release
 * end
 * ```
 */
internal object FortreeCity_House1_EventScript_Trader : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_House1_EventScript_Trader")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_ZIGZAGOON, CRY_MODE_NORMAL
 * msgbox FortreeCity_House1_Text_Zigzagoon, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object FortreeCity_House1_EventScript_Zigzagoon : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FortreeCity_House1_EventScript_Zigzagoon")
}

internal object FortreeCity_House1_EventScript_ExpertF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FortreeCity_House1.TradingMemoriesWithOthers)
}

internal val FortreeCity_House1Scripts: Map<String, Script> =
    mapOf(
        "FortreeCity_House1_EventScript_Trader" to FortreeCity_House1_EventScript_Trader,
        "FortreeCity_House1_EventScript_Zigzagoon" to FortreeCity_House1_EventScript_Zigzagoon,
        "FortreeCity_House1_EventScript_ExpertF" to FortreeCity_House1_EventScript_ExpertF,
    )
