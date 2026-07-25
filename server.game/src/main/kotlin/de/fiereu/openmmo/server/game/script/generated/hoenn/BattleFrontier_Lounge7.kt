package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_Lounge7
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object BattleFrontier_Lounge7_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge7.ThinkLadiesDontGetAlong)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_C, SCROLL_MULTI_BF_MOVE_TUTOR_1
 * goto_if_set FLAG_MET_FRONTIER_BEAUTY_MOVE_TUTOR, BattleFrontier_Lounge7_EventScript_AlreadyMetLeftTutor
 * msgbox BattleFrontier_Lounge7_Text_LeftTutorIntro, MSGBOX_DEFAULT
 * setflag FLAG_MET_FRONTIER_BEAUTY_MOVE_TUTOR
 * goto BattleFrontier_Lounge7_EventScript_ChooseLeftTutorMove
 * end
 * ```
 */
internal object BattleFrontier_Lounge7_EventScript_LeftMoveTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_Lounge7_EventScript_LeftMoveTutor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_C, SCROLL_MULTI_BF_MOVE_TUTOR_2
 * goto_if_set FLAG_MET_FRONTIER_SWIMMER_MOVE_TUTOR, BattleFrontier_Lounge7_EventScript_AlreadyMetRightTutor
 * msgbox BattleFrontier_Lounge7_Text_RightTutorIntro, MSGBOX_DEFAULT
 * setflag FLAG_MET_FRONTIER_SWIMMER_MOVE_TUTOR
 * goto BattleFrontier_Lounge7_EventScript_ChooseRightTutorMove
 * end
 * ```
 */
internal object BattleFrontier_Lounge7_EventScript_RightMoveTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_Lounge7_EventScript_RightMoveTutor")
}

internal object BattleFrontier_Lounge7_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge7.LadiesWereStrongAndBeautiful)
}

internal val BattleFrontier_Lounge7Scripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_Lounge7_EventScript_Sailor" to BattleFrontier_Lounge7_EventScript_Sailor,
        "BattleFrontier_Lounge7_EventScript_LeftMoveTutor" to
            BattleFrontier_Lounge7_EventScript_LeftMoveTutor,
        "BattleFrontier_Lounge7_EventScript_RightMoveTutor" to
            BattleFrontier_Lounge7_EventScript_RightMoveTutor,
        "BattleFrontier_Lounge7_EventScript_Gentleman" to
            BattleFrontier_Lounge7_EventScript_Gentleman,
    )
