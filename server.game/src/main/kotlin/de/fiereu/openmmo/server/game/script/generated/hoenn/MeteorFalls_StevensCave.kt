package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * goto_if_set FLAG_DEFEATED_METEOR_FALLS_STEVEN, MeteorFalls_StevensCave_EventScript_Defeated
 * waitse
 * playse SE_PIN
 * applymovement LOCALID_METEOR_FALLS_STEVEN, Common_Movement_ExclamationMark
 * waitmovement 0
 * applymovement LOCALID_METEOR_FALLS_STEVEN, Common_Movement_Delay48
 * waitmovement 0
 * applymovement LOCALID_METEOR_FALLS_STEVEN, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox MeteorFalls_StevensCave_Text_ShouldKnowHowGoodIAmExpectWorst, MSGBOX_DEFAULT
 * trainerbattle_no_intro TRAINER_STEVEN, MeteorFalls_StevensCave_Text_StevenDefeat
 * msgbox MeteorFalls_StevensCave_Text_MyPredictionCameTrue, MSGBOX_DEFAULT
 * setflag FLAG_DEFEATED_METEOR_FALLS_STEVEN
 * release
 * end
 * ```
 */
internal object MeteorFalls_StevensCave_EventScript_Steven : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MeteorFalls_StevensCave_EventScript_Steven")
}

internal val MeteorFalls_StevensCaveScripts: Map<String, Script> =
    mapOf(
        "MeteorFalls_StevensCave_EventScript_Steven" to MeteorFalls_StevensCave_EventScript_Steven,
    )
