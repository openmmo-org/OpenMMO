package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FourIsland_House1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TUTOR_BODY_SLAM, EventScript_BodySlamTaught
 * msgbox Text_BodySlamTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_BodySlamDeclined
 * call EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, EventScript_BodySlamDeclined
 * msgbox Text_BodySlamWhichMon
 * setvar VAR_0x8005, MOVETUTOR_BODY_SLAM
 * call EventScript_ChooseMoveTutorMon
 * goto_if_eq VAR_RESULT, FALSE, EventScript_BodySlamDeclined
 * setflag FLAG_TUTOR_BODY_SLAM
 * goto EventScript_BodySlamTaught
 * end
 * ```
 */
internal object FourIsland_House1_EventScript_BodySlamTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FourIsland_House1_EventScript_BodySlamTutor")
}

internal object FourIsland_House1_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FourIsland_House1.YoureAwfullyHeavy)
}

internal val FourIsland_House1Scripts: Map<String, Script> =
    mapOf(
        "FourIsland_House1_EventScript_BodySlamTutor" to
            FourIsland_House1_EventScript_BodySlamTutor,
        "FourIsland_House1_EventScript_FatMan" to FourIsland_House1_EventScript_FatMan,
    )
