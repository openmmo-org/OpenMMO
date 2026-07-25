package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_BattlePikeLobby
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_FRONTIER_FACILITY, FRONTIER_FACILITY_PIKE
 * setvar VAR_FRONTIER_BATTLE_MODE, FRONTIER_MODE_SINGLES
 * special SavePlayerParty
 * msgbox BattleFrontier_BattlePikeLobby_Text_WelcomeToBattlePike, MSGBOX_DEFAULT
 * message BattleFrontier_BattlePikeLobby_Text_TakeChallenge
 * waitmessage
 * multichoice 17, 6, MULTI_CHALLENGEINFO, FALSE
 * switch VAR_RESULT
 * case 0, BattleFrontier_BattlePikeLobby_EventScript_TryEnterChallenge
 * case 1, BattleFrontier_BattlePikeLobby_EventScript_ExplainChallenge
 * case 2, BattleFrontier_BattlePikeLobby_EventScript_CancelChallenge
 * case MULTI_B_PRESSED, BattleFrontier_BattlePikeLobby_EventScript_CancelChallenge
 * ```
 */
internal object BattleFrontier_BattlePikeLobby_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattlePikeLobby_EventScript_Attendant")
}

internal object BattleFrontier_BattlePikeLobby_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_BattlePikeLobby.OneRoomAwayFromGoal)
}

internal object BattleFrontier_BattlePikeLobby_EventScript_Twin : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_BattlePikeLobby.NeverHadToBattleTrainer)
}

internal object BattleFrontier_BattlePikeLobby_EventScript_Beauty : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_BattlePikeLobby.ThinkAbilitiesUsefulHere)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * frontier_results FRONTIER_FACILITY_PIKE
 * waitbuttonpress
 * special RemoveRecordsWindow
 * releaseall
 * end
 * ```
 */
internal object BattleFrontier_BattlePikeLobby_EventScript_ShowResults : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattlePikeLobby_EventScript_ShowResults")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox BattleFrontier_BattlePikeLobby_Text_RulesAreListed, MSGBOX_DEFAULT
 * goto BattleFrontier_BattlePikeLobby_EventScript_ReadRulesBoard
 * end
 * ```
 */
internal object BattleFrontier_BattlePikeLobby_EventScript_RulesBoard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattlePikeLobby_EventScript_RulesBoard")
}

internal val BattleFrontier_BattlePikeLobbyScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_BattlePikeLobby_EventScript_Attendant" to
            BattleFrontier_BattlePikeLobby_EventScript_Attendant,
        "BattleFrontier_BattlePikeLobby_EventScript_Hiker" to
            BattleFrontier_BattlePikeLobby_EventScript_Hiker,
        "BattleFrontier_BattlePikeLobby_EventScript_Twin" to
            BattleFrontier_BattlePikeLobby_EventScript_Twin,
        "BattleFrontier_BattlePikeLobby_EventScript_Beauty" to
            BattleFrontier_BattlePikeLobby_EventScript_Beauty,
        "BattleFrontier_BattlePikeLobby_EventScript_ShowResults" to
            BattleFrontier_BattlePikeLobby_EventScript_ShowResults,
        "BattleFrontier_BattlePikeLobby_EventScript_RulesBoard" to
            BattleFrontier_BattlePikeLobby_EventScript_RulesBoard,
    )
