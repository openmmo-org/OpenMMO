package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_House1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RUSTBORO_NPC_TRADE_COMPLETED, RustboroCity_House1_EventScript_TradeCompleted
 * setvar VAR_0x8008, INGAME_TRADE_SEEDOT
 * copyvar VAR_0x8004, VAR_0x8008
 * specialvar VAR_RESULT, GetInGameTradeSpeciesInfo
 * copyvar VAR_0x8009, VAR_RESULT
 * msgbox RustboroCity_House1_Text_IllTradeIfYouWant, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, RustboroCity_House1_EventScript_DeclineTrade
 * special ChoosePartyMon
 * copyvar VAR_0x800A, VAR_0x8004
 * goto_if_eq VAR_0x8004, PARTY_NOTHING_CHOSEN, RustboroCity_House1_EventScript_DeclineTrade
 * copyvar VAR_0x8005, VAR_0x800A
 * specialvar VAR_RESULT, GetTradeSpecies
 * copyvar VAR_0x800B, VAR_RESULT
 * goto_if_ne VAR_RESULT, VAR_0x8009, RustboroCity_House1_EventScript_NotRequestedMon
 * copyvar VAR_0x8004, VAR_0x8008
 * copyvar VAR_0x8005, VAR_0x800A
 * special CreateInGameTradePokemon
 * special DoInGameTradeScene
 * msgbox RustboroCity_House1_Text_PleaseBeGoodToMyPokemon, MSGBOX_DEFAULT
 * setflag FLAG_RUSTBORO_NPC_TRADE_COMPLETED
 * release
 * end
 * ```
 */
internal object RustboroCity_House1_EventScript_Trader : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_House1_EventScript_Trader")
}

internal object RustboroCity_House1_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RustboroCity_House1.AllSortsOfPlaces)
}

internal val RustboroCity_House1Scripts: Map<String, Script> =
    mapOf(
        "RustboroCity_House1_EventScript_Trader" to RustboroCity_House1_EventScript_Trader,
        "RustboroCity_House1_EventScript_Hiker" to RustboroCity_House1_EventScript_Hiker,
    )
