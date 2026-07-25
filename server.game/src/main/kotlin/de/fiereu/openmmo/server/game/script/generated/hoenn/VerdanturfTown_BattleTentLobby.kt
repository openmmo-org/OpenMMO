package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.VerdanturfTown_BattleTentLobby
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * verdanturftent_getprize
 * goto_if_ne VAR_RESULT, ITEM_NONE, VerdanturfTown_BattleTentLobby_EventScript_PrizeWaiting
 * special SavePlayerParty
 * msgbox VerdanturfTown_BattleTentLobby_Text_WelcomeToBattleTent, MSGBOX_DEFAULT
 * message VerdanturfTown_BattleTentLobby_Text_TakeChallenge
 * waitmessage
 * multichoice 17, 6, MULTI_CHALLENGEINFO, FALSE
 * switch VAR_RESULT
 * case 0, VerdanturfTown_BattleTentLobby_EventScript_TryEnterChallenge
 * case 1, VerdanturfTown_BattleTentLobby_EventScript_ExplainChallenge
 * case 2, VerdanturfTown_BattleTentLobby_EventScript_CancelChallenge
 * case MULTI_B_PRESSED, VerdanturfTown_BattleTentLobby_EventScript_CancelChallenge
 * ```
 */
internal object VerdanturfTown_BattleTentLobby_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VerdanturfTown_BattleTentLobby_EventScript_Attendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_ATTRACT, VerdanturfTown_BattleTentLobby_EventScript_ReceivedAttract
 * msgbox VerdanturfTown_BattleTentLobby_Text_AttractionRunsDeep, MSGBOX_DEFAULT
 * giveitem ITEM_TM_ATTRACT
 * goto_if_eq VAR_RESULT, 0, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_TM_ATTRACT
 * msgbox VerdanturfTown_BattleTentLobby_Text_AttractionMutual, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object VerdanturfTown_BattleTentLobby_EventScript_AttractGiver : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VerdanturfTown_BattleTentLobby_EventScript_AttractGiver")
}

internal object VerdanturfTown_BattleTentLobby_EventScript_Boy1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VerdanturfTown_BattleTentLobby.TaughtWhatKindsOfMoves)
}

internal object VerdanturfTown_BattleTentLobby_EventScript_Boy2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VerdanturfTown_BattleTentLobby.MonsReluctantToUseDislikedMoves)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MET_SCOTT_IN_VERDANTURF, VerdanturfTown_BattleTentLobby_EventScript_ScottAlreadySpokenTo
 * msgbox VerdanturfTown_BattleTentLobby_Text_ScottCanMeetToughTrainers, MSGBOX_DEFAULT
 * addvar VAR_SCOTT_STATE, 1
 * setflag FLAG_MET_SCOTT_IN_VERDANTURF
 * release
 * end
 * ```
 */
internal object VerdanturfTown_BattleTentLobby_EventScript_Scott : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VerdanturfTown_BattleTentLobby_EventScript_Scott")
}

internal object VerdanturfTown_BattleTentLobby_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VerdanturfTown_BattleTentLobby.GentleMonsScaryIfAngry)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox VerdanturfTown_BattleTentLobby_Text_RulesAreListed, MSGBOX_DEFAULT
 * goto VerdanturfTown_BattleTentLobby_EventScript_ReadRulesBoard
 * end
 * ```
 */
internal object VerdanturfTown_BattleTentLobby_EventScript_RulesBoard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VerdanturfTown_BattleTentLobby_EventScript_RulesBoard")
}

internal val VerdanturfTown_BattleTentLobbyScripts: Map<String, Script> =
    mapOf(
        "VerdanturfTown_BattleTentLobby_EventScript_Attendant" to
            VerdanturfTown_BattleTentLobby_EventScript_Attendant,
        "VerdanturfTown_BattleTentLobby_EventScript_AttractGiver" to
            VerdanturfTown_BattleTentLobby_EventScript_AttractGiver,
        "VerdanturfTown_BattleTentLobby_EventScript_Boy1" to
            VerdanturfTown_BattleTentLobby_EventScript_Boy1,
        "VerdanturfTown_BattleTentLobby_EventScript_Boy2" to
            VerdanturfTown_BattleTentLobby_EventScript_Boy2,
        "VerdanturfTown_BattleTentLobby_EventScript_Scott" to
            VerdanturfTown_BattleTentLobby_EventScript_Scott,
        "VerdanturfTown_BattleTentLobby_EventScript_LittleBoy" to
            VerdanturfTown_BattleTentLobby_EventScript_LittleBoy,
        "VerdanturfTown_BattleTentLobby_EventScript_RulesBoard" to
            VerdanturfTown_BattleTentLobby_EventScript_RulesBoard,
    )
