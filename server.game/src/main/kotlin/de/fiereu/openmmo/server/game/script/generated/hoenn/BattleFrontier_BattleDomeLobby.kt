package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_BattleDomeLobby
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_FRONTIER_FACILITY, FRONTIER_FACILITY_DOME
 * setvar VAR_FRONTIER_BATTLE_MODE, FRONTIER_MODE_SINGLES
 * goto BattleFrontier_BattleDomeLobby_EventScript_AttendantWelcome
 * end
 * ```
 */
internal object BattleFrontier_BattleDomeLobby_EventScript_SinglesAttendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleDomeLobby_EventScript_SinglesAttendant")
}

internal object BattleFrontier_BattleDomeLobby_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_BattleDomeLobby.NeedToCheckOpponentCarefully)
}

internal object BattleFrontier_BattleDomeLobby_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_BattleDomeLobby.WinnersGainReputation)
}

internal object BattleFrontier_BattleDomeLobby_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_BattleDomeLobby.TrashedInFirstRound)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * dome_getwinnersname
 * msgbox BattleFrontier_BattleDomeLobby_Text_LastWinnerWasTough, MSGBOX_NPC
 * end
 * ```
 */
internal object BattleFrontier_BattleDomeLobby_EventScript_Maniac : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleDomeLobby_EventScript_Maniac")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_FRONTIER_FACILITY, FRONTIER_FACILITY_DOME
 * setvar VAR_FRONTIER_BATTLE_MODE, FRONTIER_MODE_DOUBLES
 * goto BattleFrontier_BattleDomeLobby_EventScript_AttendantWelcome
 * end
 * ```
 */
internal object BattleFrontier_BattleDomeLobby_EventScript_DoublesAttendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleDomeLobby_EventScript_DoublesAttendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * frontier_results FRONTIER_FACILITY_DOME, FRONTIER_MODE_SINGLES
 * waitbuttonpress
 * special RemoveRecordsWindow
 * releaseall
 * end
 * ```
 */
internal object BattleFrontier_BattleDomeLobby_EventScript_ShowSinglesResults : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleDomeLobby_EventScript_ShowSinglesResults")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * dome_get DOME_DATA_PREV_TOURNEY_TYPE
 * call_if_eq VAR_RESULT, 0, BattleFrontier_BattleDomeLobby_EventScript_PrevTourneyResultsSinglesLv50
 * call_if_eq VAR_RESULT, 1, BattleFrontier_BattleDomeLobby_EventScript_PrevTourneyResultsDoublesLv50
 * call_if_eq VAR_RESULT, 2, BattleFrontier_BattleDomeLobby_EventScript_PrevTourneyResultsSinglesLvOpen
 * call_if_eq VAR_RESULT, 3, BattleFrontier_BattleDomeLobby_EventScript_PrevTourneyResultsDoublesLvOpen
 * fadescreen FADE_TO_BLACK
 * dome_showprevtourneytree
 * waitstate
 * end
 * ```
 */
internal object BattleFrontier_BattleDomeLobby_EventScript_ShowPrevTourneyTree : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleDomeLobby_EventScript_ShowPrevTourneyTree")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * frontier_results FRONTIER_FACILITY_DOME, FRONTIER_MODE_DOUBLES
 * waitbuttonpress
 * special RemoveRecordsWindow
 * releaseall
 * end
 * ```
 */
internal object BattleFrontier_BattleDomeLobby_EventScript_ShowDoublesResults : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleDomeLobby_EventScript_ShowDoublesResults")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox BattleFrontier_BattleDomeLobby_Text_RulesAreListed, MSGBOX_DEFAULT
 * goto BattleFrontier_BattleDomeLobby_EventScript_ReadRulesBoard
 * end
 * ```
 */
internal object BattleFrontier_BattleDomeLobby_EventScript_RulesBoard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleDomeLobby_EventScript_RulesBoard")
}

internal val BattleFrontier_BattleDomeLobbyScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_BattleDomeLobby_EventScript_SinglesAttendant" to
            BattleFrontier_BattleDomeLobby_EventScript_SinglesAttendant,
        "BattleFrontier_BattleDomeLobby_EventScript_Man" to
            BattleFrontier_BattleDomeLobby_EventScript_Man,
        "BattleFrontier_BattleDomeLobby_EventScript_Lass" to
            BattleFrontier_BattleDomeLobby_EventScript_Lass,
        "BattleFrontier_BattleDomeLobby_EventScript_FatMan" to
            BattleFrontier_BattleDomeLobby_EventScript_FatMan,
        "BattleFrontier_BattleDomeLobby_EventScript_Maniac" to
            BattleFrontier_BattleDomeLobby_EventScript_Maniac,
        "BattleFrontier_BattleDomeLobby_EventScript_DoublesAttendant" to
            BattleFrontier_BattleDomeLobby_EventScript_DoublesAttendant,
        "BattleFrontier_BattleDomeLobby_EventScript_ShowSinglesResults" to
            BattleFrontier_BattleDomeLobby_EventScript_ShowSinglesResults,
        "BattleFrontier_BattleDomeLobby_EventScript_ShowPrevTourneyTree" to
            BattleFrontier_BattleDomeLobby_EventScript_ShowPrevTourneyTree,
        "BattleFrontier_BattleDomeLobby_EventScript_ShowDoublesResults" to
            BattleFrontier_BattleDomeLobby_EventScript_ShowDoublesResults,
        "BattleFrontier_BattleDomeLobby_EventScript_RulesBoard" to
            BattleFrontier_BattleDomeLobby_EventScript_RulesBoard,
    )
