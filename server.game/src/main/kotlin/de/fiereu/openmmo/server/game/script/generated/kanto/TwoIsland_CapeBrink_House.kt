package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * faceplayer
 * goto_if_set FLAG_LEARNED_ALL_MOVES_AT_CAPE_BRINK, CapeBrinkTutor_EventScript_TaughtAllMoves
 * goto_if_set FLAG_TEMP_2, CapeBrinkTutor_EventScript_MoveJustTaught
 * bufferleadmonspeciesname STR_VAR_1
 * msgbox Text_UltimateMoveThatMon
 * specialvar VAR_RESULT, CapeBrinkGetMoveToTeachLeadPokemon
 * goto_if_eq VAR_RESULT, FALSE, CapeBrinkTutor_EventScript_NoLeadStarter
 * copyvar VAR_0x8009, VAR_0x8005
 * call_if_eq VAR_FACING, DIR_NORTH, CapeBrinkTutor_EventScript_JumpInPlaceDown
 * call_if_eq VAR_FACING, DIR_SOUTH, CapeBrinkTutor_EventScript_JumpInPlaceUp
 * call_if_eq VAR_FACING, DIR_EAST, CapeBrinkTutor_EventScript_JumpInPlaceLeft
 * call_if_eq VAR_FACING, DIR_WEST, CapeBrinkTutor_EventScript_JumpInPlaceRight
 * msgbox Text_AllowMeToTeachMonUltimateMove, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, CapeBrinkTutor_EventScript_DeclineMove
 * call EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, CapeBrinkTutor_EventScript_DeclineMove
 * msgbox Text_LetMeConferUltimateMove
 * closemessage
 * fadescreen FADE_TO_BLACK
 * goto CapeBrinkTutor_EventScript_ChooseMon
 * ```
 */
internal object TwoIsland_CapeBrink_House_EventScript_StarterTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TwoIsland_CapeBrink_House_EventScript_StarterTutor")
}

internal val TwoIsland_CapeBrink_HouseScripts: Map<String, Script> =
    mapOf(
        "TwoIsland_CapeBrink_House_EventScript_StarterTutor" to
            TwoIsland_CapeBrink_House_EventScript_StarterTutor,
    )
