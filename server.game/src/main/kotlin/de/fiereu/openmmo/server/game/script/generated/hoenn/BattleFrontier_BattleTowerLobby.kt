package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_BattleTowerLobby
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_FRONTIER_FACILITY, FRONTIER_FACILITY_TOWER
 * special SavePlayerParty
 * msgbox BattleFrontier_BattleTowerLobby_Text_WelcomSingleBattle, MSGBOX_DEFAULT
 * message BattleFrontier_BattleTowerLobby_Text_TakeSinglesChallenge
 * waitmessage
 * multichoice 17, 6, MULTI_CHALLENGEINFO, FALSE
 * switch VAR_RESULT
 * case 0, BattleFrontier_BattleTowerLobby_EventScript_TryEnterSinglesChallenge
 * case 1, BattleFrontier_BattleTowerLobby_EventScript_ExplainSinglesChallenge
 * case 2, BattleFrontier_BattleTowerLobby_EventScript_CancelChallenge
 * case MULTI_B_PRESSED, BattleFrontier_BattleTowerLobby_EventScript_CancelChallenge
 * ```
 */
internal object BattleFrontier_BattleTowerLobby_EventScript_SinglesAttendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerLobby_EventScript_SinglesAttendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message BattleFrontier_BattleTowerLobby_Text_DescribeFeelingsAboutBattleTower
 * waitmessage
 * multichoice 16, 4, MULTI_BATTLE_TOWER_FEELINGS, FALSE
 * switch VAR_RESULT
 * case 0, BattleFrontier_BattleTowerLobby_EventScript_FeelingsBattleNow
 * case 1, BattleFrontier_BattleTowerLobby_EventScript_FeelingsIWon
 * case 2, BattleFrontier_BattleTowerLobby_EventScript_FeelingsILost
 * case 3, BattleFrontier_BattleTowerLobby_EventScript_FeelingsWontTell
 * case MULTI_B_PRESSED, BattleFrontier_BattleTowerLobby_EventScript_FeelingsWontTell
 * release
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerLobby_EventScript_FeelingsMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerLobby_EventScript_FeelingsMan")
}

internal object BattleFrontier_BattleTowerLobby_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_BattleTowerLobby.WinsInRowRecorded)
}

internal object BattleFrontier_BattleTowerLobby_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_BattleTowerLobby.CanLeaveUntilLossOrSevenWins)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TEMP_2, BattleFrontier_BattleTowerLobby_EventScript_AlreadyInterviewed
 * setvar VAR_0x8005, TVSHOW_BRAVO_TRAINER_BATTLE_TOWER_PROFILE
 * special InterviewBefore
 * goto_if_eq VAR_RESULT, TRUE, BattleFrontier_BattleTowerLobby_EventScript_AlreadyInterviewed
 * copyvar VAR_0x8009, VAR_0x8006
 * msgbox BattleFrontier_BattleTowerLobby_Text_InterviewRequest, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, BattleFrontier_BattleTowerLobby_EventScript_AcceptInterview
 * goto_if_eq VAR_RESULT, NO, BattleFrontier_BattleTowerLobby_EventScript_DeclineInterview
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerLobby_EventScript_Reporter : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerLobby_EventScript_Reporter")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * apprentice_gavelvlmode
 * goto_if_eq VAR_RESULT, FALSE, Apprentice_EventScript_FirstMeeting
 * apprentice_shouldcheckgone
 * goto_if_eq VAR_0x8004, FALSE, Apprentice_EventScript_AskQuestion  @ VAR_0x8004 always TRUE here
 * goto_if_set FLAG_DAILY_APPRENTICE_LEAVES, Apprentice_EventScript_Gone
 * ```
 */
internal object BattleFrontier_BattleTowerLobby_EventScript_Apprentice : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerLobby_EventScript_Apprentice")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_FRONTIER_FACILITY, FRONTIER_FACILITY_TOWER
 * special SavePlayerParty
 * msgbox BattleFrontier_BattleTowerLobby_Text_WelcomeDoubleBattle, MSGBOX_DEFAULT
 * message BattleFrontier_BattleTowerLobby_Text_TakeDoublesChallenge
 * waitmessage
 * multichoice 17, 6, MULTI_CHALLENGEINFO, FALSE
 * switch VAR_RESULT
 * case 0, BattleFrontier_BattleTowerLobby_EventScript_TryEnterDoublesChallenge
 * case 1, BattleFrontier_BattleTowerLobby_EventScript_ExplainDoublesChallenge
 * case 2, BattleFrontier_BattleTowerLobby_EventScript_CancelChallenge
 * case MULTI_B_PRESSED, BattleFrontier_BattleTowerLobby_EventScript_CancelChallenge
 * ```
 */
internal object BattleFrontier_BattleTowerLobby_EventScript_DoublesAttendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerLobby_EventScript_DoublesAttendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_FRONTIER_FACILITY, FRONTIER_FACILITY_TOWER
 * clearflag FLAG_CHOSEN_MULTI_BATTLE_NPC_PARTNER
 * special SavePlayerParty
 * msgbox BattleFrontier_BattleTowerLobby_Text_WelcomeMultiBattle, MSGBOX_DEFAULT
 * message BattleFrontier_BattleTowerLobby_Text_TakeMultisChallenge
 * waitmessage
 * multichoice 17, 6, MULTI_CHALLENGEINFO, FALSE
 * switch VAR_RESULT
 * case 0, BattleFrontier_BattleTowerLobby_EventScript_TryEnterMultisChallenge
 * case 1, BattleFrontier_BattleTowerLobby_EventScript_ExplainMultisChallenge
 * case 2, BattleFrontier_BattleTowerLobby_EventScript_CancelChallenge
 * case MULTI_B_PRESSED, BattleFrontier_BattleTowerLobby_EventScript_CancelChallenge
 * ```
 */
internal object BattleFrontier_BattleTowerLobby_EventScript_MultisAttendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerLobby_EventScript_MultisAttendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_FRONTIER_FACILITY, FRONTIER_FACILITY_TOWER
 * special SavePlayerParty
 * msgbox BattleFrontier_BattleTowerLobby_Text_WelcomeLinkMultiBattle, MSGBOX_DEFAULT
 * message BattleFrontier_BattleTowerLobby_Text_TakeLinkMultisChallenge
 * waitmessage
 * multichoice 17, 6, MULTI_CHALLENGEINFO, FALSE
 * switch VAR_RESULT
 * case 0, BattleFrontier_BattleTowerLobby_EventScript_TryEnterLinkMultisChallenge
 * case 1, BattleFrontier_BattleTowerLobby_EventScript_ExplainLinkMultisChallenge
 * case 2, BattleFrontier_BattleTowerLobby_EventScript_CancelChallenge
 * case MULTI_B_PRESSED, BattleFrontier_BattleTowerLobby_EventScript_CancelChallenge
 * ```
 */
internal object BattleFrontier_BattleTowerLobby_EventScript_LinkMultisAttendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerLobby_EventScript_LinkMultisAttendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * frontier_results FRONTIER_FACILITY_TOWER, FRONTIER_MODE_SINGLES
 * waitbuttonpress
 * special RemoveRecordsWindow
 * releaseall
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerLobby_EventScript_ShowSinglesResults : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerLobby_EventScript_ShowSinglesResults")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * frontier_results FRONTIER_FACILITY_TOWER, FRONTIER_MODE_DOUBLES
 * waitbuttonpress
 * special RemoveRecordsWindow
 * releaseall
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerLobby_EventScript_ShowDoublesResults : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerLobby_EventScript_ShowDoublesResults")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * frontier_results FRONTIER_FACILITY_TOWER, FRONTIER_MODE_MULTIS
 * waitbuttonpress
 * special RemoveRecordsWindow
 * releaseall
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerLobby_EventScript_ShowMultisResults : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerLobby_EventScript_ShowMultisResults")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * frontier_results FRONTIER_FACILITY_TOWER, FRONTIER_MODE_LINK_MULTIS
 * waitbuttonpress
 * special RemoveRecordsWindow
 * releaseall
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerLobby_EventScript_ShowLinkMultisResults : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerLobby_EventScript_ShowLinkMultisResults")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox BattleFrontier_BattleTowerLobby_Text_RulesAreListed, MSGBOX_DEFAULT
 * goto BattleFrontier_BattleTowerLobby_EventScript_ReadRulesBoard
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerLobby_EventScript_RulesBoard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerLobby_EventScript_RulesBoard")
}

internal val BattleFrontier_BattleTowerLobbyScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_BattleTowerLobby_EventScript_SinglesAttendant" to
            BattleFrontier_BattleTowerLobby_EventScript_SinglesAttendant,
        "BattleFrontier_BattleTowerLobby_EventScript_FeelingsMan" to
            BattleFrontier_BattleTowerLobby_EventScript_FeelingsMan,
        "BattleFrontier_BattleTowerLobby_EventScript_Woman" to
            BattleFrontier_BattleTowerLobby_EventScript_Woman,
        "BattleFrontier_BattleTowerLobby_EventScript_Boy" to
            BattleFrontier_BattleTowerLobby_EventScript_Boy,
        "BattleFrontier_BattleTowerLobby_EventScript_Reporter" to
            BattleFrontier_BattleTowerLobby_EventScript_Reporter,
        "BattleFrontier_BattleTowerLobby_EventScript_Apprentice" to
            BattleFrontier_BattleTowerLobby_EventScript_Apprentice,
        "BattleFrontier_BattleTowerLobby_EventScript_DoublesAttendant" to
            BattleFrontier_BattleTowerLobby_EventScript_DoublesAttendant,
        "BattleFrontier_BattleTowerLobby_EventScript_MultisAttendant" to
            BattleFrontier_BattleTowerLobby_EventScript_MultisAttendant,
        "BattleFrontier_BattleTowerLobby_EventScript_LinkMultisAttendant" to
            BattleFrontier_BattleTowerLobby_EventScript_LinkMultisAttendant,
        "BattleFrontier_BattleTowerLobby_EventScript_ShowSinglesResults" to
            BattleFrontier_BattleTowerLobby_EventScript_ShowSinglesResults,
        "BattleFrontier_BattleTowerLobby_EventScript_ShowDoublesResults" to
            BattleFrontier_BattleTowerLobby_EventScript_ShowDoublesResults,
        "BattleFrontier_BattleTowerLobby_EventScript_ShowMultisResults" to
            BattleFrontier_BattleTowerLobby_EventScript_ShowMultisResults,
        "BattleFrontier_BattleTowerLobby_EventScript_ShowLinkMultisResults" to
            BattleFrontier_BattleTowerLobby_EventScript_ShowLinkMultisResults,
        "BattleFrontier_BattleTowerLobby_EventScript_RulesBoard" to
            BattleFrontier_BattleTowerLobby_EventScript_RulesBoard,
    )
