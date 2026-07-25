package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PacifidlogTown_House3
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object PacifidlogTown_House3_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PacifidlogTown_House3.IsThatAPokedex)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_PACIFIDLOG_NPC_TRADE_COMPLETED, PacifidlogTown_House3_EventScript_TradeCompleted
 * setvar VAR_0x8008, INGAME_TRADE_HORSEA
 * copyvar VAR_0x8004, VAR_0x8008
 * specialvar VAR_RESULT, GetInGameTradeSpeciesInfo
 * copyvar VAR_0x8009, VAR_RESULT
 * msgbox PacifidlogTown_House3_Text_WillingToTradeIt, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, PacifidlogTown_House3_EventScript_DeclineTrade
 * special ChoosePartyMon
 * copyvar VAR_0x800A, VAR_0x8004
 * goto_if_eq VAR_0x8004, PARTY_NOTHING_CHOSEN, PacifidlogTown_House3_EventScript_DeclineTrade
 * copyvar VAR_0x8005, VAR_0x800A
 * specialvar VAR_RESULT, GetTradeSpecies
 * copyvar VAR_0x800B, VAR_RESULT
 * goto_if_ne VAR_RESULT, VAR_0x8009, PacifidlogTown_House3_EventScript_NotRequestedMon
 * copyvar VAR_0x8004, VAR_0x8008
 * copyvar VAR_0x8005, VAR_0x800A
 * special CreateInGameTradePokemon
 * special DoInGameTradeScene
 * bufferspeciesname STR_VAR_1, VAR_0x8009
 * msgbox PacifidlogTown_House3_Text_ItsSubtlyDifferentThankYou, MSGBOX_DEFAULT
 * setflag FLAG_PACIFIDLOG_NPC_TRADE_COMPLETED
 * release
 * end
 * ```
 */
internal object PacifidlogTown_House3_EventScript_Trader : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PacifidlogTown_House3_EventScript_Trader")
}

internal val PacifidlogTown_House3Scripts: Map<String, Script> =
    mapOf(
        "PacifidlogTown_House3_EventScript_Girl" to PacifidlogTown_House3_EventScript_Girl,
        "PacifidlogTown_House3_EventScript_Trader" to PacifidlogTown_House3_EventScript_Trader,
    )
