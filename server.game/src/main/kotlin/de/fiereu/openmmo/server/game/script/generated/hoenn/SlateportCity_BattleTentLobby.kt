package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SlateportCity_BattleTentLobby
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * slateporttent_getprize
 * goto_if_ne VAR_RESULT, ITEM_NONE, SlateportCity_BattleTentLobby_EventScript_GivePrize
 * special SavePlayerParty
 * msgbox SlateportCity_BattleTentLobby_Text_WelcomeToBattleTent, MSGBOX_DEFAULT
 * message SlateportCity_BattleTentLobby_Text_TakeChallenge
 * waitmessage
 * multichoice 17, 6, MULTI_CHALLENGEINFO, FALSE
 * switch VAR_RESULT
 * case 0, SlateportCity_BattleTentLobby_EventScript_TryEnterChallenge
 * case 1, SlateportCity_BattleTentLobby_EventScript_ExplainChallenge
 * case 2, SlateportCity_BattleTentLobby_EventScript_CancelChallenge
 * case MULTI_B_PRESSED, SlateportCity_BattleTentLobby_EventScript_CancelChallenge
 * ```
 */
internal object SlateportCity_BattleTentLobby_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_BattleTentLobby_EventScript_Attendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_TORMENT, SlateportCity_BattleTentLobby_EventScript_ReceivedTorment
 * msgbox SlateportCity_BattleTentLobby_Text_CouldntFindMonForMe, MSGBOX_DEFAULT
 * giveitem ITEM_TM_TORMENT
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_TM_TORMENT
 * msgbox SlateportCity_BattleTentLobby_Text_ExplainTorment, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_BattleTentLobby_EventScript_TormentGiver : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_BattleTentLobby_EventScript_TormentGiver")
}

internal object SlateportCity_BattleTentLobby_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_BattleTentLobby.IllTryUsingBugMons)
}

internal object SlateportCity_BattleTentLobby_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_BattleTentLobby.BattleEvenWithoutToughMons)
}

internal object SlateportCity_BattleTentLobby_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_BattleTentLobby.NiceIfMoreSelection)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox BattleFrontier_BattleFactoryLobby_Text_RulesAreListed, MSGBOX_DEFAULT
 * goto SlateportCity_BattleTentLobby_EventScript_ReadRulesBoard
 * end
 * ```
 */
internal object SlateportCity_BattleTentLobby_EventScript_RulesBoard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_BattleTentLobby_EventScript_RulesBoard")
}

internal val SlateportCity_BattleTentLobbyScripts: Map<String, Script> =
    mapOf(
        "SlateportCity_BattleTentLobby_EventScript_Attendant" to
            SlateportCity_BattleTentLobby_EventScript_Attendant,
        "SlateportCity_BattleTentLobby_EventScript_TormentGiver" to
            SlateportCity_BattleTentLobby_EventScript_TormentGiver,
        "SlateportCity_BattleTentLobby_EventScript_Man" to
            SlateportCity_BattleTentLobby_EventScript_Man,
        "SlateportCity_BattleTentLobby_EventScript_Girl" to
            SlateportCity_BattleTentLobby_EventScript_Girl,
        "SlateportCity_BattleTentLobby_EventScript_Woman" to
            SlateportCity_BattleTentLobby_EventScript_Woman,
        "SlateportCity_BattleTentLobby_EventScript_RulesBoard" to
            SlateportCity_BattleTentLobby_EventScript_RulesBoard,
    )
