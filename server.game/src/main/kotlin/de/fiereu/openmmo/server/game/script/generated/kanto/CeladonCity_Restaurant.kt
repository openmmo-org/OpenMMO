package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_Restaurant
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeladonCity_Restaurant_EventScript_Chef : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Restaurant.TakingBreakRightNow)
}

internal object CeladonCity_Restaurant_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Restaurant.OftenGoToDrugstore)
}

internal object CeladonCity_Restaurant_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Restaurant.ManLostItAllAtSlots)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_COIN_CASE, CeladonCity_Restaurant_EventScript_AlreadyGotCoinCase
 * msgbox CeladonCity_Restaurant_Text_TakeThisImBusted
 * checkitemspace ITEM_COIN_CASE
 * goto_if_eq VAR_RESULT, FALSE, CeladonCity_Restaurant_EventScript_NoRoomForCoinCase
 * giveitem_msg CeladonCity_Restaurant_Text_ReceivedCoinCaseFromMan, ITEM_COIN_CASE
 * setflag FLAG_GOT_COIN_CASE
 * release
 * end
 * ```
 */
internal object CeladonCity_Restaurant_EventScript_CoinCaseMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_Restaurant_EventScript_CoinCaseMan")
}

internal object CeladonCity_Restaurant_EventScript_WorkerM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_Restaurant.PsstBasementUnderGameCorner)
}

internal val CeladonCity_RestaurantScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_Restaurant_EventScript_Chef" to CeladonCity_Restaurant_EventScript_Chef,
        "CeladonCity_Restaurant_EventScript_Woman" to CeladonCity_Restaurant_EventScript_Woman,
        "CeladonCity_Restaurant_EventScript_FatMan" to CeladonCity_Restaurant_EventScript_FatMan,
        "CeladonCity_Restaurant_EventScript_CoinCaseMan" to
            CeladonCity_Restaurant_EventScript_CoinCaseMan,
        "CeladonCity_Restaurant_EventScript_WorkerM" to CeladonCity_Restaurant_EventScript_WorkerM,
    )
